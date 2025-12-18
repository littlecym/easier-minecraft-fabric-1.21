package com.easier_minecraft.mixin;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.easier_minecraft.register.EnchantmentRegister;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        if (player.getY() < player.getWorld().getBottomY() - 32) {
            Optional<RegistryEntry.Reference<Enchantment>> voidSalvationEntry = player.getWorld().getRegistryManager()
                    .getWrapperOrThrow(RegistryKeys.ENCHANTMENT)
                    .getOptional(EnchantmentRegister.VOID_SALVATION);
            if (voidSalvationEntry.isEmpty()) {
                return;
            }
            int voidSalvationLevel = 0;
            for (ItemStack armorStack : player.getArmorItems()) {
                voidSalvationLevel += EnchantmentHelper.getLevel(voidSalvationEntry.get(), armorStack);
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

}
