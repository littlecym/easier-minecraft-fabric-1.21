package com.easier_minecraft.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.passive.VillagerEntity;

@Mixin(VillagerEntity.class)
public class VillagerEntityMixin {

    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(CallbackInfo ci) {
        VillagerEntity villager = (VillagerEntity) (Object) this;
        if (!villager.getWorld().isClient && villager.isAlive() && villager.age % 10 == 0) {
            villager.heal(1.0F);
        }
    }

}
