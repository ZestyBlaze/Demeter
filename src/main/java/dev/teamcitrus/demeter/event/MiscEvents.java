package dev.teamcitrus.demeter.event;

import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.registry.BlockRegistry;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;

@EventBusSubscriber(modid = Demeter.MODID, bus = EventBusSubscriber.Bus.MOD)
public class MiscEvents {
    @SubscribeEvent
    public static void onBlockEntityValidBlocks(BlockEntityTypeAddBlocksEvent event) {
        event.modify(BlockEntityType.SIGN, BlockRegistry.MAPLE_SIGN.get(), BlockRegistry.MAPLE_WALL_SIGN.get());
        event.modify(BlockEntityType.HANGING_SIGN, BlockRegistry.MAPLE_HANGING_SIGN.get(), BlockRegistry.MAPLE_WALL_HANGING_SIGN.get());
    }
}
