package dev.teamcitrus.demeter.item;

import dev.teamcitrus.citruslib.item.CitrusItem;
import dev.teamcitrus.demeter.attachment.AnimalAttachment;
import dev.teamcitrus.demeter.data.loaders.NamesLoader;
import dev.teamcitrus.demeter.util.AnimalUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class AnimalTagItem extends CitrusItem {
    public AnimalTagItem() {
        super(new Properties().stacksTo(16));
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack pStack, Player pPlayer, LivingEntity pInteractionTarget, InteractionHand pUsedHand) {
        if (!pPlayer.level().isClientSide() && pInteractionTarget instanceof Animal animal) {
            if (pPlayer.getName().getString().equals("ElysiaSilly") || pPlayer.getName().getString().equals("blerbedoob")) {
                if (pPlayer.level().random.nextInt(100) >= 15) {
                    animal.setCustomName(Component.literal("Pippi"));
                    return InteractionResult.SUCCESS;
                }
            }

            AnimalAttachment.AnimalGenders gender = AnimalUtil.getGender(animal);
            List<String> possibleNames = NamesLoader.NAME_LIST.get(gender);
            String name = possibleNames.get(pPlayer.level().random.nextInt(possibleNames.size()));

            animal.setCustomName(Component.literal(name));

            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }
}
