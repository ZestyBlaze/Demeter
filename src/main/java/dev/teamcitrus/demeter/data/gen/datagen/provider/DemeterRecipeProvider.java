package dev.teamcitrus.demeter.data.gen.datagen.provider;

import dev.teamcitrus.citruslib.datagen.CitrusRecipeProvider;
import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.compat.AccessoriesCompat;
import dev.teamcitrus.demeter.registry.BlockRegistry;
import dev.teamcitrus.demeter.registry.ItemRegistry;
import dev.teamcitrus.demeter.registry.WoodSetRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;

import java.util.concurrent.CompletableFuture;

public class DemeterRecipeProvider extends CitrusRecipeProvider {
    public DemeterRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    protected void buildRecipes(RecipeOutput pRecipeOutput) {
        ShapelessRecipeBuilder.shapeless(
                        RecipeCategory.FOOD,
                        ItemRegistry.MILK_BOTTLE, 3
                ).requires(Items.MILK_BUCKET).requires(Items.GLASS_BOTTLE, 3)
                .unlockedBy("has_item", has(Items.MILK_BUCKET))
                .save(pRecipeOutput, Demeter.id("bucket_to_bottles"));

        planksFromLog(pRecipeOutput, WoodSetRegistry.MAPLE.getPlanks(), DemeterItemTagsProvider.MAPLE_LOGS, 4);
        woodFromLogs(pRecipeOutput, BlockRegistry.MAPLE_WOOD, BlockRegistry.MAPLE_LOG);
        woodFromLogs(pRecipeOutput, BlockRegistry.STRIPPED_MAPLE_WOOD, BlockRegistry.STRIPPED_MAPLE_LOG);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.ANIMAL_TAG)
                .requires(Items.PAPER).requires(Items.INK_SAC)
                .unlockedBy("has_item", has(Items.PAPER))
                .save(pRecipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, AccessoriesCompat.Items.BREEDING_CHARM)
                        .requires(Items.DIAMOND)
                        .unlockedBy("has_item", has(Items.DIAMOND))
                        .save(pRecipeOutput.withConditions(new ModLoadedCondition("accessories")));

        twoByTwoPacker(pRecipeOutput, RecipeCategory.REDSTONE, BlockRegistry.MAPLE_SYRUP_BLOCK, ItemRegistry.MAPLE_SYRUP_BOTTLE);

        woodenBoat(pRecipeOutput, ItemRegistry.MAPLE_BOAT, WoodSetRegistry.MAPLE.getPlanks());
        signBuilder(ItemRegistry.MAPLE_SIGN, Ingredient.of(WoodSetRegistry.MAPLE.getPlanks()));
        generateWoodSetRecipes(pRecipeOutput, WoodSetRegistry.MAPLE);
    }
}
