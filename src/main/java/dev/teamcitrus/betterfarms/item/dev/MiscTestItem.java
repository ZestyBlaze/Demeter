package dev.teamcitrus.betterfarms.item.dev;

import dev.teamcitrus.betterfarms.BetterFarms;
import dev.teamcitrus.betterfarms.data.NamesLoader;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MiscTestItem extends Item {
    public MiscTestItem() {
        super(new Item.Properties());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        if (!pLevel.isClientSide()) {
            BetterFarms.LOGGER.info(NamesLoader.MALE_NAMES);
            BetterFarms.LOGGER.info(NamesLoader.FEMALE_NAMES);
        }
        return InteractionResultHolder.fail(stack);
    }
}
