package dev.teamcitrus.betterfarms.event;

import dev.teamcitrus.betterfarms.BetterFarms;
import dev.teamcitrus.betterfarms.registry.AttachmentRegistry;
import dev.teamcitrus.betterfarms.util.AnimalGenders;
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
                    AnimalGenders.values()[event.getEntity().level().random.nextInt(AnimalGenders.values().length)]
            );
        }
    }

    @SubscribeEvent
    public static void onEntitySleep(SleepingTimeCheckEvent event) {
        event.setResult(Event.Result.ALLOW);
    }
}
