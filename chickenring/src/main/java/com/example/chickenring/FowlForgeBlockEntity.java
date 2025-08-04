package com.example.chickenring;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Simple BlockEntity with a fluid‐tank field.
 * Later we’ll hook up actual Storage<FluidVariant> and recipes.
 */
public class FowlForgeBlockEntity extends BlockEntity {
    // our fluid tank: stored as millibuckets (mB)
    private long storedEssence = 0;
    public static final long CAPACITY_MB = 1000;

     public FowlForgeBlockEntity(BlockPos pos, BlockState state) {
    super(ChickenRingMod.FOWL_FORGE_BLOCK_ENTITY, pos, state);
}

    /** Called each server tick by the Block’s ticker. */
    public void tick(World world, BlockPos pos, BlockState state) {
        // For now, do nothing. Later: process fluid + recipe.
        // Mark dirty if you update any data:
        // markDirty();
    }

    // --- NBT persistence ---
    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        this.storedEssence = tag.getLong("Essence");
    }

    @Override
    public void writeNbt(NbtCompound tag) {
        super.writeNbt(tag);
        tag.putLong("Essence", this.storedEssence);
    }

    // --- helper to insert fluid mB ---
    public long insertEssence(long amountMb) {
        long space = CAPACITY_MB - storedEssence;
        long toInsert = Math.min(amountMb, space);
        storedEssence += toInsert;
        markDirty();
        return toInsert;
    }

    public long getStoredEssence() {
        return storedEssence;
    }
}
