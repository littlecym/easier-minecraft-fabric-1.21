package com.easier_minecraft.enchantment;

import java.util.List;

import com.mojang.serialization.MapCodec;

import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public record BlessedEdgeEnchantmentEffect() implements EnchantmentEntityEffect {
    public static final MapCodec<BlessedEdgeEnchantmentEffect> CODEC = MapCodec.unit(BlessedEdgeEnchantmentEffect::new);

    private static final float BASE_CHANCE = 0.2F;
    private static final float CHANCE_PER_LEVEL = 0.15F;
    private static final List<RegistryEntry<StatusEffect>> VALID_BENEFICIAL_EFFECTS = List.of(
            StatusEffects.ABSORPTION,
            StatusEffects.CONDUIT_POWER,
            StatusEffects.DOLPHINS_GRACE,
            StatusEffects.FIRE_RESISTANCE,
            StatusEffects.HASTE,
            StatusEffects.JUMP_BOOST,
            StatusEffects.LUCK,
            StatusEffects.REGENERATION,
            StatusEffects.RESISTANCE,
            StatusEffects.SATURATION,
            StatusEffects.SPEED,
            StatusEffects.STRENGTH,
            StatusEffects.WATER_BREATHING);

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity user, Vec3d pos) {
        if (user instanceof PlayerEntity player) {
            float chance = BASE_CHANCE + CHANCE_PER_LEVEL * (level - 1);
            if (player.getRandom().nextFloat() <= chance) {
                player.setStatusEffect(
                        new StatusEffectInstance(
                                VALID_BENEFICIAL_EFFECTS
                                        .get(player.getRandom().nextInt(VALID_BENEFICIAL_EFFECTS.size())),
                                320, player.getRandom().nextInt(level)),
                        null);
            }
        }

    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }

}
