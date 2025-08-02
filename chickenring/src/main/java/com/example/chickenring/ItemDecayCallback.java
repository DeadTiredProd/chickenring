package com.example.chickenring;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class ItemDecayCallback {
    private static final Map<UUID, Long> spawnTimes = new HashMap<>();
    private static final long DECAY_TIME_TICKS = 20 * 60; // 1 minute

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(ItemDecayCallback::onServerTick);
    }

    private static void onServerTick(MinecraftServer server) {
        Set<UUID> activeEntityUUIDs = new HashSet<>();

        for (ServerWorld world : server.getWorlds()) {
            long currentTick = world.getServer().getOverworld().getTime();

            for (ItemEntity itemEntity : world.getEntitiesByType(
                    net.minecraft.entity.EntityType.ITEM,
                    entity -> true)) {

                UUID uuid = itemEntity.getUuid();
                activeEntityUUIDs.add(uuid);

                Item item = itemEntity.getStack().getItem();

                boolean isRawChickenItem = item == Items.CHICKEN;
                boolean isRawChickenBlockItem = item instanceof BlockItem &&
                        ((BlockItem) item).getBlock() == ChickenRingMod.RAW_CHICKEN_BLOCK;

                if (!isRawChickenItem && !isRawChickenBlockItem) continue;

                BlockPos pos = itemEntity.getBlockPos();
                Biome biome = world.getBiome(pos).value();
                if (biome.getPrecipitation() == Biome.Precipitation.SNOW) continue;

                if (!spawnTimes.containsKey(uuid)) {
                    spawnTimes.put(uuid, currentTick);
                    continue;
                }

                long age = currentTick - spawnTimes.get(uuid);
                if (age >= DECAY_TIME_TICKS) {
                    itemEntity.remove(net.minecraft.entity.Entity.RemovalReason.DISCARDED);

                    Item rottedItem = isRawChickenBlockItem
                            ? ChickenRingMod.ROTTING_CHICKEN_BLOCK.asItem()
                            : ChickenRingMod.ROTTING_CHICKEN;

                    ItemEntity rottedEntity = new ItemEntity(
                            world,
                            itemEntity.getX(),
                            itemEntity.getY(),
                            itemEntity.getZ(),
                            new net.minecraft.item.ItemStack(rottedItem, itemEntity.getStack().getCount())
                    );
                    world.spawnEntity(rottedEntity);
                    spawnTimes.remove(uuid);
                }
            }
        }

        // Clean up memory: remove UUIDs of entities no longer in the world
        spawnTimes.keySet().removeIf(uuid -> !activeEntityUUIDs.contains(uuid));
    }
}
