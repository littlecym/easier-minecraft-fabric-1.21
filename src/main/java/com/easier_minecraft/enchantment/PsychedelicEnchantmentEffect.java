package com.easier_minecraft.enchantment;

import com.mojang.serialization.MapCodec;

import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public record PsychedelicEnchantmentEffect() implements EnchantmentEntityEffect {
    public static final MapCodec<PsychedelicEnchantmentEffect> CODEC = MapCodec.unit(PsychedelicEnchantmentEffect::new);

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity user, Vec3d pos) {
        if (user instanceof LivingEntity) {
            ((LivingEntity)user).addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 80 * level, level - 1));
            ((LivingEntity)user).addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 80 * level, level - 1));
            ((LivingEntity)user).addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 80 * level, level - 1));
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
    
}
