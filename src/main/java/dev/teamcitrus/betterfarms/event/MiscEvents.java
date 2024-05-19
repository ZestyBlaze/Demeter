package dev.teamcitrus.betterfarms.event;

import dev.teamcitrus.betterfarms.BetterFarms;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.AddReloadListenerEvent;

@Mod.EventBusSubscriber(modid = BetterFarms.MODID)
public class MiscEvents {
    @SubscribeEvent
    public static void reloadListeners(AddReloadListenerEvent event) {
        //event.addListener(new BFStatsManager());
    }
}
