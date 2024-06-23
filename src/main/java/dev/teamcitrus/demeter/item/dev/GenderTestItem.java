package dev.teamcitrus.demeter.item.dev;

import dev.teamcitrus.citruslib.item.CitrusItem;
import dev.teamcitrus.demeter.attachment.AnimalAttachment.AnimalGenders;
import dev.teamcitrus.demeter.util.AnimalUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.StringUtils;


public class GenderTestItem extends CitrusItem {
    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity interactionTarget, InteractionHand usedHand) {
        if (!interactionTarget.level().isClientSide()) {
            if (interactionTarget instanceof Animal animal) {
                AnimalGenders gender = AnimalUtil.getAnimalData(animal).getGender();
                player.displayClientMessage(Component.literal("Animal is: " + StringUtils.capitalize(gender.getId())), true);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }
}
