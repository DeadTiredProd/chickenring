package com.example.chickenring;

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
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.server.network.ServerPlayerEntity;

public class FowlForgeBlockEntity extends BlockEntity implements Inventory, ExtendedScreenHandlerFactory {
    public static final long CAPACITY_MB = 12000; // 12,000 mB = 12 L
    private long storedEssence = 0;

    // 11-slot inventory: 0=fluid, 1–9=inputs, 10=output
    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(11, ItemStack.EMPTY);

    private final PropertyDelegate delegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            if (index == 0) return (int) (storedEssence >> 32); // High bits
            if (index == 1) return (int) storedEssence; // Low bits
            if (index == 2) return (int) (CAPACITY_MB >> 32); // High bits
            if (index == 3) return (int) CAPACITY_MB; // Low bits
            return 0;
        }
        @Override
        public void set(int index, int value) {
            if (index == 0) storedEssence = ((long) value << 32) | (storedEssence & 0xFFFFFFFFL);
            if (index == 1) storedEssence = (storedEssence & 0xFFFFFFFF00000000L) | (value & 0xFFFFFFFFL);
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
            be.handleFluidSlot();
            // Later: handle recipes
        }
    }

    private void handleFluidSlot() {
        ItemStack in = items.get(0);
        if (!in.isEmpty()) {
            if (in.getItem() == ChickenRingMod.DARK_CHICKEN_ESSENCE_BUCKET) {
                if (storedEssence + 1000 <= CAPACITY_MB) {
                    storedEssence += 1000;
                    items.set(0, new ItemStack(net.minecraft.item.Items.BUCKET));
                    markDirty();
                    if (world != null) {
                        world.updateListeners(pos, getCachedState(), getCachedState(), 3);
                    }
                }
            } else if (in.getItem() == ChickenRingMod.DARK_CHICKEN_ESSENCE_CELL) {
                if (storedEssence + 100 <= CAPACITY_MB) {
                    storedEssence += 100;
                    items.set(0, ItemStack.EMPTY);
                    markDirty();
                    if (world != null) {
                        world.updateListeners(pos, getCachedState(), getCachedState(), 3);
                    }
                }
            }
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
    public ItemStack removeStack(int slot, int amount) {
        ItemStack ret = Inventories.splitStack(items, slot, amount);
        if (!ret.isEmpty()) markDirty();
        return ret;
    }

    @Override
    public ItemStack removeStack(int slot) {
        ItemStack ret = Inventories.removeStack(items, slot);
        if (!ret.isEmpty()) markDirty();
        return ret;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        items.set(slot, stack);
        if (stack.getCount() > getMaxCountPerStack()) {
            stack.setCount(getMaxCountPerStack());
        }
        markDirty();
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return pos.isWithinDistance(player.getPos(), 5.0);
    }

    @Override
    public void clear() {
        items.clear();
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