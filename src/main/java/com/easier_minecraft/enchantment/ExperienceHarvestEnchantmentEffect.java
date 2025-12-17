package com.easier_minecraft.enchantment;

import com.mojang.serialization.MapCodec;

import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public record ExperienceHarvestEnchantmentEffect() implements EnchantmentEntityEffect {
    public static final MapCodec<ExperienceHarvestEnchantmentEffect> CODEC = MapCodec
            .unit(ExperienceHarvestEnchantmentEffect::new);

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity user, Vec3d pos) {
        int temp = user.getRandom().nextInt(level);
        ExperienceOrbEntity.spawn(world, user.getPos(), level * 100);
        ((PlayerEntity) user).experienceLevel += temp;
        ((PlayerEntity) user).heal(temp);
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }

}
