package dev.zestyblaze.betterfauna.data.provider;

import dev.zestyblaze.betterfauna.BetterFauna;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class BFItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public static final TagKey<Item> QUALITY_CROPS = TagKey.create(Registries.ITEM, new ResourceLocation(BetterFauna.MODID, "quality_crops"));

    public BFItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        tag(QUALITY_CROPS).add(
                reverseLookup(Items.APPLE),
                reverseLookup(Items.WHEAT),
                reverseLookup(Items.CARROT),
                reverseLookup(Items.POTATO),
                reverseLookup(Items.PUMPKIN),
                reverseLookup(Items.MELON_SLICE),
                reverseLookup(Items.BEETROOT),
                reverseLookup(Items.SWEET_BERRIES),
                reverseLookup(Items.GLOW_BERRIES),
                reverseLookup(Items.CHORUS_FRUIT)
        );
    }
}
