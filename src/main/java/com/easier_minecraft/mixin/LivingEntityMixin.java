package com.easier_minecraft.mixin;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.easier_minecraft.register.EnchantmentRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    private float calculateReductionPercent(int sonicGuardLevel) {
        return Math.min(sonicGuardLevel * 0.2F, 0.8F);
    }

    @ModifyVariable(method = "damage", at = @At("HEAD"), argsOnly = true, ordinal = 0)
    private float modifyDamageAmount(float amount, DamageSource source) {
        LivingEntity target = (LivingEntity) (Object) this;
        Optional<RegistryEntry.Reference<Enchantment>> sonicGuardEntry = target.getWorld().getRegistryManager()
                .getWrapperOrThrow(RegistryKeys.ENCHANTMENT)
                .getOptional(EnchantmentRegister.SONIC_GUARD);

        if (source.isOf(DamageTypes.SONIC_BOOM) && sonicGuardEntry.isPresent()) {
            int sonicGuardLevel = 0;
            for (ItemStack armorStack : target.getArmorItems()) {
                sonicGuardLevel += EnchantmentHelper.getLevel(sonicGuardEntry.get(), armorStack);
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
        LivingEntity target = (LivingEntity) (Object) this;
        Entity sourceEntity = source.getAttacker();
        if (!(sourceEntity instanceof PlayerEntity)) {
            return;
        }
        Optional<RegistryEntry.Reference<Enchantment>> swiftComboEntry = sourceEntity.getWorld().getRegistryManager()
                .getWrapperOrThrow(RegistryKeys.ENCHANTMENT)
                .getOptional(EnchantmentRegister.SWIFT_COMBO);
        if (swiftComboEntry.isEmpty()) {
            return;
        }
        int swiftComboLevel = EnchantmentHelper.getLevel(swiftComboEntry.get(),
                ((LivingEntity) sourceEntity).getMainHandStack());
        target.hurtTime = Math.max(0, target.maxHurtTime - 2 * swiftComboLevel);
    }

}
