package com.easier_minecraft.mixin;


import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.easier_minecraft.register.EnchantmentRegister;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ProfileComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.GameMode;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
    @Unique
    private ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;

    private boolean shouldTeleport() {
        return player.interactionManager.getGameMode() == GameMode.SURVIVAL
                || player.interactionManager.getGameMode() == GameMode.ADVENTURE;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        if (shouldTeleport() && player.getY() < player.getWorld().getBottomY() - 32) {
            int voidSalvationLevel = 0;
            RegistryEntry.Reference<Enchantment> voidSalvationEntry = player.getWorld().getRegistryManager()
                    .get(RegistryKeys.ENCHANTMENT)
                    .getEntry(EnchantmentRegister.SONIC_GUARD)
                    .orElse(null);
            if (voidSalvationEntry != null) {
                for (ItemStack armorStack : player.getArmorItems()) {
                    voidSalvationLevel += EnchantmentHelper.getLevel(voidSalvationEntry, armorStack);
                }
            }
            if (voidSalvationLevel > 0 && player.getWorld() instanceof ServerWorld) {
                player.teleport(
                        (ServerWorld) player.getWorld(),
                        player.getX(),
                        player.getWorld().getTopY() + 32,
                        player.getZ(),
                        player.getYaw(),
                        player.getPitch());
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 100, 0));
            }
        }
    }

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void invulnerableToVoid(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (source.isOf(DamageTypes.OUT_OF_WORLD) && !shouldTeleport()) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }

    @Inject(method = "onDeath", at = @At("HEAD"))
    private void dropPlayerHead(DamageSource damageSource, CallbackInfo ci) {
        if (player.isCreative() || player.isSpectator()) {
            return;
        }
        ItemStack skull = new ItemStack(Items.PLAYER_HEAD);
        ProfileComponent profileComponent = new ProfileComponent(player.getGameProfile());
        skull.set(DataComponentTypes.PROFILE, profileComponent);
        if (!player.getInventory().insertStack(skull)) {
            ItemEntity playerHeadEntity = new ItemEntity(
                    player.getWorld(),
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    skull);
            player.getWorld().spawnEntity(playerHeadEntity);
        }
    }

}
