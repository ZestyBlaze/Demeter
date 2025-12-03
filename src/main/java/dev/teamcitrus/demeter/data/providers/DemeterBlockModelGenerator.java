package dev.teamcitrus.demeter.data.providers;

import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.block.tray.FeedingTrayBlock;
import dev.teamcitrus.demeter.block.trough.TroughBlock;
import dev.teamcitrus.demeter.duck.Section;
import dev.teamcitrus.demeter.registry.BlockFamilyRegistry;
import dev.teamcitrus.demeter.registry.BlockRegistry;
import dev.teamcitrus.demeter.registry.ItemRegistry;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.blockstates.BlockModelDefinitionGenerator;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.data.BlockFamily;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class DemeterBlockModelGenerator extends BlockModelGenerators {
    public DemeterBlockModelGenerator(Consumer<BlockModelDefinitionGenerator> blockStateOutput, ItemModelOutput itemModelOutput, BiConsumer<ResourceLocation, ModelInstance> modelOutput) {
        super(blockStateOutput, itemModelOutput, modelOutput);
    }

    @Override
    public void run() {
        BlockFamilyRegistry.getAllFamilies()
                .filter(BlockFamily::shouldGenerateModel)
                .forEach(blockFamily -> family(blockFamily.getBaseBlock()).generateFor(blockFamily));
        woodProvider(BlockRegistry.MAPLE_LOG.get()).logWithHorizontal(BlockRegistry.MAPLE_LOG.get()).wood(BlockRegistry.MAPLE_WOOD.get());
        woodProvider(BlockRegistry.STRIPPED_MAPLE_LOG.get()).logWithHorizontal(BlockRegistry.STRIPPED_MAPLE_LOG.get()).wood(BlockRegistry.STRIPPED_MAPLE_WOOD.get());
        createTrapdoor(BlockRegistry.MAPLE_TRAPDOOR.get(), "cutout");
        createBlockWithRenderType(BlockRegistry.MAPLE_LEAVES.get(), TexturedModel.LEAVES, "cutout");
        createHangingSign(BlockRegistry.STRIPPED_MAPLE_LOG.get(), BlockRegistry.MAPLE_HANGING_SIGN.get(), BlockRegistry.MAPLE_WALL_HANGING_SIGN.get());
        createPlantWithDefaultItem(BlockRegistry.MAPLE_SAPLING.get(), BlockRegistry.POTTED_MAPLE_SAPLING.get(), PlantType.NOT_TINTED, "cutout");
        createNonTemplateModelBlock(BlockRegistry.WINE.get());
        troughBlock();
        feedingTray();
        createPlantWithDefaultItem(BlockRegistry.BAMBOO_SHOOTS.get(), PlantType.NOT_TINTED, "cutout");
        createStrawberryBush();
        createCounter(BlockRegistry.COUNTER.get());
        this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(BlockRegistry.NEST.get(), plainVariant(Demeter.id("block/nest"))));
    }

    private void createCrossBlock(Block block, PlantType plantType, String renderType) {
        TextureMapping texturemapping = plantType.getTextureMapping(block);
        ResourceLocation resourcelocation = plantType.getCross().extend().renderType(renderType).build().create(block, texturemapping, modelOutput);
        blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, plainVariant(resourcelocation)));
    }

    private void createBlockWithRenderType(Block block, TexturedModel.Provider provider, String renderType) {
        ResourceLocation resourceLocation = provider.get(block).getTemplate().extend().renderType(renderType).build().create(block, TextureMapping.cube(block), modelOutput);
        blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, plainVariant(resourceLocation)));
    }

    public void createPlantWithDefaultItem(Block block, Block pottedBlock, PlantType plantType, String renderType) {
        registerSimpleItemModel(block.asItem(), plantType.createItemModel(this, block));
        this.createPlant(block, pottedBlock, plantType, renderType);
    }

    public void createPlant(Block block, Block pottedBlock, PlantType plantType, String renderType) {
        this.createCrossBlock(block, plantType, renderType);
        TextureMapping texturemapping = plantType.getPlantTextureMapping(block);
        ResourceLocation resourcelocation = plantType.getCrossPot().extend().renderType(renderType).build().create(pottedBlock, texturemapping, modelOutput);
        blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(pottedBlock, plainVariant(resourcelocation)));
    }

    public void createPlantWithDefaultItem(Block block, PlantType plantType, String renderType) {
        registerSimpleItemModel(block.asItem(), plantType.createItemModel(this, block));
        this.createPlant(block, plantType, renderType);
    }

    public void createPlant(Block block, PlantType plantType, String renderType) {
        this.createCrossBlock(block, plantType, renderType);
    }

    public void createStrawberryBush() {
        this.registerSimpleFlatItemModel(ItemRegistry.STRAWBERRIES.get());
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(BlockRegistry.STRAWBERRY_BUSH.get())
                .with(PropertyDispatch.initial(BlockStateProperties.AGE_3)
                        .generate((p_388136_) -> plainVariant(this.createSuffixedVariant(BlockRegistry.STRAWBERRY_BUSH.get(), "_stage" + p_388136_, ModelTemplates.CROSS.extend().renderType("cutout").build(), TextureMapping::cross)))));
    }

    public void createTrapdoor(Block trapdoorBlock, String renderType) {
        TextureMapping texturemapping = TextureMapping.defaultTexture(trapdoorBlock);
        ResourceLocation resourcelocation = ModelTemplates.TRAPDOOR_TOP.extend().renderType(renderType).build().create(trapdoorBlock, texturemapping, this.modelOutput);
        ResourceLocation resourcelocation1 = ModelTemplates.TRAPDOOR_BOTTOM.extend().renderType(renderType).build().create(trapdoorBlock, texturemapping, this.modelOutput);
        ResourceLocation resourcelocation2 = ModelTemplates.TRAPDOOR_OPEN.extend().renderType(renderType).build().create(trapdoorBlock, texturemapping, this.modelOutput);
        this.blockStateOutput.accept(createOrientableTrapdoor(trapdoorBlock, plainVariant(resourcelocation), plainVariant(resourcelocation1), plainVariant(resourcelocation2)));
        this.registerSimpleItemModel(trapdoorBlock, resourcelocation1);
    }

    public void createCounter(Block block) {
        blockStateOutput.accept(MultiVariantGenerator.dispatch(block)
                .with(PropertyDispatch.initial(Section.SECTION).generate(section ->
                        switch (section) {
                    case STRAIGHT -> plainVariant(getPath("counter_straight"));
                    case INNER -> plainVariant(getPath("counter_inner"));
                    case OUTER -> plainVariant(getPath("counter_outer"));
                })
        ).with(ROTATION_HORIZONTAL_FACING));
    }

    private void troughBlock() {
        blockStateOutput.accept(MultiPartGenerator.multiPart(BlockRegistry.TROUGH.get())
                .with(plainVariant(Demeter.id("block/trough")))
                .with(condition().term(TroughBlock.FOOD_TYPE, TroughBlock.FoodType.HAY).term(TroughBlock.FOOD_LEVEL, 1),
                        plainVariant(Demeter.id("block/hay_one_quarter")))
                .with(condition().term(TroughBlock.FOOD_TYPE, TroughBlock.FoodType.HAY).term(TroughBlock.FOOD_LEVEL, 2),
                        plainVariant(Demeter.id("block/hay_two_quarters")))
                .with(condition().term(TroughBlock.FOOD_TYPE, TroughBlock.FoodType.HAY).term(TroughBlock.FOOD_LEVEL, 3),
                        plainVariant(Demeter.id("block/hay_three_quarters")))
                .with(condition().term(TroughBlock.FOOD_TYPE, TroughBlock.FoodType.HAY).term(TroughBlock.FOOD_LEVEL, 4),
                        plainVariant(Demeter.id("block/hay_four_quarters")))
                .with(condition().term(TroughBlock.FOOD_TYPE, TroughBlock.FoodType.SLOP).term(TroughBlock.FOOD_LEVEL, 1),
                        plainVariant(Demeter.id("block/slop_one_quarter")))
                .with(condition().term(TroughBlock.FOOD_TYPE, TroughBlock.FoodType.SLOP).term(TroughBlock.FOOD_LEVEL, 2),
                        plainVariant(Demeter.id("block/slop_two_quarters")))
                .with(condition().term(TroughBlock.FOOD_TYPE, TroughBlock.FoodType.SLOP).term(TroughBlock.FOOD_LEVEL, 3),
                        plainVariant(Demeter.id("block/slop_three_quarters")))
                .with(condition().term(TroughBlock.FOOD_TYPE, TroughBlock.FoodType.SLOP).term(TroughBlock.FOOD_LEVEL, 4),
                        plainVariant(Demeter.id("block/slop_four_quarters")))
        );
    }

    private void feedingTray() {
        blockStateOutput.accept(MultiPartGenerator.multiPart(BlockRegistry.FEEDING_TRAY.get())
                .with(plainVariant(Demeter.id("block/feeding_tray")))
                .with(condition().term(FeedingTrayBlock.FOOD_LEVEL, 1),
                        plainVariant(Demeter.id("block/seed_semi")))
                .with(condition().term(FeedingTrayBlock.FOOD_LEVEL, 2),
                        plainVariant(Demeter.id("block/seed_full")))
        );
    }

    private ResourceLocation getPath(String id) {
        return Demeter.id("block/" + id);
    }
}
