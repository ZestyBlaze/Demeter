package dev.teamcitrus.betterfarms.item.dev;

import dev.teamcitrus.betterfarms.api.util.AnimalUtil;
import dev.teamcitrus.betterfarms.attachment.AnimalAttachment.AnimalGenders;
import dev.teamcitrus.betterfarms.data.BFStatsManager;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.StringUtils;

public class GenderTestItem extends Item {
    public GenderTestItem() {
        super(new Item.Properties());
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity interactionTarget, InteractionHand usedHand) {
        if (!interactionTarget.level().isClientSide()) {
            if (interactionTarget instanceof Animal animal && BFStatsManager.newMap.containsKey(animal.getType())) {
                AnimalGenders gender = AnimalUtil.getAnimalData(animal).getGender();
                player.displayClientMessage(Component.literal("Animal is: " + StringUtils.capitalize(gender.getId())), true);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }
}
