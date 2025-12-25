package com.easier_minecraft.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @Unique
    private PlayerEntity player = (PlayerEntity) (Object) this;

    @Inject(method = "isInvulnerableTo", at = @At("HEAD"), cancellable = true)
    private void modifyInvulnerablility(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        if (source.isOf(DamageTypes.OUT_OF_WORLD) && (player.isCreative() || player.isSpectator())) {
            cir.setReturnValue(true);
            cir.cancel();
        }
        if (source.getAttacker() instanceof PlayerEntity playerEntity) {
            if (playerEntity.equals(player)) {
                cir.setReturnValue(true);
                cir.cancel();
            }
        }
    }

}
