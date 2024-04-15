package dev.teamcitrus.betterfarms.item.dev;

import dev.teamcitrus.betterfarms.api.util.AnimalUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class LoveCheckerItem extends Item {
    public LoveCheckerItem() {
        super(new Properties());
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity interactionTarget, InteractionHand usedHand) {
        if (!interactionTarget.level().isClientSide()) {
            if (interactionTarget instanceof Animal animal) {
                if (!player.isCrouching()) {
                    int value = AnimalUtil.getAnimalData(animal).getLove();
                    player.displayClientMessage(Component.literal("Love is is: " + value), true);
                } else {
                    AnimalUtil.getAnimalData(animal).setLove(50);
                    player.displayClientMessage(Component.literal("Changed love value"), true);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }
}
