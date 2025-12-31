package com.easier_minecraft.mixin;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.easier_minecraft.accessor.MultiDamageAccessor;
import com.easier_minecraft.register.EnchantmentRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Unique
    private LivingEntity target = (LivingEntity) (Object) this;

    private float calculateReductionPercent(int sonicGuardLevel) {
        return Math.min(sonicGuardLevel * 0.2F, 0.8F);
    }

    @ModifyVariable(method = "damage", at = @At("HEAD"), argsOnly = true, ordinal = 0)
    private float modifyDamageAmount(float amount, DamageSource source) {
        if (source.getSource() instanceof ArrowEntity arrowEntity &&
                arrowEntity instanceof MultiDamageAccessor accessor &&
                accessor.getMultiDamage()) {
            amount *= 1.5F;
        } else if (source.isOf(DamageTypes.SONIC_BOOM)) {
            int sonicGuardLevel = 0;
            RegistryEntry.Reference<Enchantment> sonicGuardEntry = target.getWorld().getRegistryManager()
                    .get(RegistryKeys.ENCHANTMENT)
                    .getEntry(EnchantmentRegister.SONIC_GUARD)
                    .orElse(null);
            if (sonicGuardEntry != null) {
                for (ItemStack armorStack : target.getArmorItems()) {
                    sonicGuardLevel += EnchantmentHelper.getLevel(sonicGuardEntry, armorStack);
                }
            }
            if (sonicGuardLevel > 0) {
                float reductionPercent = calculateReductionPercent(sonicGuardLevel);
                return amount * (1.0F - reductionPercent);
            }
        }

        return amount;
    }

    @Inject(method = "onDamaged", at = @At("TAIL"))
    private void modifyHurtTime(DamageSource source, CallbackInfo ci) {
        Entity sourceEntity = source.getAttacker();
        if (!(sourceEntity instanceof PlayerEntity)) {
            return;
        }
        int swiftComboLevel = 0;
        RegistryEntry.Reference<Enchantment> swiftComboEntry = target.getWorld().getRegistryManager()
                .get(RegistryKeys.ENCHANTMENT)
                .getEntry(EnchantmentRegister.SWIFT_COMBO)
                .orElse(null);
        if (swiftComboEntry != null) {
            swiftComboLevel = EnchantmentHelper.getLevel(swiftComboEntry,
                    ((LivingEntity) sourceEntity).getMainHandStack());
        }
        target.hurtTime = Math.max(0, 10 - 2 * swiftComboLevel);
    }

    @Inject(method = "damage", at = @At("TAIL"))
    private void modifyTimeUntilRegen(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        Entity sourceEntity = source.getAttacker();
        if (!(sourceEntity instanceof PlayerEntity)) {
            return;
        }
        int swiftComboLevel = 0;
        RegistryEntry.Reference<Enchantment> swiftComboEntry = target.getWorld().getRegistryManager()
                .get(RegistryKeys.ENCHANTMENT)
                .getEntry(EnchantmentRegister.SWIFT_COMBO)
                .orElse(null);
        if (swiftComboEntry != null) {
            swiftComboLevel = EnchantmentHelper.getLevel(swiftComboEntry,
                    ((LivingEntity) sourceEntity).getMainHandStack());
        }
        target.timeUntilRegen = Math.max(10, 20 - 2 * swiftComboLevel);
    }

    @Inject(method = "onKilledBy", at = @At("HEAD"))
    private void healAttacker(@Nullable LivingEntity adversary, CallbackInfo ci) {
        if (adversary == null) {
            return;
        }
        if (!target.getWorld().isClient) {
            int lifeDrainLevel = 0;
            RegistryEntry.Reference<Enchantment> lifeDrainEntry = target.getWorld().getRegistryManager()
                    .get(RegistryKeys.ENCHANTMENT)
                    .getEntry(EnchantmentRegister.LIFE_DRAIN)
                    .orElse(null);
            if (lifeDrainEntry != null) {
                lifeDrainLevel = EnchantmentHelper.getLevel(lifeDrainEntry, adversary.getMainHandStack());
            }
            adversary.heal(adversary.getRandom().nextFloat() * lifeDrainLevel * 2);
        }
    }

}
