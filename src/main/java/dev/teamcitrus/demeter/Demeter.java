package dev.teamcitrus.demeter;

import dev.teamcitrus.citruslib.network.PayloadHelper;
import dev.teamcitrus.citruslib.tab.TabFillingRegistry;
import dev.teamcitrus.citruslib.util.ModUtil;
import dev.teamcitrus.demeter.block.trough.TroughBlockEntity;
import dev.teamcitrus.demeter.compat.accessories.AccessoriesCompat;
import dev.teamcitrus.demeter.config.DemeterConfig;
import dev.teamcitrus.demeter.network.BirthNotificationPacket;
import dev.teamcitrus.demeter.network.SyncSexPacket;
import dev.teamcitrus.demeter.registry.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlags;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.fluids.capability.templates.FluidHandlerItemStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Demeter.MODID)
public class Demeter {
    public static final String MODID = "demeter";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final FeatureFlag EXPERIMENTAL = FeatureFlags.REGISTRY.getFlag(id("experimental"));

    public Demeter(IEventBus bus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.CLIENT, DemeterConfig.CLIENT_SPEC);
        modContainer.registerConfig(ModConfig.Type.COMMON, DemeterConfig.GENERAL_SPEC);
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        EntityTypeRegistry.ENTITY_TYPES.register(bus);
        BlockRegistry.BLOCKS.register(bus);
        ItemRegistry.ITEMS.register(bus);
        ItemRegistry.CREATIVE_MODE_TABS.register(bus);
        BlockEntityRegistry.BLOCK_ENTITIES.register(bus);
        LootModifierRegistry.LOOT_MODIFIERS.register(bus);
        AttachmentRegistry.ATTACHMENT_TYPES.register(bus);
        ComponentRegistry.COMPONENTS.register(bus);
        AdvancementRegistry.CRITERION.register(bus);
        PoiTypeRegistry.POI_TYPES.register(bus);
        StatsRegistry.STATS.register(bus);
        ConsumeEffectRegistry.TYPE.register(bus);
        bus.register(this);
        
        if (ModUtil.isModInstalled("accessories")) {
            AccessoriesCompat.init(bus);
        }
    }

    public static ResourceLocation id(String name) {
        return ResourceLocation.fromNamespaceAndPath(MODID, name);
    }

    @SubscribeEvent
    public void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            PayloadHelper.registerPayload(new BirthNotificationPacket.Provider());
            PayloadHelper.registerPayload(new SyncSexPacket.Provider());
            TabFillingRegistry.register(ItemRegistry.DEMETER_TAB.getKey(), ItemRegistry.WATERING_CAN.get());
            Stats.CUSTOM.get(StatsRegistry.TIMES_PET.get(), StatFormatter.DEFAULT);
            Stats.CUSTOM.get(StatsRegistry.ANIMALS_FED.get(), StatFormatter.DEFAULT);
        });
    }

    @SubscribeEvent
    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerItem(Capabilities.FluidHandler.ITEM, (itemstack, context) ->
                new FluidHandlerItemStack(ComponentRegistry.FLUID_HANDLER, itemstack, 1000),
                ItemRegistry.WATERING_CAN
        );
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BlockEntityRegistry.TROUGH.get(), TroughBlockEntity::getCapability);
    }
}
