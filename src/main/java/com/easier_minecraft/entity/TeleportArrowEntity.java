package com.easier_minecraft.entity;

import org.jetbrains.annotations.Nullable;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;

public class TeleportArrowEntity extends PersistentProjectileEntity {
    public TeleportArrowEntity(EntityType<? extends TeleportArrowEntity> entityType, World world) {
        super(entityType, world);
        this.pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED;
    }

    public TeleportArrowEntity(World world, double x, double y, double z, ItemStack stack,
            @Nullable ItemStack shotFrom) {
        super(EntityType.ARROW, x, y, z, world, stack, shotFrom);
        this.pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED;
    }

    public TeleportArrowEntity(World world, LivingEntity owner, ItemStack stack, @Nullable ItemStack shotFrom) {
        super(EntityType.ARROW, owner, world, stack, shotFrom);
        this.pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED;
    }

    protected double getGravity() {
        return 0.03;
    }

    private void teleport() {
        if (this.getOwner().hasVehicle()) {
            this.getOwner().detach();
        }
        if (this.getWorld() instanceof ServerWorld) {
            this.getOwner()
                    .teleportTo(new TeleportTarget((ServerWorld) this.getWorld(), this.getPos(),
                            this.getOwner().getVelocity(), this.getOwner().getYaw(), this.getOwner().getPitch(),
                            TeleportTarget.NO_OP));
            this.getOwner().onLanding();
            this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_PLAYER_TELEPORT,
                    SoundCategory.PLAYERS);
        }
        this.discard();
    }

    protected void onBlockHit(BlockHitResult blockHitResult) {
        teleport();
    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        teleport();
    }

    protected ItemStack getDefaultItemStack() {
        return new ItemStack(Items.ARROW);
    }

    public void tick() {
        super.tick();
        if (!this.isRemoved() && this.isInFluid()) {
            teleport();
        }
    }

}