package dev.teamcitrus.betterfarms.item;

import dev.teamcitrus.betterfarms.data.BFStatsListener;
import net.minecraft.network.chat.Component;
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
        if (BFStatsListener.newMap.containsKey(pInteractionTarget.getType())) {
            pPlayer.displayClientMessage(Component.literal("Total of " +
                    BFStatsListener.getManager(pInteractionTarget).daysPregnant()
                    + " days pregnant"), true
            );
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }
}
