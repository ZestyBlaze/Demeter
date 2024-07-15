package dev.teamcitrus.demeter.item;

import dev.teamcitrus.citruslib.item.CitrusItem;
import dev.teamcitrus.demeter.registry.AdvancementRegistry;
import dev.teamcitrus.demeter.util.AnimalUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class BrushItem extends CitrusItem {
    public BrushItem() {
        super(new Properties().stacksTo(1).durability(156));
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack pStack, Player pPlayer, LivingEntity pInteractionTarget, InteractionHand pUsedHand) {
        if (!pPlayer.level().isClientSide() && pInteractionTarget instanceof Animal animal) {
            if (!AnimalUtil.getAnimalData(animal).hasBeenBrushedToday()) {
                ServerPlayer serverPlayer = (ServerPlayer) pPlayer;
                serverPlayer.connection.send(new ClientboundSoundPacket(Holder.direct(SoundEvents.BRUSH_GENERIC), SoundSource.PLAYERS, animal.getX(), animal.getY(), animal.getZ(), 1.0f, 1.0f, 0));
                AnimalUtil.getAnimalData(animal).setHasBeenBrushedToday(true);
                AnimalUtil.getAnimalData(animal).alterLove(10);
                AdvancementRegistry.BRUSHED.get().trigger(serverPlayer);
                pStack.hurtAndBreak(1, pPlayer, EquipmentSlot.MAINHAND);
                return InteractionResult.SUCCESS;
            } else {
                pPlayer.displayClientMessage(
                        Component.translatable("message.demeter.brush.fail_daily")
                                .withStyle(ChatFormatting.RED), true
                );
            }
        }
        return InteractionResult.FAIL;
    }
}
