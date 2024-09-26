package dev.teamcitrus.demeter.item;

import dev.teamcitrus.citruslib.item.CitrusItem;
import dev.teamcitrus.demeter.config.DemeterConfig;
import dev.teamcitrus.demeter.enchantment.DemeterEnchantments;
import dev.teamcitrus.demeter.registry.AdvancementRegistry;
import dev.teamcitrus.demeter.util.AnimalUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerLevel;
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

import java.util.Optional;

public class BrushItem extends CitrusItem {
    public BrushItem() {
        super(new Properties().stacksTo(1).durability(156));
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack pStack, Player pPlayer, LivingEntity pInteractionTarget, InteractionHand pUsedHand) {
        if (!pPlayer.level().isClientSide() && pInteractionTarget instanceof Animal animal) {
            if (!AnimalUtil.getAnimalData(animal).hasBeenBrushedToday()) {
                ServerPlayer serverPlayer = (ServerPlayer) pPlayer;
                ServerLevel level = (ServerLevel)serverPlayer.level();
                serverPlayer.connection.send(new ClientboundSoundPacket(Holder.direct(SoundEvents.BRUSH_GENERIC), SoundSource.PLAYERS, animal.getX(), animal.getY(), animal.getZ(), 1.0f, 1.0f, 0));
                level.sendParticles(ParticleTypes.HEART, animal.getX(), animal.getY() + 0.7, animal.getZ(), 4, 0.5, 0, 0.5, animal.getRandom().nextGaussian() * 0.02);

                int comfortLevel = pStack.getEnchantmentLevel(pPlayer.level().registryAccess().holderOrThrow(DemeterEnchantments.COMFORT));
                int spiteLevel = pStack.getEnchantmentLevel(pPlayer.level().registryAccess().holderOrThrow(DemeterEnchantments.SPITE));
                int love = DemeterConfig.brushingLoveValue.get();

                if (comfortLevel > 0) {
                    love += (comfortLevel * DemeterConfig.comfortBonusPerLevel.get());
                }

                if (spiteLevel > 0) {
                    switch (DemeterConfig.spiteEffect.get()) {
                        case HALVE -> love *= (int) getSpiteHalving(spiteLevel);
                        case INVERT -> love *= -1;
                        case REDUCE -> love -= (spiteLevel * DemeterConfig.loveLossPerSpiteLevel.get());
                    }
                    AdvancementRegistry.SPITEFUL_BRUSHED.get().trigger(serverPlayer);
                } else {
                    AdvancementRegistry.BRUSHED.get().trigger(serverPlayer);
                }

                AnimalUtil.getAnimalData(animal).setHasBeenBrushedToday(true);
                AnimalUtil.getAnimalData(animal).alterLove(Optional.of(serverPlayer), love);
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

    private float getSpiteHalving(int spiteLevel) {
        return switch (spiteLevel) {
            case 1 -> 0.75f;
            case 2 -> 0.5f;
            case 3 -> 0.25f;
            default -> throw new IllegalStateException("Unexpected value: " + spiteLevel);
        };
    }
}
