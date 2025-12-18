package com.easier_minecraft.register;

import com.easier_minecraft.EasierMinecraft;
import com.easier_minecraft.enchantment.ExperienceHarvestEnchantmentEffect;
import com.easier_minecraft.enchantment.PsychedelicEnchantmentEffect;

import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.effect.EnchantmentEffectTarget;
import net.minecraft.item.Item;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public final class EnchantmentRegister {
	public static final RegistryKey<Enchantment> PSYCHEDELIC = of("psychedelic");
	public static final RegistryKey<Enchantment> SONIC_GUARD = of("sonic_guard");
	public static final RegistryKey<Enchantment> EXPERIENCE_HARVEST = of("experience_harvest");
	public static final RegistryKey<Enchantment> VOID_SALVATION = of("void_salvation");

	public static void bootstrap(Registerable<Enchantment> registry) {
		RegistryEntryLookup<Item> registryEntryLookup3 = registry.getRegistryLookup(RegistryKeys.ITEM);
		register(
				registry,
				PSYCHEDELIC,
				Enchantment.builder(
						Enchantment.definition(
								registryEntryLookup3.getOrThrow(
										TagKey.of(RegistryKeys.ITEM, Identifier.ofVanilla("enchantable/psychedelic"))),
								registryEntryLookup3.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
								10,
								3,
								Enchantment.leveledCost(1, 11),
								Enchantment.leveledCost(12, 11),
								1,
								AttributeModifierSlot.MAINHAND))
						.addEffect(EnchantmentEffectComponentTypes.POST_ATTACK, EnchantmentEffectTarget.ATTACKER,
								EnchantmentEffectTarget.VICTIM, new PsychedelicEnchantmentEffect()));
		register(
				registry,
				SONIC_GUARD,
				Enchantment.builder(
						Enchantment.definition(
								registryEntryLookup3.getOrThrow(ItemTags.CHEST_ARMOR_ENCHANTABLE),
								5,
								4,
								Enchantment.leveledCost(5, 6),
								Enchantment.leveledCost(11, 6),
								2,
								AttributeModifierSlot.ARMOR)));
		register(
				registry,
				EXPERIENCE_HARVEST,
				Enchantment.builder(
						Enchantment.definition(
								registryEntryLookup3.getOrThrow(ItemTags.SHARP_WEAPON_ENCHANTABLE),
								registryEntryLookup3.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
								10,
								3,
								Enchantment.leveledCost(1, 11),
								Enchantment.leveledCost(12, 11),
								1,
								AttributeModifierSlot.MAINHAND))
						.addEffect(EnchantmentEffectComponentTypes.POST_ATTACK, EnchantmentEffectTarget.ATTACKER,
								EnchantmentEffectTarget.ATTACKER, new ExperienceHarvestEnchantmentEffect()));
		register(
				registry,
				VOID_SALVATION,
				Enchantment.builder(
						Enchantment.definition(
								registryEntryLookup3.getOrThrow(ItemTags.LEG_ARMOR_ENCHANTABLE),
								2,
								1,
								Enchantment.leveledCost(25, 25),
								Enchantment.leveledCost(75, 25),
								4,
								AttributeModifierSlot.ARMOR)));
	}

	private static void register(Registerable<Enchantment> registry, RegistryKey<Enchantment> key,
			Enchantment.Builder builder) {
		registry.register(key, builder.build(key.getValue()));
	}

	private static RegistryKey<Enchantment> of(String id) {
		return RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(EasierMinecraft.MOD_ID, id));
	}

}
