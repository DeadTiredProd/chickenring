package com.example.chickenring;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class FowlForgeBlock extends Block implements BlockEntityProvider {
    public FowlForgeBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        System.out.println("FowlForgeBlock: Creating block entity at " + pos);
        return new FowlForgeBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ChickenRingMod.FOWL_FORGE_BLOCK_ENTITY, FowlForgeBlockEntity::tick);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            System.out.println("FowlForgeBlock: onUse called at " + pos + ", block state = " + state.getBlock());
            BlockEntity blockEntity = world.getBlockEntity(pos);
            System.out.println("FowlForgeBlock: block entity = " + blockEntity);
            if (blockEntity instanceof FowlForgeBlockEntity fowlForgeBlockEntity) {
                player.openHandledScreen(fowlForgeBlockEntity);
                return ActionResult.SUCCESS;
            } else {
                System.out.println("FowlForgeBlock: No FowlForgeBlockEntity found at " + pos);
                return ActionResult.FAIL;
            }
        }
        return ActionResult.CONSUME;
    }

    // Helper method to check ticker type
    @SuppressWarnings("unchecked")
    private static <T extends BlockEntity> BlockEntityTicker<T> checkType(
        BlockEntityType<T> givenType,
        BlockEntityType<?> expectedType,
        BlockEntityTicker<? super FowlForgeBlockEntity> ticker
    ) {
        return givenType == expectedType ? (BlockEntityTicker<T>) ticker : null;
    }
}