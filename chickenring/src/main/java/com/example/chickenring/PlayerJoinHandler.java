package com.example.chickenring;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class PlayerJoinHandler {
    public static void register() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.getPlayer();
            NbtCompound persistentData = PlayerDataHandler.getPlayerData(player);

            if (!persistentData.getBoolean("has_received_chicken_ring_guide_book")) {
                // Create a Patchouli book with the proper tag
                Item patchouliBookItem = Registry.ITEM.get(new Identifier("patchouli", "guide_book"));
                ItemStack bookStack = new ItemStack(patchouliBookItem);

                NbtCompound tag = bookStack.getOrCreateNbt();
                tag.putString("patchouli:book", "hellfiremadeit:peckronomicon");

                // Give the book to the player
                if (!player.getInventory().insertStack(bookStack)) {
                    player.dropStack(bookStack);
                }

                persistentData.putBoolean("has_received_chicken_ring_guide_book", true);
                PlayerDataHandler.setPlayerData(player, persistentData);
            }
        });
    }
}
