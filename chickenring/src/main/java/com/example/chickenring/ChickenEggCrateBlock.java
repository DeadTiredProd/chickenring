package com.example.chickenring;

import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.util.math.random.Random;

public class ChickenEggCrateBlock extends FallingBlock {
    /** Chance (0.0–1.0) that it breaks on landing. */
    private static final float BREAK_CHANCE = 0.2f;

    public ChickenEggCrateBlock(Settings settings) {
        super(settings);
    }

    /**
     * Called when the falling‐block entity lands and turns into a block again
     * (or breaks into items if it can’t place).
     */
    @Override
    public void onLanding(World world, BlockPos pos, BlockState fallingState, BlockState newState, net.minecraft.entity.FallingBlockEntity entity) {
        super.onLanding(world, pos, fallingState, newState, entity);

        // Only run server‐side
        if (world.isClient) return;

        Random random = ((ServerWorld)world).getRandom();
        if (random.nextFloat() < BREAK_CHANCE) {
            // Remove the crate block without dropping its item
            world.breakBlock(pos, false);

            // Spawn 1–3 baby chickens
            int count = 1 + random.nextInt(3);
            for (int i = 0; i < count; i++) {
                ChickenEntity chick = new ChickenEntity(EntityType.CHICKEN, world);
                chick.setBreedingAge(-24000);  // mark as baby
                chick.refreshPositionAndAngles(
                    pos.getX() + 0.5, 
                    pos.getY(), 
                    pos.getZ() + 0.5, 
                    random.nextFloat() * 360, 
                    0
                );
                world.spawnEntity(chick);
            }
        }
    }
}
