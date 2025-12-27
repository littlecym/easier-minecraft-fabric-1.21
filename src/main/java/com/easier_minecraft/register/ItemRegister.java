package com.easier_minecraft.register;

import com.easier_minecraft.EasierMinecraft;
import com.easier_minecraft.item.DiamondBowItem;
import com.easier_minecraft.item.EmeraldAppleItem;
import com.easier_minecraft.item.ExplosionBowItem;
import com.easier_minecraft.item.FireworkRocketBowItem;
import com.easier_minecraft.item.TeleportBowItem;
import com.easier_minecraft.item.VillagerBowItem;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public final class ItemRegister {
	public static final ExplosionBowItem EXPLOSION_BOW = new ExplosionBowItem(new ExplosionBowItem.Settings()
			.maxCount(1)
			.rarity(Rarity.RARE));

	public static final TeleportBowItem TELEPORT_BOW = new TeleportBowItem(new TeleportBowItem.Settings()
			.maxCount(1)
			.rarity(Rarity.RARE));

	public static final FireworkRocketBowItem FIREWORK_ROCKET_BOW = new FireworkRocketBowItem(
			new FireworkRocketBowItem.Settings()
					.maxCount(1)
					.rarity(Rarity.RARE));

	public static final VillagerBowItem VILLAGER_BOW = new VillagerBowItem(new VillagerBowItem.Settings()
			.maxCount(1)
			.rarity(Rarity.RARE));

	public static final DiamondBowItem DIAMOND_BOW = new DiamondBowItem(new DiamondBowItem.Settings()
			.maxCount(1)
			.maxDamage(3047));

	public static final EmeraldAppleItem EMERALD_APPLE = new EmeraldAppleItem(new EmeraldAppleItem.Settings()
			.maxCount(64)
			.food(new FoodComponent.Builder()
					.nutrition(8)
					.saturationModifier(1.2F)
					.statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 9600, 9), 1.0F)
					.statusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 9600, 3), 1.0F)
					.statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 9600, 0), 1.0F)
					.statusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 9600, 4), 1.0F)
					.statusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 9600, 4), 1.0F)
					.statusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 9600, 9), 1.0F)
					.statusEffect(new StatusEffectInstance(StatusEffects.HASTE, 9600, 4), 1.0F)
					.statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 9600, 1), 1.0F)
					.statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 9600, 4), 1.0F)
					.statusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 9600, 0), 1.0F)
					.statusEffect(new StatusEffectInstance(StatusEffects.LUCK, 9600, 4), 1.0F)
					.statusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 9600, 1), 1.0F)
					.statusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 9600, 0), 1.0F)
					.statusEffect(new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, 9600, 0), 1.0F)
					.alwaysEdible()
					.build())
			.rarity(Rarity.EPIC));

	public static void onInitialize() {
		Registry.register(Registries.ITEM, Identifier.of(EasierMinecraft.MOD_ID, "explosion_bow"), EXPLOSION_BOW);
		Registry.register(Registries.ITEM, Identifier.of(EasierMinecraft.MOD_ID, "teleport_bow"), TELEPORT_BOW);
		Registry.register(Registries.ITEM, Identifier.of(EasierMinecraft.MOD_ID, "firework_rocket_bow"),
				FIREWORK_ROCKET_BOW);
		Registry.register(Registries.ITEM, Identifier.of(EasierMinecraft.MOD_ID, "villager_bow"), VILLAGER_BOW);
		Registry.register(Registries.ITEM, Identifier.of(EasierMinecraft.MOD_ID, "diamond_bow"), DIAMOND_BOW);
		Registry.register(Registries.ITEM, Identifier.of(EasierMinecraft.MOD_ID, "emerald_apple"), EMERALD_APPLE);
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(content -> {
			content.addAfter(Items.BOW, EXPLOSION_BOW);
		});
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(content -> {
			content.addAfter(Items.BOW, TELEPORT_BOW);
		});
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(content -> {
			content.addAfter(Items.BOW, FIREWORK_ROCKET_BOW);
		});
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(content -> {
			content.addAfter(Items.BOW, VILLAGER_BOW);
		});
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(content -> {
			content.addAfter(Items.BOW, DIAMOND_BOW);
		});
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(content -> {
			content.addAfter(Items.ENCHANTED_GOLDEN_APPLE, EMERALD_APPLE);
		});
		FuelRegistry.INSTANCE.add(EXPLOSION_BOW, 300);
		FuelRegistry.INSTANCE.add(TELEPORT_BOW, 300);
		FuelRegistry.INSTANCE.add(FIREWORK_ROCKET_BOW, 300);
		FuelRegistry.INSTANCE.add(VILLAGER_BOW, 300);
		FuelRegistry.INSTANCE.add(DIAMOND_BOW, 300);
	}

}
