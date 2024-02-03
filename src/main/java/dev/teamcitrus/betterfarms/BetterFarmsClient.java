package dev.teamcitrus.betterfarms;

import dev.teamcitrus.betterfarms.registry.BlockRegistry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@Mod.EventBusSubscriber(modid = BetterFarms.MODID, bus = Mod.EventBusSubscriber.Bus.MOD ,value = Dist.CLIENT)
public class BetterFarmsClient {
    @SubscribeEvent
    public static void registerColors(RegisterColorHandlersEvent.Block event) {
        event.register(((pState, pLevel, pPos, pTintIndex) -> 0xFFFEFCFF), BlockRegistry.MILK_CAULDRON_BLOCK.get());
    }
}
