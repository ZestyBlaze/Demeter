package dev.teamcitrus.demeter.data.providers;

/*
public class DemeterBlockStateProvider extends CitrusBlockStateProvider {
    public DemeterBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Demeter.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        logBlock(BlockRegistry.MAPLE_LOG.get());
        axisBlock(BlockRegistry.MAPLE_WOOD.get(), models().cubeAll("maple_wood", modLoc("block/maple_log")), models().cubeAll("maple_wood", modLoc("block/maple_log")));
        logBlock(BlockRegistry.STRIPPED_MAPLE_LOG.get());
        axisBlock(BlockRegistry.STRIPPED_MAPLE_WOOD.get(), models().cubeAll("stripped_maple_wood", modLoc("block/stripped_maple_log")), models().cubeAll("stripped_maple_wood", modLoc("block/stripped_maple_log")));
        simpleBlock(BlockRegistry.MAPLE_LEAVES.get(), models().cubeAll("maple_leaves", Demeter.id("block/maple_leaves")).renderType("cutout"));
        signBlock(BlockRegistry.MAPLE_SIGN.get(), BlockRegistry.MAPLE_WALL_SIGN.get(), modLoc("block/maple_planks"));
        hangingSignBlock(BlockRegistry.MAPLE_HANGING_SIGN.get(), BlockRegistry.MAPLE_WALL_HANGING_SIGN.get(), modLoc("block/stripped_maple_log"));
        simpleBlock(BlockRegistry.MAPLE_SAPLING.get(), models().cross(BuiltInRegistries.BLOCK.getKey(BlockRegistry.MAPLE_SAPLING.get()).getPath(), blockTexture(BlockRegistry.MAPLE_SAPLING.get())).renderType("cutout"));
        trough(BlockRegistry.TROUGH.get());
        generateSetModels(WoodSetRegistry.MAPLE);
    }

    private void trough(Block block) {
        getMultipartBuilder(block)
                .part().modelFile(models().getExistingFile(Demeter.id("trough"))).addModel().end()
                .part().modelFile(models().getExistingFile(Demeter.id("hay_one_quarter")))
                .addModel().condition(TroughBlock.FOOD_TYPE, TroughBlock.FoodType.HAY).condition(TroughBlock.FOOD_LEVEL, 1).end()
                .part().modelFile(models().getExistingFile(Demeter.id("hay_two_quarters")))
                .addModel().condition(TroughBlock.FOOD_TYPE, TroughBlock.FoodType.HAY).condition(TroughBlock.FOOD_LEVEL, 2).end()
                .part().modelFile(models().getExistingFile(Demeter.id("hay_three_quarters")))
                .addModel().condition(TroughBlock.FOOD_TYPE, TroughBlock.FoodType.HAY).condition(TroughBlock.FOOD_LEVEL, 3).end()
                .part().modelFile(models().getExistingFile(Demeter.id("hay_four_quarters")))
                .addModel().condition(TroughBlock.FOOD_TYPE, TroughBlock.FoodType.HAY).condition(TroughBlock.FOOD_LEVEL, 4).end()
                .part().modelFile(models().getExistingFile(Demeter.id("slop_one_quarter")))
                .addModel().condition(TroughBlock.FOOD_TYPE, TroughBlock.FoodType.SLOP).condition(TroughBlock.FOOD_LEVEL, 1).end()
                .part().modelFile(models().getExistingFile(Demeter.id("slop_two_quarters")))
                .addModel().condition(TroughBlock.FOOD_TYPE, TroughBlock.FoodType.SLOP).condition(TroughBlock.FOOD_LEVEL, 2).end()
                .part().modelFile(models().getExistingFile(Demeter.id("slop_three_quarters")))
                .addModel().condition(TroughBlock.FOOD_TYPE, TroughBlock.FoodType.SLOP).condition(TroughBlock.FOOD_LEVEL, 3).end()
                .part().modelFile(models().getExistingFile(Demeter.id("slop_four_quarters")))
                .addModel().condition(TroughBlock.FOOD_TYPE, TroughBlock.FoodType.SLOP).condition(TroughBlock.FOOD_LEVEL, 4).end();
    }
}
 */
