package com.example.chickenring;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FowlForgeBlock extends BlockWithEntity {
    public FowlForgeBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        // Force the block to be rendered using its block model
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FowlForgeBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state,
                                                                   BlockEntityType<T> type) {
        if (world instanceof ServerWorld) {
            return (world1, pos, state1, be) -> {
                if (be instanceof FowlForgeBlockEntity) {
                    ((FowlForgeBlockEntity) be).tick(world1, pos, state1);
                }
            };
        }
        return null;
    }
}
