package dev.teamcitrus.demeter.event;

import dev.teamcitrus.citruslib.reload.DynamicHolder;
import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.attachment.AnimalAttachment;
import dev.teamcitrus.demeter.config.DemeterConfig;
import dev.teamcitrus.demeter.data.AnimalStats;
import dev.teamcitrus.demeter.data.IStats;
import dev.teamcitrus.demeter.network.BirthNotificationPacket;
import dev.teamcitrus.demeter.registry.AdvancementRegistry;
import dev.teamcitrus.demeter.registry.AttachmentRegistry;
import dev.teamcitrus.demeter.util.AnimalUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.BabyEntitySpawnEvent;
import net.neoforged.neoforge.event.entity.player.CanPlayerSleepEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Optional;

@EventBusSubscriber(modid = Demeter.MODID)
public class EntityEvents {
    @SubscribeEvent
    public static void onEntityAdded(EntityJoinLevelEvent event) {
        if (!(event.getEntity() instanceof Animal animal)) return;
        DynamicHolder<AnimalStats> stats = AnimalUtil.getStats(animal);
        if (stats.isBound() && stats.get().activity().equals(IStats.Activity.DIURNAL)) {
        }
        if (!event.loadedFromDisk()) {
            AnimalUtil.getAnimalData(animal).setGender(
                    AnimalAttachment.AnimalGenders.values()[event.getEntity().level().random.nextInt(AnimalAttachment.AnimalGenders.values().length)]
            );
        }
    }

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        if (event.getTarget() instanceof Animal animal) {
            if (event.getLevel().isClientSide()) return;
            if (animal.getData(AttachmentRegistry.ANIMAL).hasBeenPetToday()) return;
            ServerLevel level = (ServerLevel) event.getLevel();
            Player player = event.getEntity();
            ServerPlayer serverPlayer = (ServerPlayer)player;
            if (!player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) return;
            if (!player.isCrouching()) return;
            String value = BuiltInRegistries.ENTITY_TYPE.getKey(animal.getType()).getPath();
            player.displayClientMessage(Component.translatable(
                    "message.demeter.animal_petted", value
            ).withStyle(ChatFormatting.GREEN), true);
            level.sendParticles(ParticleTypes.HEART, animal.getX(), animal.getY() + 0.7, animal.getZ(), 4, 0.5, 0, 0.5, animal.getRandom().nextGaussian() * 0.02);
            AnimalUtil.getAnimalData(animal).alterLove(Optional.of(serverPlayer), DemeterConfig.pettingLoveValue.get());
            AnimalUtil.getAnimalData(animal).setHasBeenPetToday(true);
            AdvancementRegistry.PET.get().trigger(serverPlayer);
            event.setCancellationResult(InteractionResult.SUCCESS);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onBabySpawned(BabyEntitySpawnEvent event) {
        if (!(event.getChild() instanceof Animal child)) return;
        if (AnimalUtil.getStats(child).isBound()) {
            AnimalUtil.getAnimalData(child).setDaysLeftUntilGrown(AnimalUtil.getStats(child).get().daysToGrowUp());
        }

        if (event.getCausedByPlayer() == null) return;
        ServerPlayer player = (ServerPlayer) event.getCausedByPlayer();
        PacketDistributor.sendToPlayer(player, new BirthNotificationPacket());
    }

    @SubscribeEvent
    public static void onEntitySleep(CanPlayerSleepEvent event) {
        event.setProblem(null);
    }
}
