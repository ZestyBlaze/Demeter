package dev.zestyblaze.betterfauna;

import dev.zestyblaze.betterfauna.config.BetterFaunaConfig;
import dev.zestyblaze.betterfauna.registry.EventRegistry;
import dev.zestyblaze.betterfauna.registry.ItemRegistry;
import fuzs.forgeconfigapiport.api.config.v3.ForgeConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.neoforged.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BetterFauna implements ModInitializer {
	public static final String MODID = "betterfauna";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

	@Override
	public void onInitialize() {
		ForgeConfigRegistry.INSTANCE.register(MODID, ModConfig.Type.COMMON, BetterFaunaConfig.GENERAL_SPEC);
		EventRegistry.register();
		ItemRegistry.init();
	}
}