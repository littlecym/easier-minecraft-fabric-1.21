package com.easier_minecraft.mixin.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.easier_minecraft.Payload.StopFallFlyingPayload;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    @Unique
    private int Cooldown = 20;

    @Inject(method = "tickMovement", at = @At("TAIL"))
    private void onJump(CallbackInfo ci) {
        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;

        if (!player.isFallFlying()) {
            Cooldown = 20;
            return;
        }
        if (Cooldown > 0) {
            --Cooldown;
            return;
        }

        if (player.input.jumping) {
            ClientPlayNetworking.send(new StopFallFlyingPayload());
            Cooldown = 20;
        }
    }
}