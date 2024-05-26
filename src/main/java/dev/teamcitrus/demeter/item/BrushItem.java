package dev.teamcitrus.demeter.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BrushItem extends Item {
    public BrushItem() {
        super(new Properties().stacksTo(1).durability(156));
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack pStack, Player pPlayer, LivingEntity pInteractionTarget, InteractionHand pUsedHand) {
        if (pInteractionTarget instanceof Animal animal) {
            Level level = pPlayer.level();
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }
}
