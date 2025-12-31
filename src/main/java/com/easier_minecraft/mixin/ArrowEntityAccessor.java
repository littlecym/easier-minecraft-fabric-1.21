package com.easier_minecraft.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.entity.projectile.ArrowEntity;

@Mixin(ArrowEntity.class)
public interface ArrowEntityAccessor {

    @Accessor("multiDamage")
    public boolean getMultiDamage();

    @Accessor("multiDamage")
    public void setMultiDamage(boolean multiDamage);

}
