package com.easier_minecraft.register;

import com.easier_minecraft.EasierMinecraft;
import com.easier_minecraft.enchantment.PsychedelicEnchantmentEffect;

import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.AttributeEnchantmentEffect;
import net.minecraft.enchantment.effect.EnchantmentEffectTarget;
import net.minecraft.enchantment.effect.value.AddEnchantmentEffect;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.EnchantmentTags;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

public final class EnchantmentRegister {
	public static final RegistryKey<Enchantment> ADVANCED_SHARPNESS = of("advanced_sharpness");
	public static final RegistryKey<Enchantment> PSYCHEDELIC = of("psychedelic");
	public static final RegistryKey<Enchantment> ADVANCED_PROTECTION = of("advanced_protection");
	public static final RegistryKey<Enchantment> ADVANCED_POWER = of("advanced_power");

	public static void bootstrap(Registerable<Enchantment> registry) {
		RegistryEntryLookup<Enchantment> registryEntryLookup2 = registry.getRegistryLookup(RegistryKeys.ENCHANTMENT);
		RegistryEntryLookup<Item> registryEntryLookup3 = registry.getRegistryLookup(RegistryKeys.ITEM);
		register(
				registry,
				ADVANCED_SHARPNESS,
				Enchantment.builder(
						Enchantment.definition(
								registryEntryLookup3.getOrThrow(ItemTags.SHARP_WEAPON_ENCHANTABLE),
								registryEntryLookup3.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
								10,
								5,
								Enchantment.leveledCost(1, 11),
								Enchantment.leveledCost(12, 11),
								1,
								AttributeModifierSlot.MAINHAND))
						.exclusiveSet(registryEntryLookup2.getOrThrow(EnchantmentTags.DAMAGE_EXCLUSIVE_SET))
						.addEffect(EnchantmentEffectComponentTypes.DAMAGE,
								new AddEnchantmentEffect(EnchantmentLevelBasedValue.linear(4.0F, 5.0F))));
		register(
				registry,
				PSYCHEDELIC,
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
								EnchantmentEffectTarget.VICTIM, new PsychedelicEnchantmentEffect()));
		register(
				registry,
				ADVANCED_PROTECTION,
				Enchantment.builder(
						Enchantment.definition(
								registryEntryLookup3.getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
								10,
								4,
								Enchantment.leveledCost(1, 11),
								Enchantment.leveledCost(12, 11),
								1,
								AttributeModifierSlot.ARMOR))
						.exclusiveSet(registryEntryLookup2.getOrThrow(EnchantmentTags.ARMOR_EXCLUSIVE_SET))
						.addEffect(
								EnchantmentEffectComponentTypes.DAMAGE_PROTECTION,
								new AddEnchantmentEffect(EnchantmentLevelBasedValue.linear(1.25F)))
						.addEffect(
								EnchantmentEffectComponentTypes.ATTRIBUTES,
								new AttributeEnchantmentEffect(
										Identifier.of(EasierMinecraft.MOD_ID, "advanced_protection"),
										EntityAttributes.GENERIC_BURNING_TIME,
										EnchantmentLevelBasedValue.linear(-0.0625F),
										EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE)));
		register(
			registry,
			ADVANCED_POWER,
			Enchantment.builder(
					Enchantment.definition(
						registryEntryLookup3.getOrThrow(ItemTags.BOW_ENCHANTABLE),
						10,
						5,
						Enchantment.leveledCost(1, 11),
						Enchantment.leveledCost(12, 11),
						1,
						AttributeModifierSlot.MAINHAND
					)
				)
				.addEffect(
					EnchantmentEffectComponentTypes.DAMAGE,
					new AddEnchantmentEffect(EnchantmentLevelBasedValue.linear(3.0F)),
					EntityPropertiesLootCondition.builder(LootContext.EntityTarget.DIRECT_ATTACKER, EntityPredicate.Builder.create().type(EntityTypeTags.ARROWS).build())
				)
		);
	}

	private static void register(Registerable<Enchantment> registry, RegistryKey<Enchantment> key,
			Enchantment.Builder builder) {
		registry.register(key, builder.build(key.getValue()));
	}

	private static RegistryKey<Enchantment> of(String id) {
		return RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(EasierMinecraft.MOD_ID, id));
	}

}
