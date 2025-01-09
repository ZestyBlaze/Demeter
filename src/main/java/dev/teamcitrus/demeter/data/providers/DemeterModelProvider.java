package dev.teamcitrus.demeter.data.providers;

import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.block.trough.TroughBlock;
import dev.teamcitrus.demeter.client.property.QualityProperty;
import dev.teamcitrus.demeter.component.QualityLevel;
import dev.teamcitrus.demeter.registry.BlockFamilyRegistry;
import dev.teamcitrus.demeter.registry.BlockRegistry;
import dev.teamcitrus.demeter.registry.ItemRegistry;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.Condition;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.blockstates.Variant;
import net.minecraft.client.data.models.blockstates.VariantProperties;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.core.Holder;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class DemeterModelProvider extends ModelProvider {
    public DemeterModelProvider(PackOutput output) {
        super(output, Demeter.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(ItemRegistry.DEV_DEBUG_ITEM.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.ANIMAL_TAG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.ANIMAL_BRUSH.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.MILK_BOTTLE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.MAPLE_SYRUP_BOTTLE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.MIRACLE_POTION.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.BUTTER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.MAPLE_BOAT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.MAPLE_CHEST_BOAT.get(), ModelTemplates.FLAT_ITEM);
        createWateringCanItem(itemModels, ItemRegistry.WATERING_CAN.get());

        BlockFamilyRegistry.getAllFamilies()
                .filter(BlockFamily::shouldGenerateModel)
                .forEach(p_386718_ -> blockModels.family(p_386718_.getBaseBlock()).generateFor(p_386718_));
        blockModels.woodProvider(BlockRegistry.MAPLE_LOG.get()).logWithHorizontal(BlockRegistry.MAPLE_LOG.get()).wood(BlockRegistry.MAPLE_WOOD.get());
        blockModels.woodProvider(BlockRegistry.STRIPPED_MAPLE_LOG.get()).logWithHorizontal(BlockRegistry.STRIPPED_MAPLE_LOG.get()).wood(BlockRegistry.STRIPPED_MAPLE_WOOD.get());
        createBlockWithRenderType(blockModels, BlockRegistry.MAPLE_LEAVES.get(), TexturedModel.LEAVES, "cutout");
        blockModels.createHangingSign(BlockRegistry.STRIPPED_MAPLE_LOG.get(), BlockRegistry.MAPLE_HANGING_SIGN.get(), BlockRegistry.MAPLE_WALL_HANGING_SIGN.get());
        troughBlock(blockModels);

        createPlantWithDefaultItem(blockModels, BlockRegistry.MAPLE_SAPLING.get(), BlockRegistry.POTTED_MAPLE_SAPLING.get(), BlockModelGenerators.PlantType.NOT_TINTED, "cutout");
    }

    @Override
    protected Stream<? extends Holder<Block>> getKnownBlocks() {
        Collection<DeferredHolder<Block, ? extends Block>> BLOCKS = BlockRegistry.BLOCKS.getEntries();
        Set<DeferredHolder<Block, ? extends Block>> COPY = new HashSet<>(BLOCKS);
        COPY.remove(BlockRegistry.MAPLE_SYRUP_BLOCK);
        COPY.remove(BlockRegistry.DEAD_CROP);
        return COPY.stream();
    }

    @Override
    protected Stream<? extends Holder<Item>> getKnownItems() {
        Collection<DeferredHolder<Item, ? extends Item>> ITEMS = ItemRegistry.ITEMS.getEntries();
        Set<DeferredHolder<Item, ? extends Item>> COPY = new HashSet<>(ITEMS);
        COPY.remove(ItemRegistry.TRUFFLE);
        return COPY.stream();
    }

    private void createCrossBlock(BlockModelGenerators blockModels, Block block, BlockModelGenerators.PlantType plantType, String renderType) {
        TextureMapping texturemapping = plantType.getTextureMapping(block);
        ResourceLocation resourcelocation = plantType.getCross().extend().renderType(renderType).build().create(block, texturemapping, blockModels.modelOutput);
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, resourcelocation));
    }

    private void createBlockWithRenderType(BlockModelGenerators blockModelGenerators, Block block, TexturedModel.Provider provider, String renderType) {
        ResourceLocation resourceLocation = provider.get(block).getTemplate().extend().renderType(renderType).build().create(block, TextureMapping.cube(block), blockModelGenerators.modelOutput);
        blockModelGenerators.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, resourceLocation));
    }

    public void createPlantWithDefaultItem(BlockModelGenerators blockModels, Block block, Block pottedBlock, BlockModelGenerators.PlantType plantType, String renderType) {
        blockModels.registerSimpleItemModel(block.asItem(), plantType.createItemModel(blockModels, block));
        this.createPlant(blockModels, block, pottedBlock, plantType, renderType);
    }

    public void createPlant(BlockModelGenerators blockModels, Block block, Block pottedBlock, BlockModelGenerators.PlantType plantType, String renderType) {
        this.createCrossBlock(blockModels, block, plantType, renderType);
        TextureMapping texturemapping = plantType.getPlantTextureMapping(block);
        ResourceLocation resourcelocation = plantType.getCrossPot().extend().renderType(renderType).build().create(pottedBlock, texturemapping, blockModels.modelOutput);
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(pottedBlock, resourcelocation));
    }

    private void troughBlock(BlockModelGenerators blockModels) {
        blockModels.blockStateOutput.accept(MultiPartGenerator.multiPart(BlockRegistry.TROUGH.get())
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

    private void createWateringCanItem(ItemModelGenerators itemModels, Item wateringCanItem) {
        ItemModel.Unbaked model$plain = ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(wateringCanItem));
        ItemModel.Unbaked model$copper = ItemModelUtils.plainModel(itemModels.createFlatItemModel(wateringCanItem, "_copper", ModelTemplates.FLAT_ITEM));
        ItemModel.Unbaked model$iron = ItemModelUtils.plainModel(itemModels.createFlatItemModel(wateringCanItem, "_iron", ModelTemplates.FLAT_ITEM));
        ItemModel.Unbaked model$netherite = ItemModelUtils.plainModel(itemModels.createFlatItemModel(wateringCanItem, "_netherite", ModelTemplates.FLAT_ITEM));

        itemModels.itemModelOutput.accept(
                wateringCanItem,
                ItemModelUtils.select(new QualityProperty(),
                        model$plain,
                        ItemModelUtils.when(QualityLevel.COPPER, model$copper),
                        ItemModelUtils.when(QualityLevel.IRON, model$iron),
                        ItemModelUtils.when(QualityLevel.NETHERITE, model$netherite)
                )
        );
    }
}
