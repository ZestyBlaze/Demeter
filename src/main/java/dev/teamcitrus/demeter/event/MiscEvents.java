package dev.teamcitrus.demeter.event;

import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.registry.BlockRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;

@EventBusSubscriber(modid = Demeter.MODID, bus = EventBusSubscriber.Bus.MOD)
public class MiscEvents {
    @SubscribeEvent
    public static void onBlockEntityValidBlocks(BlockEntityTypeAddBlocksEvent event) {
        event.modify(BlockEntityType.SIGN, BlockRegistry.MAPLE_SIGN.get(), BlockRegistry.MAPLE_WALL_SIGN.get());
        event.modify(BlockEntityType.HANGING_SIGN, BlockRegistry.MAPLE_HANGING_SIGN.get(), BlockRegistry.MAPLE_WALL_HANGING_SIGN.get());
    }

    @SubscribeEvent
    public static void addFeaturePacks(AddPackFindersEvent event) {
        event.addPackFinders(
                Demeter.id("data/demeter/datapacks/experimental"),
                PackType.SERVER_DATA, Component.literal("Demeter: Experimental Features"),
                PackSource.FEATURE, false, Pack.Position.TOP
        );
    }
}
