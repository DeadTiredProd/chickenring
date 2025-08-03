package com.example.chickenring;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;


public class FrozenCookedChickenBlock extends Block {
    public FrozenCookedChickenBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, net.minecraft.util.math.random.Random random) {
        // 1) Thaw if we're no longer in a snow biome
        Biome biome = world.getBiome(pos).value();
        boolean isSnowBiome = biome.getPrecipitation() == Biome.Precipitation.SNOW;
        if (!isSnowBiome) {
            world.setBlockState(pos, ChickenRingMod.COOKED_CHICKEN_BLOCK.getDefaultState());
            return;
        }

        // 2) Or thaw if next to any heat source
        if (isAdjacentToHeat(world, pos)) {
            world.setBlockState(pos, ChickenRingMod.COOKED_CHICKEN_BLOCK.getDefaultState());
        }
    }

    private boolean isAdjacentToHeat(World world, BlockPos pos) {
        for (BlockPos adj : new BlockPos[] {
                pos.up(), pos.down(), pos.north(), pos.south(), pos.east(), pos.west()
        }) {
            BlockState adjState = world.getBlockState(adj);
            if (adjState.getBlock() == Blocks.FIRE
             || adjState.getBlock() == Blocks.LAVA
             || adjState.getBlock() == Blocks.SOUL_FIRE) {
                return true;
            }
        }
        return false;
    }
}
