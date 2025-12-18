package com.easier_minecraft.register;

import com.easier_minecraft.Payload.StopFallFlyingPayload;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public final class ServerReceiverRegister {

    public static void onInitialize() {
        PayloadTypeRegistry.playC2S().register(StopFallFlyingPayload.ID, StopFallFlyingPayload.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(StopFallFlyingPayload.ID, (payload, context) -> {
            context.server().execute(() -> {
                var player = context.player();
                if (player != null && player.isFallFlying()) {
                    player.stopFallFlying();
                }
            });
        });
    }

}
