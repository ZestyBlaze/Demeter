package dev.teamcitrus.demeter.registry;

import dev.teamcitrus.demeter.Demeter;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

@EventBusSubscriber(modid = Demeter.MODID, bus = EventBusSubscriber.Bus.MOD)
public class DemeterRegistries {

    @SubscribeEvent
    public static void registerDatapacks(DataPackRegistryEvent.NewRegistry event) {
    }
}
