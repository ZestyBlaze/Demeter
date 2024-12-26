package dev.teamcitrus.demeter.data.providers;

/*
public class DemeterRecipeProvider extends CitrusRecipeProvider {
    public DemeterRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    protected void buildRecipes() {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ItemRegistry.MILK_BOTTLE, 3)
                .requires(Items.MILK_BUCKET).requires(Items.GLASS_BOTTLE, 3)
                .unlockedBy("has_item", has(Items.MILK_BUCKET))
                .save(pRecipeOutput, Demeter.id("bucket_to_bottles"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ItemRegistry.BUTTER, 3)
                .requires(Items.MILK_BUCKET).requires(Items.PAPER)
                .unlockedBy("has_item", has(Items.PAPER))
                .save(pRecipeOutput, Demeter.id("butter_from_bucket"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ItemRegistry.BUTTER)
                .requires(ItemRegistry.MILK_BOTTLE).requires(Items.PAPER)
                .unlockedBy("has_item", has(Items.PAPER))
                .save(pRecipeOutput, Demeter.id("butter_from_bottles"));

        planksFromLog(pRecipeOutput, WoodSetRegistry.MAPLE.getPlanks(), DemeterItemTagsProvider.MAPLE_LOGS, 4);
        woodFromLogs(pRecipeOutput, BlockRegistry.MAPLE_WOOD, BlockRegistry.MAPLE_LOG);
        woodFromLogs(pRecipeOutput, BlockRegistry.STRIPPED_MAPLE_WOOD, BlockRegistry.STRIPPED_MAPLE_LOG);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.ANIMAL_TAG)
                .requires(Items.PAPER).requires(Items.INK_SAC)
                .unlockedBy("has_item", has(Items.PAPER))
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AccessoriesCompat.Items.BREEDING_CHARM)
                .define('#', Items.IRON_NUGGET).define('m', ItemRegistry.TRUFFLE)
                .define('b', Items.BONE_MEAL).define('t', Items.TORCHFLOWER)
                .define('g', Items.GLASS_BOTTLE)
                .pattern("#m#").pattern("btb").pattern(" g ")
                .unlockedBy("has_item", has(Items.EXPERIENCE_BOTTLE))
                .save(pRecipeOutput.withConditions(new ModLoadedCondition("accessories")));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.MAPLE_SIGN, 3)
                .group("sign")
                .define('#', WoodSetRegistry.MAPLE.getPlanks())
                .define('X', Items.STICK)
                .pattern("###").pattern("###").pattern(" X ")
                .unlockedBy("has_item", has(WoodSetRegistry.MAPLE.getPlanks()))
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockRegistry.TROUGH)
                .define('P', ItemTags.PLANKS)
                .define('L', ItemTags.LOGS)
                .pattern("P P").pattern("PPP").pattern("L L")
                .unlockedBy("has_item", has(ItemTags.PLANKS))
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegistry.ANIMAL_BRUSH)
                .define('W', Items.WHEAT).define('S', Items.STICK)
                .pattern(" W").pattern("S ")
                .unlockedBy("has_item", has(Items.WHEAT))
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, AccessoriesCompat.Items.POCKET_WATCH)
                        .define('#', DemeterItemTagsProvider.CLOCKS).define('R', Items.REDSTONE).define('C', Items.CHAIN)
                        .pattern("C ").pattern("#R")
                        .unlockedBy("has_item", has(DemeterItemTagsProvider.CLOCKS))
                        .save(pRecipeOutput.withConditions(new ModLoadedCondition("accessories")));

        twoByTwoPacker(pRecipeOutput, RecipeCategory.REDSTONE, BlockRegistry.MAPLE_SYRUP_BLOCK, ItemRegistry.MAPLE_SYRUP_BOTTLE);

        woodenBoat(pRecipeOutput, ItemRegistry.MAPLE_BOAT, WoodSetRegistry.MAPLE.getPlanks());
        chestBoat(pRecipeOutput, ItemRegistry.MAPLE_CHEST_BOAT, ItemRegistry.MAPLE_BOAT);
        hangingSign(pRecipeOutput, ItemRegistry.MAPLE_HANGING_SIGN, BlockRegistry.STRIPPED_MAPLE_LOG);
        generateWoodSetRecipes(pRecipeOutput, WoodSetRegistry.MAPLE);
    }
}

 */
