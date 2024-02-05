package dev.teamcitrus.betterfarms.event;

import dev.teamcitrus.betterfarms.BetterFarms;
import dev.teamcitrus.betterfarms.attachment.AnimalAttachment;
import dev.teamcitrus.betterfarms.data.BFStatsManager;
import dev.teamcitrus.betterfarms.registry.AttachmentRegistry;
import net.minecraft.world.entity.animal.Animal;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.player.SleepingTimeCheckEvent;

@Mod.EventBusSubscriber(modid = BetterFarms.MODID)
public class EntityEvents {
    @SubscribeEvent
    public static void onEntityAdded(EntityJoinLevelEvent event) {
        if (!event.loadedFromDisk() && event.getEntity() instanceof Animal animal) {
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
    public static void onEntitySleep(SleepingTimeCheckEvent event) {
        event.setResult(Event.Result.ALLOW);
    }
}
