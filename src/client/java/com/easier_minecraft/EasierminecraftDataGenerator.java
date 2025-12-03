package com.easier_minecraft;

import com.easier_minecraft.datagen.RecipeDatagenProvider;
import com.easier_minecraft.datagen.WorldDatagenProvider;
import com.easier_minecraft.register.EnchantmentRegister;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class EasierMinecraftDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(RecipeDatagenProvider::new);
        pack.addProvider(WorldDatagenProvider::new);
    }

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RegistryKeys.ENCHANTMENT, EnchantmentRegister::bootstrap);
    }
}
