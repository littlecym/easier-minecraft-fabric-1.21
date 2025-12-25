package com.easier_minecraft.mixin;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.easier_minecraft.register.EnchantmentRegister;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;

@Mixin(Entity.class)
public class EntityMixin {
    @Unique
    private Entity entity = (Entity) (Object) this;
    @Unique
    private Optional<RegistryEntry.Reference<Enchantment>> lifeDrainEntry = null;

    @Inject(method = "onKilledOther", at = @At("HEAD"))
    private void healLivingEntity(ServerWorld world, LivingEntity other, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof LivingEntity livingEntity) {
            if (lifeDrainEntry == null) {
                lifeDrainEntry = entity.getWorld().getRegistryManager()
                        .getWrapperOrThrow(RegistryKeys.ENCHANTMENT)
                        .getOptional(EnchantmentRegister.LIFE_DRAIN);
            }
            int lifeDrainLevel = EnchantmentHelper.getLevel(lifeDrainEntry.get(),
                    livingEntity.getMainHandStack());
            livingEntity.heal(livingEntity.getRandom().nextFloat() * lifeDrainLevel);
        }
    }

}
