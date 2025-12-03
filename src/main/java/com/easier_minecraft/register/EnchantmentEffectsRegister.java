package com.easier_minecraft.register;


import com.mojang.serialization.MapCodec;
import com.easier_minecraft.EasierMinecraft;
import com.easier_minecraft.enchantment.AdvancedPowerEnchantmentEffect;
import com.easier_minecraft.enchantment.AdvancedProtectionEnchantmentEffect;
import com.easier_minecraft.enchantment.AdvancedSharpnessEnchantmentEffect;
import com.easier_minecraft.enchantment.PsychedelicEnchantmentEffect;

import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class EnchantmentEffectsRegister {
    public static final MapCodec<? extends EnchantmentEntityEffect> ADVANCED_SHARPNESS = register("advanced_sharpness", AdvancedSharpnessEnchantmentEffect.CODEC);
    public static final MapCodec<? extends EnchantmentEntityEffect> PSYCHEDELIC = register("psychedelic", PsychedelicEnchantmentEffect.CODEC);
    public static final MapCodec<? extends EnchantmentEntityEffect> ADVANCED_PROTECTION = register("advanced_protection", AdvancedProtectionEnchantmentEffect.CODEC);
    public static final MapCodec<? extends EnchantmentEntityEffect> ADVANCED_POWER = register("advanced_power", AdvancedPowerEnchantmentEffect.CODEC);

    private static MapCodec<? extends EnchantmentEntityEffect> register(String name, MapCodec<? extends EnchantmentEntityEffect> codec) {
        return Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, Identifier.of(EasierMinecraft.MOD_ID, name), codec);
    }

    public static void onInitialize() {

    }

}
