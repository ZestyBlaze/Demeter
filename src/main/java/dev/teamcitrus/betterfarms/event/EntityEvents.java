package dev.teamcitrus.betterfarms.event;

import dev.teamcitrus.betterfarms.BetterFarms;
import dev.teamcitrus.betterfarms.api.util.AnimalUtil;
import dev.teamcitrus.betterfarms.attachment.AnimalAttachment;
import dev.teamcitrus.betterfarms.config.BetterFarmsConfig;
import dev.teamcitrus.betterfarms.data.BFStatsManager;
import dev.teamcitrus.betterfarms.registry.AttachmentRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.BabyEntitySpawnEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.entity.player.SleepingTimeCheckEvent;

@Mod.EventBusSubscriber(modid = BetterFarms.MODID)
public class EntityEvents {
    @SubscribeEvent
    public static void onEntityAdded(EntityJoinLevelEvent event) {
        if (!(event.getEntity() instanceof Animal animal)) return;
        if (!event.loadedFromDisk()) {
            animal.getData(AttachmentRegistry.ANIMAL).setGender(
                    AnimalAttachment.AnimalGenders.values()[event.getEntity().level().random.nextInt(AnimalAttachment.AnimalGenders.values().length - 1)]
            );
            if (BFStatsManager.newMap.containsKey(animal.getType())) {
                if (BFStatsManager.getStats(animal).milking().isPresent()) {
                    animal.getData(AttachmentRegistry.MILK);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        if (event.getTarget() instanceof Animal animal) {
            if (event.getLevel().isClientSide()) return;
            if (animal.getData(AttachmentRegistry.ANIMAL).hasBeenPetToday()) return;
            ServerLevel level = (ServerLevel)event.getLevel();
            Player player = event.getEntity();
            if (!player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) return;
            if (!player.isCrouching()) return;
            String value = BuiltInRegistries.ENTITY_TYPE.getKey(animal.getType()).getPath();
            player.displayClientMessage(Component.translatable(
                    "message.betterfarms.animal_petted", value
            ).withStyle(ChatFormatting.GREEN), true);
            level.sendParticles(ParticleTypes.HEART, animal.getX(), animal.getY() + 0.7, animal.getZ(), 4, 0.5, 0, 0.5, animal.getRandom().nextGaussian() * 0.02);
            AnimalUtil.getAnimalData(animal).alterLoveForKeeper(10);
            AnimalUtil.getAnimalData(animal).setHasBeenPetToday(true);
            event.setCancellationResult(InteractionResult.SUCCESS);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onBabySpawned(BabyEntitySpawnEvent event) {
        if (event.getCausedByPlayer() == null) return;
        ServerPlayer player = (ServerPlayer)event.getCausedByPlayer();
        if (BetterFarmsConfig.animalBirthAlert.get()) {
            player.sendSystemMessage(Component.translatable("message.betterfarms.baby_spawned").withStyle(ChatFormatting.GREEN), true);
            player.connection.send(new ClientboundSoundPacket(Holder.direct(SoundEvents.AMETHYST_BLOCK_CHIME), SoundSource.AMBIENT, player.getX(), player.getY(), player.getZ(), 1.0f, 1.0f, 0));
        }
    }

    @SubscribeEvent
    public static void onEntitySleep(SleepingTimeCheckEvent event) {
        event.setResult(Event.Result.ALLOW);
    }
}
