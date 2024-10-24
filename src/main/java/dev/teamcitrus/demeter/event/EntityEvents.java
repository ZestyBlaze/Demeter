package dev.teamcitrus.demeter.event;

import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.attachment.AnimalAttachment;
import dev.teamcitrus.demeter.config.DemeterConfig;
import dev.teamcitrus.demeter.entity.ai.DigTrufflesGoal;
import dev.teamcitrus.demeter.network.BirthNotificationPacket;
import dev.teamcitrus.demeter.registry.AdvancementRegistry;
import dev.teamcitrus.demeter.registry.AttachmentRegistry;
import dev.teamcitrus.demeter.util.AnimalUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.BabyEntitySpawnEvent;
import net.neoforged.neoforge.event.entity.player.CanContinueSleepingEvent;
import net.neoforged.neoforge.event.entity.player.CanPlayerSleepEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;

@EventBusSubscriber(modid = Demeter.MODID)
public class EntityEvents {
    @SubscribeEvent
    public static void onEntityAdded(EntityJoinLevelEvent event) {
        if (!(event.getEntity() instanceof Animal animal)) return;
        if (!event.getLevel().isClientSide()) {
            if (!event.loadedFromDisk()) {
                AnimalAttachment.AnimalGenders gender = AnimalAttachment.AnimalGenders.values()[event.getEntity().level().random.nextInt(AnimalAttachment.AnimalGenders.values().length)];
                AnimalUtil.getAnimalData(animal).setGender(gender);
            }
            if (event.getEntity() instanceof Pig pig) {
                pig.goalSelector.addGoal(4, new DigTrufflesGoal(pig, 1));
            }
        }
    }

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        if (event.getTarget() instanceof Animal animal) {
            if (animal.getData(AttachmentRegistry.ANIMAL).hasBeenPetToday()) return;
            Player player = event.getEntity();

            if (!player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) return;
            if (!player.isCrouching()) return;

            Level level = event.getLevel();
            String value = BuiltInRegistries.ENTITY_TYPE.getKey(animal.getType()).getPath();
            player.displayClientMessage(Component.translatable(
                    "message.demeter.animal_petted", value
            ).withStyle(ChatFormatting.GREEN), true);

            for (int i = 0; i <= 8; i++) {
                level.addParticle(ParticleTypes.HEART, animal.getRandomX(1.0), animal.getRandomY() + 0.5, animal.getRandomZ(1.0), animal.getRandom().nextGaussian() * 0.02, animal.getRandom().nextGaussian() * 0.02, animal.getRandom().nextGaussian() * 0.02);
            }

            AnimalUtil.getAnimalData(animal).alterLove(player, DemeterConfig.pettingLoveValue.get());
            AnimalUtil.getAnimalData(animal).setHasBeenPetToday(true);
            AdvancementRegistry.PET.get().trigger(player);

            event.setCancellationResult(InteractionResult.SUCCESS);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void animalFoodHandler(PlayerInteractEvent.EntityInteract event) {
        if (event.getTarget() instanceof Animal animal) {
            Player player = event.getEntity();
            InteractionHand hand = event.getHand();
            ItemStack stack = player.getItemInHand(hand);
            if (animal.isFood(stack) && !AnimalUtil.getAnimalData(animal).hasBeenFedToday()) {
                List<Item> favouriteFoods = AnimalUtil.getStats(animal).favouriteFoods();
                int love = DemeterConfig.feedingLoveValue.get();
                AnimalUtil.getAnimalData(animal).alterLove(player, favouriteFoods.contains(stack.getItem()) ? love * 2 : love);
                AnimalUtil.getAnimalData(animal).setHasBeenFedToday(true);
                player.swing(hand);
                event.getLevel().addParticle(ParticleTypes.HEART, animal.getRandomX(1.0), animal.getRandomY() + 0.5, animal.getRandomZ(1.0), animal.getRandom().nextGaussian() * 0.02, animal.getRandom().nextGaussian() * 0.02, animal.getRandom().nextGaussian() * 0.02);
            }
        }
    }

    @SubscribeEvent
    public static void onBabySpawned(BabyEntitySpawnEvent event) {
        if (!(event.getChild() instanceof Animal child)) return;
        if (AnimalUtil.getStats(child) != null) {
            AnimalUtil.getAnimalData(child).setDaysLeftUntilGrown(AnimalUtil.getStats(child).daysToGrowUp());
        }

        if (event.getCausedByPlayer() == null) return;
        ServerPlayer player = (ServerPlayer) event.getCausedByPlayer();
        PacketDistributor.sendToPlayer(player, new BirthNotificationPacket());
    }

    @SubscribeEvent
    public static void onEntitySleep(CanPlayerSleepEvent event) {
        event.setProblem(null);
    }

    @SubscribeEvent
    public static void onEntitySleep(CanContinueSleepingEvent event) {
        event.setContinueSleeping(true);
    }
}
