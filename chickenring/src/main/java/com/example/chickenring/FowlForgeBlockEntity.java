package com.example.chickenring;

import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Optional;
import java.util.List;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.random.Random;

public class FowlForgeBlockEntity extends BlockEntity implements Inventory, ExtendedScreenHandlerFactory {
    public static final long CAPACITY_MB = 12000; // 12,000 mB = 12 L
    private long storedEssence = 0;
    private int soundTimer = 0;
    private boolean wasFullLastTick = false;

    private static final int SOUND_INTERVAL = 1200; // Play sound every 5 minutes (6000 ticks = 300 seconds)
    // 11-slot inventory: 0=fluid, 1–9=inputs, 10=output
    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(11, ItemStack.EMPTY);
    private Optional<FowlForgeRecipe> currentRecipe = Optional.empty();
    private static final SoundEvent[] AMBIENT_SOUNDS = {
            ChickenRingMod.FOWL_FORGE_AMBIENT0,
            ChickenRingMod.FOWL_FORGE_AMBIENT1,
            ChickenRingMod.FOWL_FORGE_AMBIENT2
    };

    private final PropertyDelegate delegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            if (index == 0)
                return (int) (storedEssence >> 32); // High bits
            if (index == 1)
                return (int) storedEssence; // Low bits
            if (index == 2)
                return (int) (CAPACITY_MB >> 32); // High bits
            if (index == 3)
                return (int) CAPACITY_MB; // Low bits
            return 0;
        }

        @Override
        public void set(int index, int value) {
            if (index == 0)
                storedEssence = ((long) value << 32) | (storedEssence & 0xFFFFFFFFL);
            if (index == 1)
                storedEssence = (storedEssence & 0xFFFFFFFF00000000L) | (value & 0xFFFFFFFFL);
        }

        @Override
        public int size() {
            return 4;
        }
    };

    public FowlForgeBlockEntity(BlockPos pos, BlockState state) {
        super(ChickenRingMod.FOWL_FORGE_BLOCK_ENTITY, pos, state);
        System.out.println("FowlForgeBlockEntity: Initialized at " + pos);
    }

    public static void tick(World world, BlockPos pos, BlockState state, FowlForgeBlockEntity be) {
        if (!world.isClient) {
            boolean isFullNow = be.storedEssence >= CAPACITY_MB;

            if (isFullNow && !be.wasFullLastTick) {
                // Just reached max capacity, grant advancement to nearby players
                List<ServerPlayerEntity> nearbyPlayers = world.getPlayers()
                        .stream()
                        .filter(player -> player instanceof ServerPlayerEntity
                                && pos.isWithinDistance(player.getPos(), 5.0))
                        .map(player -> (ServerPlayerEntity) player)
                        .toList();

                for (ServerPlayerEntity player : nearbyPlayers) {
                    Advancement advancement = player.getServer()
                            .getAdvancementLoader()
                            .get(new Identifier("hellfiremadeit", "max_fowl_forge_capacity"));

                    if (advancement != null) {
                        AdvancementProgress progress = player.getAdvancementTracker().getProgress(advancement);
                        if (!progress.isDone()) {
                            player.getAdvancementTracker().grantCriterion(advancement, "trigger_on_max");
                            System.out.println("FowlForge: Granted max capacity advancement to "
                                    + player.getName().getString() + " at " + pos);
                        }
                    }
                }
                be.wasFullLastTick = true; // Mark that advancement was granted this tick
            } else if (!isFullNow) {
                // Reset flag to allow granting advancement next time it fills
                be.wasFullLastTick = false;
            }

            // Ambient sound logic
            if (be.storedEssence > 1000) {
                be.soundTimer++;
                if (be.soundTimer >= SOUND_INTERVAL) {
                    Random random = world.getRandom();
                    SoundEvent sound = AMBIENT_SOUNDS[random.nextInt(AMBIENT_SOUNDS.length)];
                    System.out.println("FowlForge: Playing ambient sound " + sound.getId() + " at " + pos
                            + ", essence: " + be.storedEssence);
                    world.playSound(null, pos, sound, SoundCategory.BLOCKS, 0.5f, 0.8f + random.nextFloat() * 0.4f);
                    be.soundTimer = 0;
                }
            } else {
                be.soundTimer = 0;
            }

            be.handleFluidSlot();
            be.handleCrafting();
        }
    }

    private void handleCrafting() {
        if (items.get(10).isEmpty()) { // Only check for recipe if output slot is empty
            Optional<FowlForgeRecipe> recipe = world.getRecipeManager()
                    .getFirstMatch(ChickenRingMod.FOWL_FORGE_RECIPE_TYPE, this, world);
            if (recipe.isEmpty() || storedEssence < recipe.get().getEssenceCost()) {
                currentRecipe = Optional.empty();
            } else {
                currentRecipe = recipe;
                items.set(10, recipe.get().craft(this));
                System.out.println("FowlForge: Previewing recipe output: " + recipe.get().getId());
                markDirty();
                if (world != null) {
                    world.updateListeners(pos, getCachedState(), getCachedState(), 3);
                }
            }
        } else if (!items.get(10).isEmpty() && currentRecipe.isEmpty()) {
            // Clear output if no valid recipe matches (e.g., ingredients changed)
            items.set(10, ItemStack.EMPTY);
            markDirty();
            if (world != null) {
                world.updateListeners(pos, getCachedState(), getCachedState(), 3);
            }
        }
    }

    private void consumeIngredientsAndEssence() {
        if (currentRecipe.isPresent() && storedEssence >= currentRecipe.get().getEssenceCost()) {
            // Consume ingredients
            for (int i = 0; i < 9; i++) {
                ItemStack stack = items.get(i + 1);
                if (!stack.isEmpty()) {
                    stack.decrement(1);
                    items.set(i + 1, stack);
                }
            }
            // Consume essence
            storedEssence -= currentRecipe.get().getEssenceCost();
            System.out.println(
                    "FowlForge: Consumed ingredients and " + currentRecipe.get().getEssenceCost() + " mB essence");
            currentRecipe = Optional.empty();
            markDirty();
            if (world != null) {
                world.updateListeners(pos, getCachedState(), getCachedState(), 3);
            }
        }
    }

    private void handleFluidSlot() {
        ItemStack in = items.get(0);
        if (!in.isEmpty()) {
            if (in.getItem() == ChickenRingMod.DARK_CHICKEN_ESSENCE_BUCKET) {
                if (storedEssence + 1000 <= CAPACITY_MB) {
                    storedEssence += 1000;
                    items.set(0, new ItemStack(net.minecraft.item.Items.BUCKET));
                    System.out.println("FowlForge: Added 1000 mB, storedEssence: " + storedEssence);
                    markDirty();
                    if (world != null) {
                        world.updateListeners(pos, getCachedState(), getCachedState(), 3);
                    }
                }
            } else if (in.getItem() == ChickenRingMod.DARK_CHICKEN_ESSENCE_CELL) {
                if (storedEssence + 100 <= CAPACITY_MB) {
                    storedEssence += 100;
                    items.set(0, ItemStack.EMPTY);
                    System.out.println("FowlForge: Added 100 mB, storedEssence: " + storedEssence);
                    markDirty();
                    if (world != null) {
                        world.updateListeners(pos, getCachedState(), getCachedState(), 3);
                    }
                }
            }
        }
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack ret = Inventories.splitStack(items, slot, amount);
        if (!ret.isEmpty() && slot == 10) {
            consumeIngredientsAndEssence();
        }
        if (!ret.isEmpty()) {
            markDirty();
            if (world != null) {
                world.updateListeners(pos, getCachedState(), getCachedState(), 3);
            }
        }
        return ret;
    }

    @Override
    public ItemStack removeStack(int slot) {
        ItemStack ret = Inventories.removeStack(items, slot);
        if (!ret.isEmpty() && slot == 10) {
            consumeIngredientsAndEssence();
        }
        if (!ret.isEmpty()) {
            markDirty();
            if (world != null) {
                world.updateListeners(pos, getCachedState(), getCachedState(), 3);
            }
        }
        return ret;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        items.set(slot, stack);
        if (stack.getCount() > getMaxCountPerStack()) {
            stack.setCount(getMaxCountPerStack());
        }
        if (slot == 10 && stack.isEmpty()) {
            consumeIngredientsAndEssence();
        }
        markDirty();
        if (world != null) {
            world.updateListeners(pos, getCachedState(), getCachedState(), 3);
        }
    }

    public long getStoredEssence() {
        return storedEssence;
    }

    public PropertyDelegate getDelegate() {
        return delegate;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("block.hellfiremadeit.fowl_forge");
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new FowlForgeScreenHandler(syncId, playerInventory, this, ScreenHandlerContext.create(world, pos));
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, net.minecraft.network.PacketByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    // --- Inventory impl & NBT ---

    @Override
    public int size() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        return items.stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public ItemStack getStack(int slot) {
        return items.get(slot);
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return pos.isWithinDistance(player.getPos(), 5.0);
    }

    @Override
    public void clear() {
        items.clear();
        currentRecipe = Optional.empty();
        markDirty();
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        storedEssence = tag.getLong("Essence");
        Inventories.readNbt(tag, items);
    }

    @Override
    public void writeNbt(NbtCompound tag) {
        super.writeNbt(tag);
        tag.putLong("Essence", storedEssence);
        Inventories.writeNbt(tag, items);
    }

    
}