package com.example.chickenring.entity.goals;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.EnumSet;

public class MoveToLightGoal extends Goal {
    private final MobEntity mob;
    private double targetX;
    private double targetY;
    private double targetZ;
    private final double speed = 1.0D; // Walking speed
    private final int searchRange = 10; // Horizontal search range
    private final int lightThresholdLow = 7; // Current light too low
    private final int lightThresholdHigh = 10; // Target light level

    public MoveToLightGoal(MobEntity mob) {
        this.mob = mob;
        this.setControls(EnumSet.of(Goal.Control.MOVE));
    }

    @Override
    public boolean canStart() {
        World world = this.mob.getWorld();
        if (!world.isNight()) {
            return false; // Only active at night
        }
        BlockPos pos = this.mob.getBlockPos();
        if (world.getLightLevel(pos) > lightThresholdLow) {
            return false; // Already in sufficient light
        }
        return this.findTargetPos();
    }

    @Override
    public boolean shouldContinue() {
        return !this.mob.getNavigation().isIdle();
    }

    @Override
    public void start() {
        this.mob.getNavigation().startMovingTo(this.targetX, this.targetY, this.targetZ, this.speed);
    }

    @Override
    public void stop() {
        this.mob.getNavigation().stop();
    }

    private boolean findTargetPos() {
    World world = this.mob.getWorld();
    Random random = this.mob.getRandom();
    BlockPos mobPos = this.mob.getBlockPos();

    for (int i = 0; i < 20; ++i) { // Try up to 20 random positions
        int x = mobPos.getX() + MathHelper.nextInt(random, -searchRange, searchRange);
        int z = mobPos.getZ() + MathHelper.nextInt(random, -searchRange, searchRange);
        int y = mobPos.getY() + MathHelper.nextInt(random, -2, 2); // Slight vertical variation

        BlockPos targetPos = new BlockPos(x, y, z);

        // Check light level and pathfinding favor
        if (world.getLightLevel(targetPos) >= lightThresholdHigh &&
            ((PathAwareEntity) this.mob).getPathfindingFavor(targetPos, world) >= 0.0F) {

            this.targetX = x;
            this.targetY = y;
            this.targetZ = z;
            return true;
        }
    }
    return false; // No suitable position found
}

}