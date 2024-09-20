package dev.teamcitrus.demeter.item;

import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.EffectCures;

import java.util.ArrayList;
import java.util.List;

public class MilkBottleItem extends MilkBucketItem {
    public MilkBottleItem() {
        super(new Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving) {
        if (entityLiving instanceof ServerPlayer serverplayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverplayer, stack);
            serverplayer.awardStat(Stats.ITEM_USED.get(this));
        }

        if (!level.isClientSide) {
            List<Holder<MobEffect>> negativeEffects = new ArrayList<>();
            entityLiving.getActiveEffects().forEach(mobEffectInstance -> {
                 if (mobEffectInstance.getEffect().value().getCategory().equals(MobEffectCategory.HARMFUL) && mobEffectInstance.getCures().contains(EffectCures.MILK)) {
                     negativeEffects.add(mobEffectInstance.getEffect());
                 }
            });

            if (!negativeEffects.isEmpty()) {
                Holder<MobEffect> mobEffect = negativeEffects.get(level.random.nextInt(negativeEffects.size()));
                entityLiving.removeEffect(mobEffect);
                negativeEffects.clear();
            }
        }

        if (entityLiving instanceof Player player) {
            return ItemUtils.createFilledResult(stack, player, new ItemStack(Items.GLASS_BOTTLE), false);
        } else {
            stack.consume(1, entityLiving);
            return stack;
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("item.demeter.milk_bottle.desc").withStyle(ChatFormatting.DARK_GRAY));
    }
}
