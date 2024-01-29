package dev.teamcitrus.betterfarms;

import dev.teamcitrus.betterfarms.config.BetterFarmsConfig;
import dev.teamcitrus.betterfarms.registry.*;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(BetterFarms.MODID)
public class BetterFarms {
    public static final String MODID = "betterfarms";
    public static final Logger LOGGER = LogManager.getLogger("BetterFarms");

    public BetterFarms(IEventBus bus) {
        ItemRegistry.ITEMS.register(bus);
        ItemRegistry.CREATIVE_MODE_TABS.register(bus);
        BlockRegistry.BLOCKS.register(bus);
        FluidRegistry.FLUIDS.register(bus);
        FluidTypeRegistry.FLUID_TYPES.register(bus);
        AttachmentRegistry.ATTACHMENT_TYPES.register(bus);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, BetterFarmsConfig.GENERAL_SPEC);
    }

    public static ResourceLocation createId(String name) {
        return new ResourceLocation(MODID, name);
    }
}
