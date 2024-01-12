package dev.zestyblaze.betterfauna.item;

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

public class PregnancyTestItem extends Item {
    public PregnancyTestItem() {
        super(new FabricItemSettings());
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity interactionTarget, InteractionHand usedHand) {
        if (!interactionTarget.level().isClientSide()) {
            if (interactionTarget instanceof Animal animal) {
                boolean value = animal.getComponent(ComponentRegistry.PREGNANCY).getPregnant();
                player.displayClientMessage(Component.literal("Pregnancy is: " + StringUtils.capitalize(String.valueOf(value))), true);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }
}
