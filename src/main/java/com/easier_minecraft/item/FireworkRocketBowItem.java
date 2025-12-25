package com.easier_minecraft.item;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FireworksComponent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.Unit;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class FireworkRocketBowItem extends RangedWeaponItem {
	public FireworkRocketBowItem(Item.Settings settings) {
		super(settings);
	}

	@Override
	public boolean hasGlint(ItemStack stack) {
		return true;
	}

	private ItemStack getArrow(ItemStack stack, ItemStack projectileStack) {
		ItemStack itemStack = projectileStack.copyWithCount(1);
		itemStack.set(DataComponentTypes.INTANGIBLE_PROJECTILE, Unit.INSTANCE);
		return itemStack;
	}

	private List<ItemStack> loadArrow(ItemStack stack, ItemStack projectileStack, LivingEntity shooter) {
		if (projectileStack.isEmpty()) {
			return List.of();
		} else {
			int i = shooter.getWorld() instanceof ServerWorld serverWorld
					? EnchantmentHelper.getProjectileCount(serverWorld, stack, shooter, 1)
					: 1;
			@SuppressWarnings({ "rawtypes", "unchecked" })
			List<ItemStack> list = new ArrayList(i);
			ItemStack itemStack = projectileStack.copy();

			for (int j = 0; j < i; j++) {
				ItemStack itemStack2 = getArrow(stack, j == 0 ? projectileStack : itemStack);
				if (!itemStack2.isEmpty()) {
					list.add(itemStack2);
				}
			}

			return list;
		}
	}

	@Override
	public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
		if (user instanceof PlayerEntity playerEntity) {
			ItemStack itemStack = playerEntity.getProjectileType(stack);
			if (!itemStack.isEmpty()) {
				int time = this.getMaxUseTime(stack, user) - remainingUseTicks;
				float t = getPullProgress(time);
				List<ItemStack> list = loadArrow(stack, itemStack, playerEntity);
				if (!world.isClient && !list.isEmpty() && user.isFallFlying()) {
					byte duration = 0;
					if (t < 0.65)
						duration = 1;
					else if (t < 0.9)
						duration = 2;
					else
						duration = 3;
					ItemStack firework = new ItemStack(Items.FIREWORK_ROCKET);
					firework.set(DataComponentTypes.FIREWORKS, new FireworksComponent(
							duration, List.of()));
					FireworkRocketEntity fireworkRocketEntity = new FireworkRocketEntity(world, firework, user);
					world.spawnEntity(fireworkRocketEntity);
					world.playSound(
							null,
							playerEntity.getX(),
							playerEntity.getY(),
							playerEntity.getZ(),
							SoundEvents.ENTITY_ARROW_SHOOT,
							SoundCategory.PLAYERS,
							1.0F,
							1.0F / (world.getRandom().nextFloat() * 0.4F + 1.2F) + t * 0.5F);
					playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
				}
			}
		}
	}

	@Override
	protected void shoot(LivingEntity shooter, ProjectileEntity projectile, int index, float speed, float divergence,
			float yaw, @Nullable LivingEntity target) {
		projectile.setVelocity(shooter, shooter.getPitch(), shooter.getYaw() + yaw, 0.0F, speed, divergence);
	}

	public static float getPullProgress(int useTicks) {
		float f = useTicks / 20.0F;
		f = (f * f + f * 2.0F) / 3.0F;
		if (f > 1.0F) {
			f = 1.0F;
		}

		return f;
	}

	@Override
	public int getMaxUseTime(ItemStack stack, LivingEntity user) {
		return 72000;
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BOW;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		boolean bl = !user.getProjectileType(itemStack).isEmpty();
		if (!user.isInCreativeMode() && !bl) {
			return TypedActionResult.fail(itemStack);
		} else {
			user.setCurrentHand(hand);
			return TypedActionResult.consume(itemStack);
		}
	}

	@Override
	public Predicate<ItemStack> getProjectiles() {
		return BOW_PROJECTILES;
	}

	@Override
	public int getRange() {
		return 15;
	}
}
