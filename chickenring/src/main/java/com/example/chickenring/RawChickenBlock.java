package com.example.chickenring;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class RawChickenBlock extends Block {
    public RawChickenBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!world.isClient()) {
            updateBlockState(world, pos);
        }
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (!world.isClient() && state.getBlock() == this) {
            updateBlockState(world, pos);
        }
    }

    private void updateBlockState(World world, BlockPos pos) {
        // Check Nether dimension properly
        if (isAdjacentToFireOrLava(world, pos) || world.getRegistryKey() == World.NETHER) {
            world.setBlockState(pos, ChickenRingMod.COOKED_CHICKEN_BLOCK.getDefaultState());
            return;
        }

        Biome biome = world.getBiome(pos).value();
        boolean isSnowBiome = biome.getPrecipitation() == Biome.Precipitation.SNOW;

        if (isSnowBiome) {
            world.setBlockState(pos, ChickenRingMod.RAW_CHICKEN_BLOCK.getDefaultState());
        } else {
            world.setBlockState(pos, ChickenRingMod.ROTTING_CHICKEN_BLOCK.getDefaultState());
        }
    }

    private boolean isAdjacentToFireOrLava(World world, BlockPos pos) {
        BlockPos[] adjacentPositions = {
            pos.up(), pos.down(), pos.north(), pos.south(), pos.east(), pos.west()
        };
        for (BlockPos adjPos : adjacentPositions) {
            BlockState adjState = world.getBlockState(adjPos);
            if (adjState.getBlock() == Blocks.FIRE || adjState.getBlock() == Blocks.LAVA) {
                return true;
            }
        }
        return false;
    }
}
