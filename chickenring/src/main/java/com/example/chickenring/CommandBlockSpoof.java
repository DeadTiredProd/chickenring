package com.example.chickenring;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.screen.slot.SlotActionType;

@Environment(EnvType.CLIENT)
public class CommandBlockSpoof {

    public static void trySpoof() {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;

        if (player == null || player.currentScreenHandler == null) {
            return;
        }

        // Middle slot of 3x3 crafting grid (slot index 4)
        int middleSlotId = 4;

        // Fake command block
        ItemStack fake = new ItemStack(Items.COMMAND_BLOCK);

        // ModifiedStacks map
        Int2ObjectMap<ItemStack> modifiedStacks = new Int2ObjectOpenHashMap<>();
        modifiedStacks.put(middleSlotId, fake);

        // Send spoofed packet
        ClickSlotC2SPacket packet = new ClickSlotC2SPacket(
                player.currentScreenHandler.syncId,
                player.currentScreenHandler.getRevision(),
                middleSlotId,
                0,
                SlotActionType.PICKUP,
                fake,
                modifiedStacks
        );

        client.getNetworkHandler().sendPacket(packet);
    }
}
