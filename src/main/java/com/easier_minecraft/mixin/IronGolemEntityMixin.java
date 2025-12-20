package com.easier_minecraft.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.passive.IronGolemEntity;

@Mixin(IronGolemEntity.class)
public class IronGolemEntityMixin {
    @Unique
    private IronGolemEntity ironGolem = (IronGolemEntity) (Object) this;

    @Inject(method = "tickMovement", at = @At("TAIL"))
    private void onTick(CallbackInfo ci) {
        if (!ironGolem.getWorld().isClient && ironGolem.isAlive() && ironGolem.age % 10 == 0) {
			ironGolem.heal(1.0F);
		}
    }

}
