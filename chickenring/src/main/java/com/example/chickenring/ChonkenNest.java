package com.example.chickenring;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;


public class ChonkenNest extends HorizontalFacingBlock {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    private static final VoxelShape OUTLINE_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 12.0, 6.0, 12.0);
    private static final VoxelShape COLLISION_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 11.0, 6.0, 11.0);
    public static final BooleanProperty OCCUPIED = BooleanProperty.of("occupied");

    public ChonkenNest(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(OCCUPIED, false));
    }

    
    // Per-block getter
    public boolean isOccupied(BlockState state) {
        return state.get(OCCUPIED);
    }

    // Per-block setter
    public void setOccupied(World world, BlockPos pos, boolean occupied) {
        BlockState state = world.getBlockState(pos);
        world.setBlockState(pos, state.with(OCCUPIED, occupied), 3);
    }

    
    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return this.getDefaultState()
            .with(FACING, context.getPlayerFacing().getOpposite())
            .with(OCCUPIED, false);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return OUTLINE_SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return COLLISION_SHAPE;
    }

    @Override
    public boolean hasSidedTransparency(BlockState state) {
        return true; // ensures side faces render when touching
    }

    @Override
    public int getOpacity(BlockState state, BlockView world, BlockPos pos) {
        return 0; // 0 means full light passes through
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING,OCCUPIED);
    }
}
