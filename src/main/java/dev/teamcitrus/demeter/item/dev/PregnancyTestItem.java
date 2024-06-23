package dev.teamcitrus.demeter.item.dev;

import dev.teamcitrus.citruslib.item.CitrusItem;
import dev.teamcitrus.demeter.util.AnimalUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.StringUtils;

public class PregnancyTestItem extends CitrusItem {
    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity interactionTarget, InteractionHand usedHand) {
        if (!interactionTarget.level().isClientSide()) {
            if (interactionTarget instanceof Animal animal && AnimalUtil.getStats(animal).isBound()) {
                boolean value = AnimalUtil.getAnimalData(animal).getPregnant();
                player.displayClientMessage(Component.literal("Pregnancy is: " + StringUtils.capitalize(String.valueOf(value))), true);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }
}
