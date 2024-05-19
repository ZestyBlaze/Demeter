package dev.teamcitrus.betterfarms.event;

import dev.teamcitrus.betterfarms.BetterFarms;
import dev.teamcitrus.betterfarms.registry.AttachmentRegistry;
import dev.teamcitrus.betterfarms.util.AnimalUtil;
import dev.teamcitrus.citruslib.event.NewDayEvent;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BetterFarms.MODID)
public class LevelEvents {
    @SubscribeEvent
    public static void onNewDay(NewDayEvent event) {
        event.getLevel().getEntities(EntityTypeTest.forClass(Animal.class), AnimalUtil::statsContains).forEach(animal -> {
            animal.getData(AttachmentRegistry.ANIMAL).onNewDay(animal);
        });
        event.getLevel().getEntities(EntityTypeTest.forClass(Animal.class), animal -> AnimalUtil.statsContains(animal) && (AnimalUtil.getStats(animal).get().milking().isPresent())).forEach(animal -> {
            animal.getData(AttachmentRegistry.MILK).setHasBeenMilked(false);
        });
    }
}
