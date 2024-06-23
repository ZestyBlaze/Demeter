package dev.teamcitrus.demeter.item.dev;

import dev.teamcitrus.citruslib.item.CitrusItem;
import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.data.NamesLoader;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MiscTestItem extends CitrusItem {
    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        if (!pLevel.isClientSide()) {
            Demeter.LOGGER.info(NamesLoader.MALE_NAMES);
            Demeter.LOGGER.info(NamesLoader.FEMALE_NAMES);
        }
        return InteractionResultHolder.fail(stack);
    }
}
