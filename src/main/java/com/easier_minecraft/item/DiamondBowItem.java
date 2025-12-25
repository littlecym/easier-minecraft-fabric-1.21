package com.easier_minecraft.item;

import java.util.List;
import com.easier_minecraft.register.EnchantmentRegister;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;

public class DiamondBowItem extends BowItem {

	public DiamondBowItem(Settings settings) {
		super(settings);
	}

	@Override
	public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
		if (user instanceof PlayerEntity playerEntity) {
			ItemStack itemStack = playerEntity.getProjectileType(stack);
			if (!itemStack.isEmpty()) {
				int i = this.getMaxUseTime(stack, user) - remainingUseTicks;
				float f = getPullProgress(i, stack);
				if (!(f < 0.1)) {
					List<ItemStack> list = load(stack, itemStack, playerEntity);
					if (world instanceof ServerWorld serverWorld && !list.isEmpty()) {
						this.shootAll(serverWorld, playerEntity, playerEntity.getActiveHand(), stack, list, f * 3.0F,
								0.0F, f == 1.0F, null);
					}

					world.playSound(
							null,
							playerEntity.getX(),
							playerEntity.getY(),
							playerEntity.getZ(),
							SoundEvents.ENTITY_ARROW_SHOOT,
							SoundCategory.PLAYERS,
							1.0F,
							1.0F / (world.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
					playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
				}
			}
		}
	}

	public static float getPullProgress(int useTicks, ItemStack stack) {
		float f = useTicks * (getQuickDrawLevel(stack) + 1) / 20.0F;
		f = (f * f + f * 2.0F) / 2.0F;
		if (f > 1.0F) {
			f = 1.0F;
		}
		return f;
	}
	
	public static int getQuickDrawLevel(ItemStack stack) {
		return EnchantmentHelper.getLevel(EnchantmentRegister.QUICK_DRAW_ENTRY, stack);
	}

}
