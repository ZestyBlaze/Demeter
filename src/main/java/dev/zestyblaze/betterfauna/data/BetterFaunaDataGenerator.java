package dev.zestyblaze.betterfauna.data;

import dev.zestyblaze.betterfauna.data.provider.BFItemTagProvider;
import dev.zestyblaze.betterfauna.data.provider.lang.EnUsProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class BetterFaunaDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(EnUsProvider::new);
		pack.addProvider(BFItemTagProvider::new);
	}
}
