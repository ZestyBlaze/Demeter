package dev.teamcitrus.demeter.event;

import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.data.loaders.NamesLoader;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddServerReloadListenersEvent;

@EventBusSubscriber(modid = Demeter.MODID)
public class ReloadListenerEvents {
    public static final NamesLoader NAME_LOADER = new NamesLoader();

    @SubscribeEvent
    public static void registerReloadListeners(AddServerReloadListenersEvent event) {
        event.addListener(Demeter.id("names"), NAME_LOADER);
    }
}
