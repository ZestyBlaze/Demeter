package dev.teamcitrus.demeter.world;

import dev.teamcitrus.citruslib.CitrusLibRegistries;
import dev.teamcitrus.citruslib.brewing.BrewingRecipe;
import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.registry.ItemRegistry;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;

public class DemeterMixes {
    public static final ResourceKey<BrewingRecipe> MIRACLE_POTION = key("miracle_potion");

    public static void bootstrap(BootstrapContext<BrewingRecipe> bootstrap) {
        bootstrap.register(MIRACLE_POTION, new BrewingRecipe(
                DataComponentIngredient.of(false, PotionContents.createItemStack(Items.POTION, Potions.REGENERATION)),
                Ingredient.of(Items.RABBIT_FOOT),
                ItemRegistry.MIRACLE_POTION.toStack()
        ));
    }

    private static ResourceKey<BrewingRecipe> key(String id) {
        return ResourceKey.create(CitrusLibRegistries.BREWING_RECIPE, Demeter.id(id));
    }
}
