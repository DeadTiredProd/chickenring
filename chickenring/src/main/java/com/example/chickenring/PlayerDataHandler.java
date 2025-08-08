package com.example.chickenring;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.WeakHashMap;

public class PlayerDataHandler {
    private static final WeakHashMap<ServerPlayerEntity, NbtCompound> dataMap = new WeakHashMap<>();

    public static NbtCompound getPlayerData(ServerPlayerEntity player) {
        return dataMap.computeIfAbsent(player, p -> new NbtCompound());
    }

    public static void setPlayerData(ServerPlayerEntity player, NbtCompound data) {
        dataMap.put(player, data);
    }

    public static void register() {
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (entity instanceof ServerPlayerEntity player) {
                NbtCompound persistentData = new NbtCompound();
                readNbt(player, player.writeNbt(persistentData));
            }
        });
    }

    public static void writeNbt(ServerPlayerEntity player, NbtCompound nbt) {
        NbtCompound data = getPlayerData(player);
        nbt.put("hellfiremadeit_data", data);
    }

    public static void readNbt(ServerPlayerEntity player, NbtCompound nbt) {
        if (nbt.contains("hellfiremadeit_data")) {
            setPlayerData(player, nbt.getCompound("hellfiremadeit_data"));
        }
    }
}
