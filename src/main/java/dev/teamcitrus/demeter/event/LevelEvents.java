package dev.teamcitrus.demeter.event;

import dev.teamcitrus.citruslib.event.NewDayEvent;
import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.registry.AttachmentRegistry;
import dev.teamcitrus.demeter.util.AnimalUtil;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;

@EventBusSubscriber(modid = Demeter.MODID)
public class LevelEvents {
    @SubscribeEvent
    public static void onNewDay(NewDayEvent event) {
        event.getLevel().getEntities(EntityTypeTest.forClass(Animal.class), animal -> AnimalUtil.getStats(animal).isBound()).forEach(animal -> {
            animal.getData(AttachmentRegistry.ANIMAL).onNewDay(animal);
        });
        event.getLevel().getEntities(EntityTypeTest.forClass(Animal.class), animal -> AnimalUtil.getStats(animal).isBound() && (AnimalUtil.getStats(animal).get().milking().isPresent())).forEach(animal -> {
            animal.getData(AttachmentRegistry.MILK).setHasBeenMilked(false);
        });
    }
}
