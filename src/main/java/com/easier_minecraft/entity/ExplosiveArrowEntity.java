package com.easier_minecraft.entity;

import org.jetbrains.annotations.Nullable;

import com.easier_minecraft.misc.KeepItemEntityExplosion;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import net.minecraft.world.World.ExplosionSourceType;
import net.minecraft.world.explosion.Explosion;

public class ExplosiveArrowEntity extends PersistentProjectileEntity {
    private static float power = 0.0F;
    private static boolean doBreakBlock = false;

    public ExplosiveArrowEntity(EntityType<? extends ExplosiveArrowEntity> entityType, World world,
            boolean type) {
        super(entityType, world);
        doBreakBlock = type;
        this.pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED;
    }

    public ExplosiveArrowEntity(World world, double x, double y, double z, ItemStack stack,
            @Nullable ItemStack shotFrom, boolean type) {
        super(EntityType.ARROW, x, y, z, world, stack, shotFrom);
        doBreakBlock = type;
        this.pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED;
    }

    public ExplosiveArrowEntity(World world, LivingEntity owner, ItemStack stack, @Nullable ItemStack shotFrom,
            boolean type) {
        super(EntityType.ARROW, owner, world, stack, shotFrom);
        doBreakBlock = type;
        this.pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED;
    }

    protected double getGravity() {
        if (doBreakBlock)
            return 0.00F;
        return 0.03F;
    }

    private void getExplosionPower() {
        float dist = distanceTo(this.getOwner());
        if (doBreakBlock) {
            if (dist >= 6.0F)
                power = 4.0F;
            else
                power = 0.0F;
            return;
        }
        if (dist >= 8.0F) {
            power = (float) Math.min(16.0F, 2.0F + Math.sqrt(dist - 4.0F));
        } else if (dist >= 4.0F) {
            power = dist - 4.0F;
        } else {
            power = 0.0F;
        }
    }

    private DamageSource getShooter() {
        return this.getOwner() == null ? null : this.getDamageSources().explosion(this, this.getOwner());
    }

    private void createExplosion() {
        getExplosionPower();
        if (doBreakBlock) {
            this.getWorld().createExplosion(this, this.getShooter(), null, this.getX(), this.getY(), this.getZ(), power,
                    false,
                    ExplosionSourceType.TNT, true, ParticleTypes.EXPLOSION,
                    ParticleTypes.EXPLOSION_EMITTER,
                    SoundEvents.ENTITY_GENERIC_EXPLODE);
            this.discard();
            return;
        }
        Explosion.DestructionType destructionType = Explosion.DestructionType.KEEP;
        KeepItemEntityExplosion explosion = new KeepItemEntityExplosion(this.getWorld(), this, this.getShooter(), null,
                this.getX(), this.getY(), this.getZ(), power, false, destructionType, ParticleTypes.EXPLOSION,
                ParticleTypes.EXPLOSION_EMITTER,
                SoundEvents.ENTITY_GENERIC_EXPLODE);
        explosion.collectBlocksAndDamageEntities();
        explosion.affectWorld(true);
        this.discard();
    }

    protected void onBlockHit(BlockHitResult blockHitResult) {
        createExplosion();
    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        createExplosion();
    }

    protected ItemStack getDefaultItemStack() {
        return new ItemStack(Items.ARROW);
    }

    public void tick() {
        super.tick();
        if (!this.isRemoved() && this.isInFluid()) {
            this.getWorld().createExplosion(this, null, null, this.getX(), this.getY(), this.getZ(), 0.0F, false,
                    World.ExplosionSourceType.NONE);
            this.discard();
        }
    }

}