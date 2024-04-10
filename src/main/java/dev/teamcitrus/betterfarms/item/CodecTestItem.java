package dev.teamcitrus.betterfarms.item;

import dev.teamcitrus.betterfarms.BetterFarms;
import dev.teamcitrus.betterfarms.data.AnimalStats;
import dev.teamcitrus.betterfarms.data.BFStatsManager;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class CodecTestItem extends Item {
    public CodecTestItem() {
        super(new Item.Properties());
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack pStack, Player pPlayer, LivingEntity pInteractionTarget, InteractionHand pUsedHand) {
        if (BFStatsManager.newMap.containsKey(pInteractionTarget.getType()) && !pPlayer.level().isClientSide()) {
            AnimalStats stats = BFStatsManager.getStats(pInteractionTarget);
            int min = stats.minChildrenPerBirth();
            int max = stats.maxChildrenPerBirth();
            RandomSource randomSource = pPlayer.getRandom();
            try {
                int value = randomSource.nextIntBetweenInclusive(min, max);
                BetterFarms.LOGGER.info(value);
            } catch (IllegalArgumentException e) {
                BetterFarms.LOGGER.error(Component.translatable("error.betterfarms.maxhighermin").getString());
            }

            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }
}
