package dev.teamcitrus.demeter;

import dev.teamcitrus.demeter.datamaps.AnimalData;
import dev.teamcitrus.demeter.datamaps.CropData;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

@EventBusSubscriber(modid = Demeter.MODID, bus = EventBusSubscriber.Bus.MOD)
public class DemeterDataMaps {
    public static final DataMapType<EntityType<?>, AnimalData> ANIMAL_DATA = DataMapType.builder(
            Demeter.id("animals"), Registries.ENTITY_TYPE, AnimalData.CODEC).synced(AnimalData.CODEC, false).build();
    public static final DataMapType<Block, CropData> CROP_DATA = DataMapType.builder(
            Demeter.id("crops"), Registries.BLOCK, CropData.CODEC).synced(CropData.CODEC, false).build();

    @SubscribeEvent
    private static void register(RegisterDataMapTypesEvent event) {
        event.register(ANIMAL_DATA);
        event.register(CROP_DATA);
    }
}
