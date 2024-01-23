package dev.teamcitrus.betterfarms.event;

import dev.teamcitrus.betterfarms.BetterFarms;
import dev.teamcitrus.betterfarms.data.BFStatsListener;
import dev.teamcitrus.betterfarms.event.custom.NewDayEvent;
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
        event.getLevel().getEntities(EntityTypeTest.forClass(Animal.class), animal -> BFStatsListener.newMap.containsKey(animal.getType())).forEach(animal -> {
            BFStatsListener.getManager(animal).onNewDay(animal);
        });
    }
}
