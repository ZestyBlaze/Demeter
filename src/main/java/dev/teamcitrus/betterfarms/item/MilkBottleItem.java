package dev.teamcitrus.betterfarms.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.EffectCure;
import net.neoforged.neoforge.common.EffectCures;

public class MilkBottleItem extends MilkBucketItem {
    public MilkBottleItem() {
        super(new Item.Properties().stacksTo(16));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving) {
        super.finishUsingItem(pStack, pLevel, pEntityLiving);
        if (pEntityLiving instanceof ServerPlayer serverplayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverplayer, pStack);
            serverplayer.awardStat(Stats.ITEM_USED.get(this));
        }

        if (!pLevel.isClientSide) {
            pEntityLiving.removeEffectsCuredBy(EffectCures.MILK);
        }

        if (pStack.isEmpty()) {
            return new ItemStack(Items.GLASS_BOTTLE);
        } else {
            if (pEntityLiving instanceof Player player && !player.getAbilities().instabuild) {
                ItemStack itemstack = new ItemStack(Items.GLASS_BOTTLE);
                if (!player.getInventory().add(itemstack)) {
                    player.drop(itemstack, false);
                }
            }

            return pStack;
        }
    }
}
