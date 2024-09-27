package dev.teamcitrus.demeter.datamaps;

import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.datamaps.crops.CropData;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

@EventBusSubscriber(modid = Demeter.MODID, bus = EventBusSubscriber.Bus.MOD)
public class DemeterDataMaps {
    public static final DataMapType<Block, CropData> CROP_DATA = DataMapType.builder(
            Demeter.id("crops"), Registries.BLOCK, CropData.CODEC).synced(CropData.CODEC, false).build();

    @SubscribeEvent
    private static void register(RegisterDataMapTypesEvent event) {
        event.register(CROP_DATA);
    }
}
