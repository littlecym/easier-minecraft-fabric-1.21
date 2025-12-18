package com.easier_minecraft.register;


import com.mojang.serialization.MapCodec;
import com.easier_minecraft.EasierMinecraft;
import com.easier_minecraft.enchantment.ExperienceHarvestEnchantmentEffect;
import com.easier_minecraft.enchantment.PsychedelicEnchantmentEffect;
import com.easier_minecraft.enchantment.SonicGuardEnchantmentEffect;
import com.easier_minecraft.enchantment.VoidSalvationEnchantmentEffect;

import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class EnchantmentEffectsRegister {
    public static final MapCodec<? extends EnchantmentEntityEffect> PSYCHEDELIC = register("psychedelic", PsychedelicEnchantmentEffect.CODEC);
    public static final MapCodec<? extends EnchantmentEntityEffect> SONIC_GUARD = register("sonic_guard", SonicGuardEnchantmentEffect.CODEC);
    public static final MapCodec<? extends EnchantmentEntityEffect> EXPERIENCE_HARVEST = register("experience_harvest", ExperienceHarvestEnchantmentEffect.CODEC);
    public static final MapCodec<? extends EnchantmentEntityEffect> VOID_SALVATION = register("void_salvation", VoidSalvationEnchantmentEffect.CODEC);

    private static MapCodec<? extends EnchantmentEntityEffect> register(String name, MapCodec<? extends EnchantmentEntityEffect> codec) {
        return Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, Identifier.of(EasierMinecraft.MOD_ID, name), codec);
    }

    public static void onInitialize() {

    }

}
