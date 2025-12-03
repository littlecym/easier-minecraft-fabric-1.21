package com.easier_minecraft.entity;

import org.jetbrains.annotations.Nullable;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class VillagerArrowEntity extends PersistentProjectileEntity {
    public VillagerArrowEntity(EntityType<? extends VillagerArrowEntity> entityType, World world) {
        super(entityType, world);
        this.pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED;
    }

    public VillagerArrowEntity(World world, double x, double y, double z, ItemStack stack,
            @Nullable ItemStack shotFrom) {
        super(EntityType.ARROW, x, y, z, world, stack, shotFrom);
        this.pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED;
    }

    public VillagerArrowEntity(World world, LivingEntity owner, ItemStack stack, @Nullable ItemStack shotFrom) {
        super(EntityType.ARROW, owner, world, stack, shotFrom);
        this.pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED;
    }

    protected double getGravity() {
        return 0.03;
    }

    private void summonVillager() {
        if (this.getWorld() instanceof ServerWorld) {
            EntityType.VILLAGER.spawn((ServerWorld)this.getWorld(), this.getBlockPos(), SpawnReason.SPAWN_EGG);
        }
    }

    protected void onBlockHit(BlockHitResult blockHitResult) {
        summonVillager();
        this.discard();
    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        summonVillager();
        this.discard();
    }

    protected ItemStack getDefaultItemStack() {
        return new ItemStack(Items.ARROW);
    }

    public void tick() {
        super.tick();
        if (!this.isRemoved() && this.isInFluid()) {
            this.discard();
        }
    }
}
