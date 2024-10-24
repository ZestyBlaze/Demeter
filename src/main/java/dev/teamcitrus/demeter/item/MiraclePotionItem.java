package dev.teamcitrus.demeter.item;

import dev.teamcitrus.citruslib.item.CitrusItem;
import dev.teamcitrus.demeter.attachment.AnimalAttachment;
import dev.teamcitrus.demeter.registry.AdvancementRegistry;
import dev.teamcitrus.demeter.util.AnimalUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class MiraclePotionItem extends CitrusItem {
    public MiraclePotionItem() {
        super(new Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(1));
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity interactionTarget, InteractionHand usedHand) {
        if (!player.level().isClientSide() && interactionTarget instanceof Animal animal) {
            if (interactionTarget instanceof Frog frog) {
                AdvancementRegistry.USE_MIRACLE_ON_FROG.get().trigger(player);
                if (AnimalUtil.getAnimalData(frog).getGender().equals(AnimalAttachment.AnimalGenders.FEMALE)) {
                    ((ServerLevel)(animal.level())).sendParticles(ParticleTypes.HEART, animal.getX(), animal.getY() + 0.7, animal.getZ(), 4, 0.5, 0, 0.5, animal.getRandom().nextGaussian() * 0.02);
                    ((ServerPlayer)player).connection.send(new ClientboundSoundPacket(Holder.direct(SoundEvents.BOTTLE_EMPTY), SoundSource.PLAYERS, animal.getX(), animal.getY(), animal.getZ(), 1.0f, 1.0f, 0));
                    frog.getBrain().setMemory(MemoryModuleType.IS_PREGNANT, Unit.INSTANCE);
                }
                return InteractionResult.SUCCESS;
            }

            if (AnimalUtil.getStats(animal) != null && AnimalUtil.getAnimalData(animal).getGender().equals(AnimalAttachment.AnimalGenders.FEMALE)) {
                if (!player.isCreative()) {
                    stack.shrink(1);
                }
                ((ServerLevel)(animal.level())).sendParticles(ParticleTypes.HEART, animal.getX(), animal.getY() + 0.7, animal.getZ(), 4, 0.5, 0, 0.5, animal.getRandom().nextGaussian() * 0.02);
                ((ServerPlayer)player).connection.send(new ClientboundSoundPacket(Holder.direct(SoundEvents.BOTTLE_EMPTY), SoundSource.PLAYERS, animal.getX(), animal.getY(), animal.getZ(), 1.0f, 1.0f, 0));
                AdvancementRegistry.USE_MIRACLE_POTION.get().trigger(player);
                AnimalUtil.getAnimalData(animal).setPregnant(animal, true, animal);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }
}
