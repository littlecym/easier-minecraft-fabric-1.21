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
    private int cooldown = 10;
    @Unique
    boolean wasJumping = false;

    @Inject(method = "tickMovement", at = @At("TAIL"))
    private void onJump(CallbackInfo ci) {
        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;

        if (cooldown == 0 && player.input.jumping && !wasJumping) {
            ClientPlayNetworking.send(new StopFallFlyingPayload());
            cooldown = 10;
        }
        if (!player.input.jumping) {
            wasJumping = false;
        } else {
            wasJumping = true;
        }
        if (!player.isFallFlying()) {
            cooldown = 10;
        } else if (cooldown > 0) {
            --cooldown;
        }
    }
}