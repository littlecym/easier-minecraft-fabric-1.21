package com.easier_minecraft.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.village.TradeOffer;

@Mixin(TradeOffer.class)
public class TradeOfferMixin {
    
    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void alwaysTrade(CallbackInfo ci) {
        ci.cancel();
    }

}
