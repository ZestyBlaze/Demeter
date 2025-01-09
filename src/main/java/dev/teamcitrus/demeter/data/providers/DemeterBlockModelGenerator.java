package dev.teamcitrus.demeter.data.providers;

import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.block.trough.TroughBlock;
import dev.teamcitrus.demeter.registry.BlockFamilyRegistry;
import dev.teamcitrus.demeter.registry.BlockRegistry;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.blockstates.*;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.data.BlockFamily;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class DemeterBlockModelGenerator extends BlockModelGenerators {
    public DemeterBlockModelGenerator(Consumer<BlockStateGenerator> blockStateOutput, ItemModelOutput itemModelOutput, BiConsumer<ResourceLocation, ModelInstance> modelOutput) {
        super(blockStateOutput, itemModelOutput, modelOutput);
    }

    @Override
    public void run() {
        BlockFamilyRegistry.getAllFamilies()
                .filter(BlockFamily::shouldGenerateModel)
                .forEach(p_386718_ -> family(p_386718_.getBaseBlock()).generateFor(p_386718_));
        woodProvider(BlockRegistry.MAPLE_LOG.get()).logWithHorizontal(BlockRegistry.MAPLE_LOG.get()).wood(BlockRegistry.MAPLE_WOOD.get());
        woodProvider(BlockRegistry.STRIPPED_MAPLE_LOG.get()).logWithHorizontal(BlockRegistry.STRIPPED_MAPLE_LOG.get()).wood(BlockRegistry.STRIPPED_MAPLE_WOOD.get());
        createBlockWithRenderType(BlockRegistry.MAPLE_LEAVES.get(), TexturedModel.LEAVES, "cutout");
        createHangingSign(BlockRegistry.STRIPPED_MAPLE_LOG.get(), BlockRegistry.MAPLE_HANGING_SIGN.get(), BlockRegistry.MAPLE_WALL_HANGING_SIGN.get());
        troughBlock();

        createPlantWithDefaultItem(BlockRegistry.MAPLE_SAPLING.get(), BlockRegistry.POTTED_MAPLE_SAPLING.get(), BlockModelGenerators.PlantType.NOT_TINTED, "cutout");
    }

    private void createCrossBlock(Block block, BlockModelGenerators.PlantType plantType, String renderType) {
        TextureMapping texturemapping = plantType.getTextureMapping(block);
        ResourceLocation resourcelocation = plantType.getCross().extend().renderType(renderType).build().create(block, texturemapping, modelOutput);
        blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, resourcelocation));
    }

    private void createBlockWithRenderType(Block block, TexturedModel.Provider provider, String renderType) {
        ResourceLocation resourceLocation = provider.get(block).getTemplate().extend().renderType(renderType).build().create(block, TextureMapping.cube(block), modelOutput);
        blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, resourceLocation));
    }

    public void createPlantWithDefaultItem(Block block, Block pottedBlock, BlockModelGenerators.PlantType plantType, String renderType) {
        registerSimpleItemModel(block.asItem(), plantType.createItemModel(this, block));
        this.createPlant(block, pottedBlock, plantType, renderType);
    }

    public void createPlant(Block block, Block pottedBlock, BlockModelGenerators.PlantType plantType, String renderType) {
        this.createCrossBlock(block, plantType, renderType);
        TextureMapping texturemapping = plantType.getPlantTextureMapping(block);
        ResourceLocation resourcelocation = plantType.getCrossPot().extend().renderType(renderType).build().create(pottedBlock, texturemapping, modelOutput);
        blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(pottedBlock, resourcelocation));
    }

    private void troughBlock() {
        blockStateOutput.accept(MultiPartGenerator.multiPart(BlockRegistry.TROUGH.get())
                .with(Variant.variant().with(VariantProperties.MODEL, Demeter.id("block/trough")))
                .with(Condition.condition().term(TroughBlock.FOOD_TYPE, TroughBlock.FoodType.HAY).term(TroughBlock.FOOD_LEVEL, 1),
                        Variant.variant().with(VariantProperties.MODEL, Demeter.id("block/hay_one_quarter")))
                .with(Condition.condition().term(TroughBlock.FOOD_TYPE, TroughBlock.FoodType.HAY).term(TroughBlock.FOOD_LEVEL, 2),
                        Variant.variant().with(VariantProperties.MODEL, Demeter.id("block/hay_two_quarters")))
                .with(Condition.condition().term(TroughBlock.FOOD_TYPE, TroughBlock.FoodType.HAY).term(TroughBlock.FOOD_LEVEL, 3),
                        Variant.variant().with(VariantProperties.MODEL, Demeter.id("block/hay_three_quarters")))
                .with(Condition.condition().term(TroughBlock.FOOD_TYPE, TroughBlock.FoodType.HAY).term(TroughBlock.FOOD_LEVEL, 4),
                        Variant.variant().with(VariantProperties.MODEL, Demeter.id("block/hay_four_quarters")))
                .with(Condition.condition().term(TroughBlock.FOOD_TYPE, TroughBlock.FoodType.SLOP).term(TroughBlock.FOOD_LEVEL, 1),
                        Variant.variant().with(VariantProperties.MODEL, Demeter.id("block/slop_one_quarter")))
                .with(Condition.condition().term(TroughBlock.FOOD_TYPE, TroughBlock.FoodType.SLOP).term(TroughBlock.FOOD_LEVEL, 2),
                        Variant.variant().with(VariantProperties.MODEL, Demeter.id("block/slop_two_quarters")))
                .with(Condition.condition().term(TroughBlock.FOOD_TYPE, TroughBlock.FoodType.SLOP).term(TroughBlock.FOOD_LEVEL, 3),
                        Variant.variant().with(VariantProperties.MODEL, Demeter.id("block/slop_three_quarters")))
                .with(Condition.condition().term(TroughBlock.FOOD_TYPE, TroughBlock.FoodType.SLOP).term(TroughBlock.FOOD_LEVEL, 4),
                        Variant.variant().with(VariantProperties.MODEL, Demeter.id("block/slop_four_quarters")))
        );
    }
}
