package dev.teamcitrus.betterfarms.item.dev;

import dev.teamcitrus.betterfarms.BetterFarms;
import dev.teamcitrus.betterfarms.test.TestRegistry;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RegistryTestItem extends Item {
    public RegistryTestItem() {
        super(new Properties());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        if (!pLevel.isClientSide()) {
            BetterFarms.LOGGER.error(TestRegistry.INSTANCE.getValues());
            return InteractionResultHolder.success(stack);
        }
        return InteractionResultHolder.fail(stack);
    }
}
