package dev.teamcitrus.betterfarms.event;

import dev.teamcitrus.betterfarms.BetterFarms;
import dev.teamcitrus.betterfarms.data.BFStatsManager;
import dev.teamcitrus.betterfarms.event.custom.level.NewDayEvent;
import dev.teamcitrus.betterfarms.registry.AttachmentRegistry;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.TickEvent;

@Mod.EventBusSubscriber(modid = BetterFarms.MODID)
public class LevelEvents {
    @SubscribeEvent
    public static void onLevelTick(TickEvent.LevelTickEvent event) {
        if(!event.level.isClientSide() && event.phase == TickEvent.Phase.END && event.level.getDayTime() % 24000L == 1) {
            NeoForge.EVENT_BUS.post(new NewDayEvent(event.level));
        }
    }

    @SubscribeEvent
    public static void onNewDay(NewDayEvent event) {
        event.getLevel().getEntities(EntityTypeTest.forClass(Animal.class), animal -> BFStatsManager.newMap.containsKey(animal.getType())).forEach(animal -> {
            animal.getData(AttachmentRegistry.ANIMAL).onNewDay(animal);
        });
        event.getLevel().getEntities(EntityTypeTest.forClass(Animal.class), animal -> BFStatsManager.newMap.containsKey(animal.getType()) && (BFStatsManager.getStats(animal).milking().isPresent())).forEach(animal -> {
            animal.getData(AttachmentRegistry.MILK).setHasBeenMilked(false);
        });
    }
}
