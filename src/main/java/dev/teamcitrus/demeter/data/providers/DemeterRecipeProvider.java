package dev.teamcitrus.demeter.data.providers;

import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.compat.curios.CuriosCompat;
import dev.teamcitrus.demeter.registry.BlockRegistry;
import dev.teamcitrus.demeter.registry.ItemRegistry;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;

import java.util.concurrent.CompletableFuture;

public class DemeterRecipeProvider extends RecipeProvider {
    public DemeterRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        HolderGetter<Item> items = registries.lookupOrThrow(Registries.ITEM);
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.FOOD, ItemRegistry.MILK_BOTTLE, 3)
                .requires(Items.MILK_BUCKET).requires(Items.GLASS_BOTTLE, 3)
                .unlockedBy("has_item", has(Items.MILK_BUCKET))
                .save(output, key("bucket_to_bottles"));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.FOOD, ItemRegistry.BUTTER, 3)
                .requires(Items.MILK_BUCKET).requires(Items.PAPER)
                .unlockedBy("has_item", has(Items.PAPER))
                .save(output, key("butter_from_bucket"));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.FOOD, ItemRegistry.BUTTER)
                .requires(ItemRegistry.MILK_BOTTLE).requires(Items.PAPER)
                .unlockedBy("has_item", has(Items.PAPER))
                .save(output, key("butter_from_bottles"));

        planksFromLog(BlockRegistry.MAPLE_PLANKS, DemeterItemTagsProvider.MAPLE_LOGS, 4);
        woodFromLogs(BlockRegistry.MAPLE_WOOD, BlockRegistry.MAPLE_LOG);
        woodFromLogs(BlockRegistry.STRIPPED_MAPLE_WOOD, BlockRegistry.STRIPPED_MAPLE_LOG);

        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, ItemRegistry.ANIMAL_TAG)
                .requires(Items.PAPER).requires(Items.INK_SAC)
                .unlockedBy("has_item", has(Items.PAPER))
                .save(output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, CuriosCompat.Items.BREEDING_CHARM)
                .define('#', Items.IRON_NUGGET).define('m', ItemRegistry.TRUFFLE)
                .define('b', Items.BONE_MEAL).define('t', Items.TORCHFLOWER)
                .define('g', Items.GLASS_BOTTLE)
                .pattern("#m#").pattern("btb").pattern(" g ")
                .unlockedBy("has_item", has(Items.EXPERIENCE_BOTTLE))
                .save(output.withConditions(new ModLoadedCondition("accessories")));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.DECORATIONS, BlockRegistry.MAPLE_SIGN, 3)
                .group("sign")
                .define('#', BlockRegistry.MAPLE_PLANKS)
                .define('X', Items.STICK)
                .pattern("###").pattern("###").pattern(" X ")
                .unlockedBy("has_item", has(BlockRegistry.MAPLE_PLANKS))
                .save(output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, BlockRegistry.TROUGH)
                .define('P', ItemTags.PLANKS)
                .define('L', ItemTags.LOGS)
                .pattern("P P").pattern("PPP").pattern("L L")
                .unlockedBy("has_item", has(ItemTags.PLANKS))
                .save(output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.TOOLS, ItemRegistry.ANIMAL_BRUSH)
                .define('W', Items.WHEAT).define('S', Items.STICK)
                .pattern(" W").pattern("S ")
                .unlockedBy("has_item", has(Items.WHEAT))
                .save(output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.TOOLS, CuriosCompat.Items.POCKET_WATCH)
                        .define('#', Items.CLOCK).define('R', Items.REDSTONE).define('C', Items.CHAIN)
                        .pattern("C ").pattern("#R")
                        .unlockedBy("has_item", has(Items.CLOCK))
                        .save(output.withConditions(new ModLoadedCondition("accessories")));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, ItemRegistry.FOOD_POUCH)
                        .requires(ItemTags.BUNDLES).requires(Tags.Items.ANIMAL_FOODS)
                        .unlockedBy("has_item", has(Items.BUNDLE))
                        .save(output);

        twoByTwoPacker(RecipeCategory.REDSTONE, BlockRegistry.MAPLE_SYRUP_BLOCK, ItemRegistry.MAPLE_SYRUP_BOTTLE);

        woodenBoat(ItemRegistry.MAPLE_BOAT, BlockRegistry.MAPLE_PLANKS);
        chestBoat(ItemRegistry.MAPLE_CHEST_BOAT, ItemRegistry.MAPLE_BOAT);
        hangingSign(ItemRegistry.MAPLE_HANGING_SIGN, BlockRegistry.STRIPPED_MAPLE_LOG);

        ShapedRecipeBuilder.shaped(items,
                        RecipeCategory.BUILDING_BLOCKS, BlockRegistry.MAPLE_STAIRS, 4
                ).define('#', BlockRegistry.MAPLE_PLANKS)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .group("wooden_stairs")
                .unlockedBy("has_item", has(BlockRegistry.MAPLE_PLANKS))
                .save(output);
        ShapedRecipeBuilder.shaped(items,
                        RecipeCategory.BUILDING_BLOCKS, BlockRegistry.MAPLE_SLAB, 6
                ).define('#', BlockRegistry.MAPLE_PLANKS)
                .pattern("###")
                .group("wooden_slab")
                .unlockedBy("has_item", has(BlockRegistry.MAPLE_PLANKS))
                .save(output);
        ShapedRecipeBuilder.shaped(items,
                        RecipeCategory.MISC, BlockRegistry.MAPLE_FENCE, 4
                ).define('#', Items.STICK).define('W', BlockRegistry.MAPLE_PLANKS)
                .pattern("W#W")
                .pattern("W#W")
                .group("wooden_fence")
                .unlockedBy("has_item", has(BlockRegistry.MAPLE_PLANKS))
                .save(output);
        ShapedRecipeBuilder.shaped(items,
                        RecipeCategory.REDSTONE, BlockRegistry.MAPLE_FENCE_GATE
                ).define('#', Items.STICK).define('W', BlockRegistry.MAPLE_PLANKS)
                .pattern("#W#")
                .pattern("#W#")
                .group("wooden_fence_gate")
                .unlockedBy("has_item", has(BlockRegistry.MAPLE_PLANKS))
                .save(output);
        ShapedRecipeBuilder.shaped(items,
                        RecipeCategory.REDSTONE, BlockRegistry.MAPLE_DOOR, 3
                ).define('#', BlockRegistry.MAPLE_PLANKS)
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .group("wooden_door")
                .unlockedBy("has_item", has(BlockRegistry.MAPLE_PLANKS))
                .save(output);
        ShapedRecipeBuilder.shaped(items,
                        RecipeCategory.REDSTONE, BlockRegistry.MAPLE_TRAPDOOR, 2
                ).define('#', BlockRegistry.MAPLE_PLANKS)
                .pattern("###")
                .pattern("###")
                .group("wooden_trapdoor")
                .unlockedBy("has_item", has(BlockRegistry.MAPLE_PLANKS))
                .save(output);
        ShapedRecipeBuilder.shaped(items,
                        RecipeCategory.REDSTONE, BlockRegistry.MAPLE_PRESSURE_PLATE
                ).define('#', BlockRegistry.MAPLE_PLANKS)
                .pattern("##")
                .group("wooden_pressure_plate")
                .unlockedBy("has_item", has(BlockRegistry.MAPLE_PLANKS))
                .save(output);
        ShapelessRecipeBuilder.shapeless(items,
                        RecipeCategory.REDSTONE, BlockRegistry.MAPLE_BUTTON
                ).requires(BlockRegistry.MAPLE_PLANKS)
                .group("wooden_button")
                .unlockedBy("has_item", has(BlockRegistry.MAPLE_PLANKS))
                .save(output);
    }

    private ResourceKey<Recipe<?>> key(String id) {
        return ResourceKey.create(Registries.RECIPE, Demeter.id(id));
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
            super(packOutput, registries);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
            return new DemeterRecipeProvider(provider, recipeOutput);
        }

        @Override
        public String getName() {
            return "Demeter Recipes";
        }
    }
}
