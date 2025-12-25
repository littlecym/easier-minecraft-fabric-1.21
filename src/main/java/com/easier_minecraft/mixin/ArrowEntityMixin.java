package com.easier_minecraft.mixin;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.easier_minecraft.register.ItemRegister;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@Mixin(ArrowEntity.class)
public class ArrowEntityMixin {
    @Unique
    public boolean multiDamage = false;

    @Inject(method = "<init>(Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)V", at = @At("TAIL"))
    private void modifyDamage(World world, LivingEntity owner, ItemStack stack, @Nullable ItemStack shotFrom,
            CallbackInfo ci) {
        if (shotFrom == null || shotFrom.isEmpty()) {
            return;
        }
        if (shotFrom.isOf(ItemRegister.DIAMOND_BOW)) {
            multiDamage = true;
        }
    }

}
