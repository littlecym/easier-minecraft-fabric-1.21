package com.easier_minecraft.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

@Mixin(ExperienceOrbEntity.class)
public class ExperienceOrbEntityMixin {
    @Unique
    private ExperienceOrbEntity experienceOrb = (ExperienceOrbEntity) (Object) this;
    @Shadow
    private int amount;
    @Shadow
    private int pickingCount;

    @Shadow
    private int repairPlayerGears(ServerPlayerEntity player, int amount) {
        throw new UnsupportedOperationException("Should not reach here.");
    }

    @Inject(method = "onPlayerCollision", at = @At("HEAD"), cancellable = true)
    private void modifyExperiencePickUpDelay(PlayerEntity player, CallbackInfo ci) {
        if (player instanceof ServerPlayerEntity serverPlayerEntity) {
            player.experiencePickUpDelay = 0;
            while (this.pickingCount > 0) {
                player.sendPickup(experienceOrb, 1);
                int i = this.repairPlayerGears(serverPlayerEntity, this.amount);
                if (i > 0) {
                    player.addExperience(i);
                }
                this.pickingCount--;
            }
            experienceOrb.discard();
            ci.cancel();
        }
    }

}
