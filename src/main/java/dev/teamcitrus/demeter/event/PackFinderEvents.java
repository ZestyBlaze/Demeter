package dev.teamcitrus.demeter.event;

import dev.teamcitrus.demeter.Demeter;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddPackFindersEvent;

@EventBusSubscriber(modid = Demeter.MODID, bus = EventBusSubscriber.Bus.MOD)
public class PackFinderEvents {
    @SubscribeEvent
    public static void addFeaturePacks(AddPackFindersEvent event) {
        event.addPackFinders(
                Demeter.id("data/demeter/datapacks/experimental"),
                PackType.SERVER_DATA, Component.literal("Demeter: Experimental Features"),
                PackSource.FEATURE, false, Pack.Position.TOP
        );
    }
}
