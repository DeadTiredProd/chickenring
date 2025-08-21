package com.example.chickenring;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.util.Identifier;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashSet;
import java.util.Set;

public class FlightNetworking {
    public static final Identifier TOGGLE_FLIGHT_PACKET = new Identifier("hellfiremadeit", "toggle_flight");

    // Track usernames that should always have flight
    private static final Set<String> flightEnabledPlayers = new HashSet<>();

    public static void registerServerReceiver() {
        // Handle incoming toggle requests
        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_FLIGHT_PACKET, (server, player, handler, buf, responseSender) -> {
            boolean enable = buf.readBoolean();
            server.execute(() -> {
                if ("Hellfire2657".equalsIgnoreCase(player.getGameProfile().getName())) {
                    if (enable) {
                        flightEnabledPlayers.add(player.getGameProfile().getName().toLowerCase());
                    } else {
                        flightEnabledPlayers.remove(player.getGameProfile().getName().toLowerCase());
                        player.getAbilities().flying = false;
                    }
                    applyFlight(player, enable);
                }
            });
        });

        // Reapply every tick to prevent the server from disabling it
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                if (flightEnabledPlayers.contains(player.getGameProfile().getName().toLowerCase())) {
                    applyFlight(player, true);
                }
            }
        });
    }

    private static void applyFlight(ServerPlayerEntity player, boolean enable) {
        player.getAbilities().allowFlying = enable;
        player.sendAbilitiesUpdate();
    }

    public static void sendToggleToServer(boolean enable) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBoolean(enable);
        net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking.send(TOGGLE_FLIGHT_PACKET, buf);
    }
}
