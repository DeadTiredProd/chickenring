package com.example.chickenring;

import net.minecraft.block.BlockState;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public abstract class DarkChickenEssenceFluid extends FlowableFluid {
    @Override public Fluid getFlowing() { return ChickenRingMod.DARK_CHICKEN_ESSENCE_FLOWING; }
    @Override public Fluid getStill()   { return ChickenRingMod.DARK_CHICKEN_ESSENCE_STILL; }
    @Override public Item getBucketItem() { return ChickenRingMod.DARK_CHICKEN_ESSENCE_BUCKET; }

    @Override protected boolean isInfinite() { return false; }

    @Override
    protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
        super.appendProperties(builder.add(LEVEL));
    }

    @Override
    protected BlockState toBlockState(FluidState state) {
        return ChickenRingMod.DARK_CHICKEN_ESSENCE_BLOCK.getDefaultState()
            .with(Properties.LEVEL_15, getBlockStateLevel(state));
    }

    @Override
    public boolean matchesType(Fluid fluid) {
        return fluid == getStill() || fluid == getFlowing();
    }

    @Override protected int getFlowSpeed(WorldView world)            { return 4; }
    @Override protected int getLevelDecreasePerBlock(WorldView world) { return 1; }
    @Override public int getTickRate(WorldView world)                 { return 5; }
    @Override public float getBlastResistance()                       { return 100f; }

    @Override
    protected void beforeBreakingBlock(WorldAccess world, BlockPos pos, BlockState state) {
        // No block drops
    }

    // <-- the critical override added here -->
    @Override
    protected boolean canBeReplacedWith(FluidState state, BlockView world, BlockPos pos, Fluid fluid, Direction direction) {
        return false;
    }
    // ----------------------------------------------------------

    // STILL variant
    public static class Still extends DarkChickenEssenceFluid {
        @Override public boolean isStill(FluidState state) { return true; }
        @Override public int getLevel(FluidState state)    { return 8; }
    }

    // FLOWING variant
    public static class Flowing extends DarkChickenEssenceFluid {
        @Override public boolean isStill(FluidState state) { return false; }
        @Override public int getLevel(FluidState state)    { return state.get(LEVEL); }
    }
}
