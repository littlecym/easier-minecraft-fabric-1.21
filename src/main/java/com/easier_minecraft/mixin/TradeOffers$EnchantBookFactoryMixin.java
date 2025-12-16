package com.easier_minecraft.mixin;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.EnchantmentTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;

@Mixin(targets = "net.minecraft.village.TradeOffers$EnchantBookFactory")
public class TradeOffers$EnchantBookFactoryMixin {
    @Shadow
    private int experience;
    @Shadow
    private TagKey<Enchantment> possibleEnchantments;

    @Inject(method = "create", at = @At(value = "HEAD"), cancellable = true)
    private void forceMaxLevel(Entity entity, Random random, CallbackInfoReturnable<TradeOffer> cir) {
        Optional<RegistryEntry<Enchantment>> optional = entity.getWorld()
                .getRegistryManager()
                .get(RegistryKeys.ENCHANTMENT)
                .getRandomEntry(this.possibleEnchantments, random);

        if (optional.isPresent()) {
            RegistryEntry<Enchantment> registryEntry = optional.get();
            Enchantment enchantment = registryEntry.value();
            int maxLevel = enchantment.getMaxLevel();
            ItemStack itemStack = EnchantedBookItem
                    .forEnchantment(new EnchantmentLevelEntry(registryEntry, maxLevel));

            int price = 2 + random.nextInt(5 + maxLevel * 10) + 3 * maxLevel;
            if (registryEntry.isIn(EnchantmentTags.DOUBLE_TRADE_PRICE)) {
                price *= 2;
            }
            if (price > 64) {
                price = 64;
            }

            TradeOffer offer = new TradeOffer(
                    new TradedItem(Items.EMERALD, price),
                    Optional.of(new TradedItem(Items.BOOK)),
                    itemStack,
                    12, this.experience, 0.2F);
            cir.setReturnValue(offer);
            cir.cancel();
        }
    }
}
