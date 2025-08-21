package com.example.chickenring.entity.goals;

import com.example.chickenring.entity.ChonkenEntity;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.EnumSet;

public class FindGrassAtDayGoal extends Goal {
    private final ChonkenEntity chonken;
    private double targetX;
    private double targetY;
    private double targetZ;
    private final double speed = 1.0D;
    private final int searchRange = 10;

    private int forageTicks;
    private boolean foraging;
    private boolean wandering;

    private int grassCheckCooldown = 0;
    private int decisionDelay = 0; // per-entity stagger

    public FindGrassAtDayGoal(ChonkenEntity chonken) {
        this.chonken = chonken;
        this.setControls(EnumSet.of(Goal.Control.MOVE));
    }

    @Override
    public boolean canStart() {
        World world = this.chonken.getWorld();
        if (!world.isDay()) {
            return false;
        }

        // Desync AI decision checks per entity
        if (decisionDelay > 0) {
            decisionDelay--;
            return false;
        }

        decisionDelay = 20 + this.chonken.getRandom().nextInt(20) + (this.chonken.getId() % 10);

        if (world.getBlockState(this.chonken.getBlockPos().down()).isOf(Blocks.GRASS_BLOCK)) {
            return true;
        }

        if (findTargetPos()) {
            wandering = false;
            return true;
        }

        if (findRandomWanderPos()) {
            wandering = true;
            return true;
        }

        return false;
    }

    @Override
    public boolean shouldContinue() {
        return foraging || !this.chonken.getNavigation().isIdle();
    }

    @Override
    public void start() {
        BlockPos pos = this.chonken.getBlockPos();
        if (!wandering && this.chonken.getWorld().getBlockState(pos.down()).isOf(Blocks.GRASS_BLOCK)) {
            beginForaging();
        } else {
            this.chonken.getNavigation().startMovingTo(this.targetX, this.targetY, this.targetZ, this.speed);
        }
    }

    @Override
    public void stop() {
        this.chonken.setForaging(false);
        this.chonken.getNavigation().stop();
        this.forageTicks = 0;
        this.foraging = false;
        this.wandering = false;
        this.grassCheckCooldown = 0;
    }

    @Override
    public void tick() {
        if (foraging) {
            forageTicks--;
            if (forageTicks <= 0) {
                this.chonken.setForaging(false);
                this.foraging = false;

                if (findTargetPos()) {
                    wandering = false;
                    this.chonken.getNavigation().startMovingTo(this.targetX, this.targetY, this.targetZ, this.speed);
                } else if (findRandomWanderPos()) {
                    wandering = true;
                    this.chonken.getNavigation().startMovingTo(this.targetX, this.targetY, this.targetZ, this.speed);
                }
            }
        } else {
            if (!wandering && this.chonken.getNavigation().isIdle()) {
                BlockPos pos = this.chonken.getBlockPos();
                if (this.chonken.getWorld().getBlockState(pos.down()).isOf(Blocks.GRASS_BLOCK)) {
                    beginForaging();
                }
            }

            // while wandering, occasionally check for grass
            if (wandering) {
                grassCheckCooldown--;
                if (grassCheckCooldown <= 0) {
                    grassCheckCooldown = 30 + this.chonken.getRandom().nextInt(40); // 1.5–3.5 sec random
                    if (findTargetPos()) {
                        wandering = false;
                        this.chonken.getNavigation().startMovingTo(this.targetX, this.targetY, this.targetZ, this.speed);
                    }
                }
            }
        }
    }

    private void beginForaging() {
        this.forageTicks = 40 + this.chonken.getRandom().nextInt(100); // 2–7 sec forage
        this.chonken.setForaging(true);
        this.chonken.getNavigation().stop();
        this.foraging = true;
    }

    private boolean findTargetPos() {
        World world = this.chonken.getWorld();
        Random random = this.chonken.getRandom();
        BlockPos mobPos = this.chonken.getBlockPos();

        for (int i = 0; i < 20; ++i) {
            int x = mobPos.getX() + MathHelper.nextInt(random, -searchRange, searchRange);
            int z = mobPos.getZ() + MathHelper.nextInt(random, -searchRange, searchRange);
            int y = mobPos.getY() + MathHelper.nextInt(random, -2, 2);

            BlockPos targetPos = new BlockPos(x, y, z);

            if (world.getBlockState(targetPos.down()).isOf(Blocks.GRASS_BLOCK) &&
                ((PathAwareEntity) this.chonken).getPathfindingFavor(targetPos, world) >= 0.0F) {

                this.targetX = x + 0.5;
                this.targetY = y;
                this.targetZ = z + 0.5;
                return true;
            }
        }
        return false;
    }

    private boolean findRandomWanderPos() {
        Random random = this.chonken.getRandom();
        Vec3d pos = new Vec3d(
                this.chonken.getX() + (random.nextDouble() * 2 - 1) * searchRange,
                this.chonken.getY(),
                this.chonken.getZ() + (random.nextDouble() * 2 - 1) * searchRange
        );

        this.targetX = pos.x;
        this.targetY = pos.y;
        this.targetZ = pos.z;
        this.grassCheckCooldown = 30 + random.nextInt(40); // randomize cooldown
        return true;
    }
}
