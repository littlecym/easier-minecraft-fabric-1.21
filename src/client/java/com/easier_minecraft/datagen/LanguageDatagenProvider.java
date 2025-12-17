package com.easier_minecraft.datagen;

import java.util.concurrent.CompletableFuture;

import com.easier_minecraft.register.EnchantmentRegister;
import com.easier_minecraft.register.ItemRegister;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;

public class LanguageDatagenProvider extends FabricLanguageProvider {

    public LanguageDatagenProvider(FabricDataOutput dataOutput, CompletableFuture<WrapperLookup> registryLookup) {
        super(dataOutput, "en_us", registryLookup);
    }

    @Override
    public String getName() {
        return "Language datagen provider";
    }

    @Override
    public void generateTranslations(WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(ItemRegister.EMERALD_APPLE, "Emerald Apple");
        translationBuilder.add(ItemRegister.EXPLOSION_BOW, "Explosion Bow");
        translationBuilder.add(ItemRegister.FIREWORK_ROCKET_BOW, "Firework Rocket Bow");
        translationBuilder.add(ItemRegister.TELEPORT_BOW, "Teleport Bow");
        translationBuilder.add(ItemRegister.VILLAGER_BOW, "Villager Bow");
        translationBuilder.add(getEnchantmentTranslationKey(EnchantmentRegister.PSYCHEDELIC), "Psychedelic");
        translationBuilder.add(getEnchantmentTranslationKey(EnchantmentRegister.SONIC_GUARD), "Sonic Guard");
        translationBuilder.add(getEnchantmentTranslationKey(Enchantments.INFINITY), "Infinity");
        translationBuilder.add(getEnchantmentTranslationKey(Enchantments.POWER), "Power");
        translationBuilder.add(getEnchantmentTranslationKey(Enchantments.PROTECTION), "Protection");
        translationBuilder.add(getEnchantmentTranslationKey(Enchantments.SHARPNESS), "Sharpness");
    }

    private static String getEnchantmentTranslationKey(RegistryKey<Enchantment> enchantment) {
        return "enchantment." + enchantment.getValue().getNamespace() + "." + enchantment.getValue().getPath();
    }

}
