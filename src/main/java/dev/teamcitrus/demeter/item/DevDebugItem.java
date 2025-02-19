package dev.teamcitrus.demeter.item;

import dev.teamcitrus.demeter.attachment.AnimalAttachment;
import dev.teamcitrus.demeter.util.AnimalUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;

import static dev.teamcitrus.demeter.registry.ItemRegistry.createID;

public class DevDebugItem extends Item {
    public DevDebugItem() {
        super(new Properties().stacksTo(1).setId(createID("dev_debug_item")));
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity interactionTarget, InteractionHand usedHand) {
        Level level = player.level();
        if (!level.isClientSide() && interactionTarget instanceof Animal animal && player instanceof ServerPlayer serverPlayer) {
            AnimalAttachment data = AnimalUtil.getAnimalData(animal);

            serverPlayer.sendSystemMessage(Component.literal(
                    "Animal's Data")
                    .append("\nUUID: " + animal.getUUID())
                    .append("\nSex: " + StringUtils.capitalize(data.getSex().name().toLowerCase(Locale.ROOT)))
                    .append("\nPregnant: " + data.isPregnant())
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
