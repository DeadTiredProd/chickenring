package com.example.chickenring;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.FurnaceOutputSlot;
import net.minecraft.screen.slot.Slot;

public class FowlForgeScreenHandler extends ScreenHandler {
    private final FowlForgeBlockEntity blockEntity;
    private final ScreenHandlerContext context;
    private final PropertyDelegate delegate;

    // Slot indices
    private static final int FLUID_INPUT = 0;
    private static final int ING_START = 1;
    private static final int ING_END = 9;
    private static final int RESULT = 10;

    // Positions (in GUI texture coords, adjusted for 176x166)
    private static final int FLUID_X = 12, FLUID_Y = 59; // Scaled from (40, 16)
    private static final int ING_X0 = 61, ING_Y0 = 17; // Scaled from (206, 18)
    private static final int RES_X = 134, RES_Y = 35;  // Scaled from (280, 54)
    private static final int INV_LEFT = 7, INV_TOP = 83; // Scaled from (94, 98)

    // Server-side constructor
    public FowlForgeScreenHandler(int syncId, PlayerInventory playerInventory, FowlForgeBlockEntity blockEntity, ScreenHandlerContext context) {
        super(ChickenRingMod.FOWL_FORGE_SCREEN_HANDLER, syncId);
        this.blockEntity = blockEntity;
        this.context = context;
        this.delegate = blockEntity.getDelegate();

        checkSize(blockEntity, 11);
        blockEntity.onOpen(playerInventory.player);
        addProperties(delegate);

        // Fluid slot
        addSlot(new Slot(blockEntity, FLUID_INPUT, FLUID_X, FLUID_Y) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isOf(ChickenRingMod.DARK_CHICKEN_ESSENCE_BUCKET)
                    || stack.isOf(ChickenRingMod.DARK_CHICKEN_ESSENCE_CELL);
            }
        });

        // 3x3 ingredients
        int idx = ING_START;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                addSlot(new Slot(blockEntity, idx++, ING_X0 + col * 18, ING_Y0 + row * 18));
            }
        }

        // Result
        addSlot(new FurnaceOutputSlot(playerInventory.player, blockEntity, RESULT, RES_X, RES_Y));

        // Player inventory
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 9; c++) {
                addSlot(new Slot(playerInventory, c + r * 9 + 9, INV_LEFT + c * 18, INV_TOP + r * 18));
            }
        }
        // Hotbar
        for (int c = 0; c < 9; c++) {
            addSlot(new Slot(playerInventory, c, INV_LEFT + c * 18, INV_TOP + 58));
        }
    }

    // Client-side constructor
    public FowlForgeScreenHandler(int syncId, PlayerInventory playerInventory, net.minecraft.network.PacketByteBuf buf) {
        this(syncId, playerInventory, (FowlForgeBlockEntity) playerInventory.player.getWorld().getBlockEntity(buf.readBlockPos()), ScreenHandlerContext.create(playerInventory.player.getWorld(), buf.readBlockPos()));
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return blockEntity.canPlayerUse(player);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        ItemStack original = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack stack = slot.getStack();
            original = stack.copy();

            if (invSlot == RESULT) {
                // Result → player
                if (!insertItem(stack, 11, slots.size(), true)) return ItemStack.EMPTY;
                slot.onQuickTransfer(stack, original);
            } else if (invSlot >= ING_START && invSlot <= ING_END) {
                // Ingredient → player
                if (!insertItem(stack, 11, slots.size(), false)) return ItemStack.EMPTY;
            } else if (invSlot == FLUID_INPUT) {
                // Fluid slot → player
                if (!insertItem(stack, 11, slots.size(), false)) return ItemStack.EMPTY;
            } else {
                // Player → fluid or ingredients
                if (stack.isOf(ChickenRingMod.DARK_CHICKEN_ESSENCE_BUCKET) ||
                    stack.isOf(ChickenRingMod.DARK_CHICKEN_ESSENCE_CELL)) {
                    if (!insertItem(stack, FLUID_INPUT, FLUID_INPUT + 1, false))
                        return ItemStack.EMPTY;
                } else {
                    if (!insertItem(stack, ING_START, ING_END + 1, false))
                        return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty()) slot.setStack(ItemStack.EMPTY);
            else slot.markDirty();
            if (stack.getCount() == original.getCount()) return ItemStack.EMPTY;
            slot.onTakeItem(player, stack);
        }
        return original;
    }

    public FowlForgeBlockEntity getBlockEntity() {
        return blockEntity;
    }
}