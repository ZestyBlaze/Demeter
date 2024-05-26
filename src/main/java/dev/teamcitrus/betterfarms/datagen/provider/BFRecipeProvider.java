package dev.teamcitrus.betterfarms.datagen.provider;

import dev.teamcitrus.betterfarms.BetterFarms;
import dev.teamcitrus.betterfarms.registry.ItemRegistry;
import dev.teamcitrus.betterfarms.registry.WoodSetRegistry;
import dev.teamcitrus.citruslib.datagen.CitrusRecipeProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class BFRecipeProvider extends CitrusRecipeProvider {
    public BFRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(RecipeOutput pRecipeOutput) {
        ShapelessRecipeBuilder.shapeless(
                RecipeCategory.FOOD,
                ItemRegistry.MILK_BOTTLE, 3
        ).requires(Ingredient.of(Items.MILK_BUCKET)).requires(Ingredient.of(Items.GLASS_BOTTLE), 3)
                .unlockedBy("has_item", has(Items.MILK_BUCKET))
                .save(pRecipeOutput, BetterFarms.id("bf_bucket_to_bottles"));

        ShapelessRecipeBuilder.shapeless(
                RecipeCategory.BUILDING_BLOCKS, WoodSetRegistry.MAPLE.getPlanks(), 4
        ).requires(ItemRegistry.MAPLE_LOG)
                .group("planks")
                .unlockedBy("has_item", has(ItemRegistry.MAPLE_LOG))
                .save(pRecipeOutput);

        generateWoodSetRecipes(pRecipeOutput, WoodSetRegistry.MAPLE);
    }
}
