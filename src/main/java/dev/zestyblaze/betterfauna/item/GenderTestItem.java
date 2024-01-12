package dev.zestyblaze.betterfauna.item;

import dev.zestyblaze.betterfauna.component.animal.AnimalComponent;
import dev.zestyblaze.betterfauna.registry.ComponentRegistry;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
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
        super(new FabricItemSettings());
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity interactionTarget, InteractionHand usedHand) {
        if (!interactionTarget.level().isClientSide()) {
            if (interactionTarget instanceof Animal animal) {
                AnimalComponent.Gender gender = animal.getComponent(ComponentRegistry.ANIMAL).getGender();
                player.displayClientMessage(Component.literal("Animal is: " + StringUtils.capitalize(gender.getId())), true);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }
}
