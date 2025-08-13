package com.example.chickenring.entity.goals;

import com.example.chickenring.ChonkenNest;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Random;

public class FindNestGoal extends Goal {
    private final PathAwareEntity mob;
    private final double speed;
    private final int searchRange;
    private final Random random;

    private BlockPos nestPos = null;
    private long sitUntil = 0;
    private long cooldownUntil = 0;

    public FindNestGoal(PathAwareEntity mob, double speed, int searchRange) {
        this.mob = mob;
        this.speed = speed;
        this.searchRange = searchRange;
        this.random = new Random();
        this.setControls(EnumSet.of(Control.MOVE));
    }

    @Override
    public boolean canStart() {
        World world = mob.getWorld();
        long currentTime = world.getTime();

        // If already has a nest, check if it still exists and is valid
        if (nestPos != null) {
            if (isNestStillValid(world, nestPos)) {
                if (currentTime < sitUntil) {
                    return true;
                }
            } else {
                // Nest destroyed or no longer valid
                releaseNest(world, nestPos);
                nestPos = null;
                return false;
            }
        }

        // On cooldown, don't look for nests
        if (currentTime < cooldownUntil) {
            return false;
        }

        // Try to find nearest unoccupied and valid nest
        Optional<BlockPos> found = findNearestValidNest(world);
        if (found.isPresent()) {
            nestPos = found.get();
            claimNest(world, nestPos);
            sitUntil = currentTime + getRandomTicks(30, 120); // Sit 30s–5min
            return true;
        }

        return false;
    }

    @Override
    public boolean shouldContinue() {
        if (nestPos == null) {
            return false;
        }

        if (!isNestStillValid(mob.getWorld(), nestPos)) {
            // Immediate abandon if destroyed or invalid mid-sit
            releaseNest(mob.getWorld(), nestPos);
            nestPos = null;
            return false;
        }

        return mob.getWorld().getTime() < sitUntil;
    }

    @Override
    public void start() {
        if (nestPos != null) {
            mob.getNavigation().startMovingTo(
                nestPos.getX() + 0.5,
                nestPos.getY(),
                nestPos.getZ() + 0.5,
                speed
            );
        }
    }

    @Override
    public void stop() {
        if (nestPos != null && mob.getWorld().getTime() >= sitUntil) {
            releaseNest(mob.getWorld(), nestPos);
            cooldownUntil = mob.getWorld().getTime() + getRandomTicks(30, 120);
            nestPos = null;
        }
    }

    private Optional<BlockPos> findNearestValidNest(World world) {
        BlockPos mobPos = mob.getBlockPos();
        BlockPos bestPos = null;
        double bestDistSq = Double.MAX_VALUE;

        for (int dx = -searchRange; dx <= searchRange; dx++) {
            for (int dy = -2; dy <= 2; dy++) {
                for (int dz = -searchRange; dz <= searchRange; dz++) {
                    BlockPos checkPos = mobPos.add(dx, dy, dz);
                    BlockState state = world.getBlockState(checkPos);
                    if (state.getBlock() instanceof ChonkenNest) {
                        boolean occupied = state.get(ChonkenNest.OCCUPIED);
                        if (!occupied && isNestSheltered(world, checkPos)) {
                            double distSq = checkPos.getSquaredDistance(mobPos);
                            if (distSq < bestDistSq) {
                                bestDistSq = distSq;
                                bestPos = checkPos;
                            }
                        }
                    }
                }
            }
        }
        return Optional.ofNullable(bestPos);
    }

    private boolean isNestStillValid(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return state.getBlock() instanceof ChonkenNest && isNestSheltered(world, pos);
    }

    private boolean isNestSheltered(World world, BlockPos pos) {
        // No direct view of the sky
        if (world.isSkyVisible(pos.up())) {
            return false;
        }

        // Must have a light source within 20 blocks
        BlockPos.Mutable searchPos = new BlockPos.Mutable();
        for (int dx = -20; dx <= 20; dx++) {
            for (int dy = -20; dy <= 20; dy++) {
                for (int dz = -20; dz <= 20; dz++) {
                    searchPos.set(pos.getX() + dx, pos.getY() + dy, pos.getZ() + dz);
                    if (world.isChunkLoaded(searchPos)) {
                        BlockState checkState = world.getBlockState(searchPos);
                        if (checkState.getLuminance() > 0) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private void claimNest(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (state.getBlock() instanceof ChonkenNest) {
            world.setBlockState(pos, state.with(ChonkenNest.OCCUPIED, true));
        }
    }

    private void releaseNest(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (state.getBlock() instanceof ChonkenNest) {
            world.setBlockState(pos, state.with(ChonkenNest.OCCUPIED, false));
        }
    }

    private int getRandomTicks(int minSeconds, int maxSeconds) {
        return (random.nextInt(maxSeconds - minSeconds + 1) + minSeconds) * 20;
    }

    // Save nest data
    public void writeCustomData(NbtCompound nbt) {
        if (nestPos != null) {
            nbt.putInt("NestX", nestPos.getX());
            nbt.putInt("NestY", nestPos.getY());
            nbt.putInt("NestZ", nestPos.getZ());
        }
        nbt.putLong("CooldownUntil", cooldownUntil);
        nbt.putLong("SitUntil", sitUntil);
    }

    // Load nest data
    public void readCustomData(NbtCompound nbt) {
        if (nbt.contains("NestX")) {
            nestPos = new BlockPos(nbt.getInt("NestX"), nbt.getInt("NestY"), nbt.getInt("NestZ"));
        }
        cooldownUntil = nbt.getLong("CooldownUntil");
        sitUntil = nbt.getLong("SitUntil");
    }
}
