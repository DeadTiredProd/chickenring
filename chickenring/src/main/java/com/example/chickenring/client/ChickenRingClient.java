package com.example.chickenring.client;

import com.example.chickenring.ChickenRingMod;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.lwjgl.glfw.GLFW;

public class ChickenRingClient implements ClientModInitializer {

    private static KeyBinding flyToggleKey;
    private static boolean flightEnabled = false;
    private static boolean wasJumping = false;
    private static long lastJumpTime = 0;

    @Override
    public void onInitializeClient() {
        flyToggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.chickenring.toggle_flight",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_UNKNOWN,
            "category.chickenring"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            PlayerEntity player = client.player;
            if (player == null || player.isCreative()) return;

            boolean hasRing = hasChickenRing(player);

            if (hasRing && flyToggleKey.getDefaultKey().getCode() == GLFW.GLFW_KEY_UNKNOWN) {
                boolean isJumping = MinecraftClient.getInstance().options.jumpKey.isPressed();

                if (isJumping && !wasJumping) {
                    long now = System.currentTimeMillis();
                    if (now - lastJumpTime < 300) {
                        flightEnabled = true;
                    }
                    lastJumpTime = now;
                }

                wasJumping = isJumping;
            }

            while (flyToggleKey.wasPressed()) {
                if (hasRing) {
                    flightEnabled = !flightEnabled;
                }
            }

            if (hasRing) {
                player.getAbilities().allowFlying = flightEnabled;
                player.sendAbilitiesUpdate();

                if (flightEnabled) {
                    if (!player.getAbilities().flying &&
                            MinecraftClient.getInstance().options.jumpKey.isPressed()) {
                        player.getAbilities().flying = true;
                        player.sendAbilitiesUpdate();
                    }
                    player.fallDistance = 0.0f;
                } else {
                    player.getAbilities().flying = false;
                    player.sendAbilitiesUpdate();
                }
            } else {
                player.getAbilities().allowFlying = false;
                player.getAbilities().flying = false;
                player.sendAbilitiesUpdate();
                flightEnabled = false;
            }
        });
    }

    private boolean hasChickenRing(PlayerEntity player) {
        for (ItemStack stack : player.getInventory().main) {
            if (stack.getItem() == ChickenRingMod.CHICKEN_RING) {
                return true;
            }
        }
        return false;
    }
}
