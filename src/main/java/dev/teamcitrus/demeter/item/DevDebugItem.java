package dev.teamcitrus.demeter.item;

import dev.teamcitrus.citruslib.item.CitrusItem;
import dev.teamcitrus.demeter.attachment.AnimalAttachment;
import dev.teamcitrus.demeter.util.AnimalUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;

public class DevDebugItem extends CitrusItem {
    public DevDebugItem() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity interactionTarget, InteractionHand usedHand) {
        Level level = player.level();
        if (!level.isClientSide() && interactionTarget instanceof Animal animal) {
            AnimalAttachment data = AnimalUtil.getAnimalData(animal);

            player.sendSystemMessage(Component.literal(
                    "Animal's Data")
                    .append("\nUUID: " + animal.getUUID())
                    .append("\nGender: " + StringUtils.capitalize(data.getGender().name().toLowerCase(Locale.ROOT)))
                    .append("\nPregnant: " + data.getPregnant())
                    .append("\nLove: " + data.getLove())
                    .append("\nPet Today?: " + data.hasBeenPetToday())
                    .append("\nBrushed Today?: " + data.hasBeenBrushedToday())
                    .append("\nFed Today?: " + data.hasBeenFedToday())
                    .append("\nDays Since Fed: " + data.getDaysSinceFed())
                    .withStyle(ChatFormatting.AQUA)
            );
        }
        return InteractionResult.FAIL;
    }
}
