package dev.teamcitrus.betterfarms.datagen.provider;

import dev.teamcitrus.betterfarms.BetterFarms;
import dev.teamcitrus.betterfarms.registry.ItemRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class BFRecipeProvider extends RecipeProvider {
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
                RecipeCategory.BUILDING_BLOCKS,
                ItemRegistry.MAPLE_PLANKS, 4
        ).requires(Ingredient.of(ItemRegistry.MAPLE_LOG)).unlockedBy("has_item", has(ItemRegistry.MAPLE_LOG)).save(pRecipeOutput);
    }
}
