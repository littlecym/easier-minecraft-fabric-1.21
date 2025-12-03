package com.easier_minecraft.register;

import com.easier_minecraft.EasierMinecraft;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;

public final class PlacedFeatureRegister {
    public static final RegistryKey<PlacedFeature> DEEPSLATE_DIAMOND_ORE_PLACED_KEY = RegistryKey
			.of(RegistryKeys.PLACED_FEATURE, Identifier.of(EasierMinecraft.MOD_ID, "deepslate_diamond_ore"));
	public static final RegistryKey<PlacedFeature> ANCIENT_DEBRIS_PLACED_KEY = RegistryKey
			.of(RegistryKeys.PLACED_FEATURE, Identifier.of(EasierMinecraft.MOD_ID, "ancient_debris"));

    public static void onInitialize() {
		BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES,
				DEEPSLATE_DIAMOND_ORE_PLACED_KEY);
		BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(), GenerationStep.Feature.UNDERGROUND_ORES,
				ANCIENT_DEBRIS_PLACED_KEY);
    }

}
