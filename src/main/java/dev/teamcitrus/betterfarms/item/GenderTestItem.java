package dev.teamcitrus.betterfarms.item;

import dev.teamcitrus.betterfarms.data.BFStatsListener;
import dev.teamcitrus.betterfarms.registry.AttachmentRegistry;
import dev.teamcitrus.betterfarms.util.AnimalGenders;
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
            if (interactionTarget instanceof Animal animal && BFStatsListener.newMap.containsKey(animal.getType())) {
                AnimalGenders gender = animal.getData(AttachmentRegistry.ANIMAL).getGender();
                player.displayClientMessage(Component.literal("Animal is: " + StringUtils.capitalize(gender.getId())), true);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }
}
