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
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
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
        itemModels.generateFlatItem(ItemRegistry.MAPLE_SAPLING.get(), ModelTemplates.FLAT_ITEM);
        createWateringCanItem(itemModels, ItemRegistry.WATERING_CAN.get());

        BlockFamilyRegistry.getAllFamilies()
                .filter(BlockFamily::shouldGenerateModel)
                .forEach(p_386718_ -> blockModels.family(p_386718_.getBaseBlock()).generateFor(p_386718_));
        blockModels.woodProvider(BlockRegistry.MAPLE_LOG.get()).logWithHorizontal(BlockRegistry.MAPLE_LOG.get()).wood(BlockRegistry.MAPLE_WOOD.get());
        blockModels.woodProvider(BlockRegistry.STRIPPED_MAPLE_LOG.get()).logWithHorizontal(BlockRegistry.STRIPPED_MAPLE_LOG.get()).wood(BlockRegistry.STRIPPED_MAPLE_WOOD.get());
        //createBlockCutout(blockModels, BlockRegistry.MAPLE_LEAVES.get(), TexturedModel.LEAVES);
        blockModels.createHangingSign(BlockRegistry.STRIPPED_MAPLE_LOG.get(), BlockRegistry.MAPLE_HANGING_SIGN.get(), BlockRegistry.MAPLE_WALL_HANGING_SIGN.get());
        troughBlock(blockModels);

        createCrossBlock(blockModels, BlockRegistry.DEAD_CROP.get(), BlockModelGenerators.PlantType.NOT_TINTED, "cutout");
        //createCrossBlock(blockModels, BlockRegistry.MAPLE_LEAVES.get(), BlockModelGenerators.PlantType.NOT_TINTED, "cutout");
        createCrossBlock(blockModels, BlockRegistry.MAPLE_SAPLING.get(), BlockModelGenerators.PlantType.NOT_TINTED, "cutout");
    }

    private void troughBlock(BlockModelGenerators blockModels) {
        blockModels.blockStateOutput.accept(MultiPartGenerator.multiPart(BlockRegistry.TROUGH.get())
                .with(Condition.condition().term(TroughBlock.FOOD_TYPE, TroughBlock.FoodType.HAY))
        );
    }

    @Override
    protected Stream<? extends Holder<Block>> getKnownBlocks() {
        Collection<DeferredHolder<Block, ? extends Block>> BLOCKS = BlockRegistry.BLOCKS.getEntries();
        Set<DeferredHolder<Block, ? extends Block>> COPY = new HashSet<>(BLOCKS);
        COPY.remove(BlockRegistry.MAPLE_SYRUP_BLOCK);
        COPY.remove(BlockRegistry.MAPLE_LEAVES);
        return COPY.stream();
    }

    @Override
    protected Stream<? extends Holder<Item>> getKnownItems() {
        Collection<DeferredHolder<Item, ? extends Item>> ITEMS = ItemRegistry.ITEMS.getEntries();
        Set<DeferredHolder<Item, ? extends Item>> COPY = new HashSet<>(ITEMS);
        COPY.remove(ItemRegistry.TRUFFLE);
        return COPY.stream();
    }

    private void createCrossBlockWithDefaultItem(BlockModelGenerators blockModels, Block block, BlockModelGenerators.PlantType plantType, String renderType) {
        blockModels.registerSimpleFlatItemModel(block);
        this.createCrossBlock(blockModels, block, plantType, renderType);
    }

    private void createCrossBlock(BlockModelGenerators blockModels, Block block, BlockModelGenerators.PlantType plantType, String renderType) {
        TextureMapping texturemapping = plantType.getTextureMapping(block);
        ResourceLocation resourcelocation = plantType.getCross().extend().renderType(renderType).build().create(block, texturemapping, blockModels.modelOutput);
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, resourcelocation));
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

    /*
    @Override
    protected void registerModels() {


        basicItem(ItemRegistry.DEV_DEBUG_ITEM.get());
        basicItem(ItemRegistry.ANIMAL_TAG.get());
        basicItem(ItemRegistry.ANIMAL_BRUSH.get());
        basicItem(ItemRegistry.MILK_BOTTLE.get());
        basicItem(ItemRegistry.MAPLE_SYRUP_BOTTLE.get());
        basicItem(ItemRegistry.MIRACLE_POTION.get());
        simpleBlockItem(BlockRegistry.MAPLE_LOG.get());
        simpleBlockItem(BlockRegistry.MAPLE_WOOD.get());
        simpleBlockItem(BlockRegistry.STRIPPED_MAPLE_LOG.get());
        simpleBlockItem(BlockRegistry.STRIPPED_MAPLE_WOOD.get());
        simpleBlockItem(BlockRegistry.MAPLE_LEAVES.get());
        itemWithBlockTexturePath(ItemRegistry.MAPLE_SAPLING.get());
        simpleBlockItem(BlockRegistry.MAPLE_SYRUP_BLOCK.get());
        basicItem(ItemRegistry.MAPLE_SIGN.get());
        basicItem(ItemRegistry.MAPLE_HANGING_SIGN.get());
        basicItem(ItemRegistry.MAPLE_BOAT.get());
        basicItem(ItemRegistry.MAPLE_CHEST_BOAT.get());
        basicItem(ItemRegistry.BUTTER.get());
        simpleBlockItem(BlockRegistry.TROUGH.get());
        wateringCanItem();
        generateSetModels(WoodSetRegistry.MAPLE);
    }

    public void wateringCanItem() {
        getBuilder("watering_can")
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", Demeter.id("item/wateringcan/watering_can"))
                .override().predicate(Demeter.id("level"), 1).model(new ModelFile.UncheckedModelFile("demeter:item/watering_can_copper")).end()
                .override().predicate(Demeter.id("level"), 2).model(new ModelFile.UncheckedModelFile("demeter:item/watering_can_iron")).end()
                .override().predicate(Demeter.id("level"), 3).model(new ModelFile.UncheckedModelFile("demeter:item/watering_can_netherite")).end();
        getBuilder("watering_can_copper")
                .parent(new ModelFile.UncheckedModelFile("demeter:item/watering_can"))
                .texture("layer0", Demeter.id("item/wateringcan/copper_watering_can"));
        getBuilder("watering_can_iron")
                .parent(new ModelFile.UncheckedModelFile("demeter:item/watering_can"))
                .texture("layer0", Demeter.id("item/wateringcan/iron_watering_can"));
        getBuilder("watering_can_netherite")
                .parent(new ModelFile.UncheckedModelFile("demeter:item/watering_can"))
                .texture("layer0", Demeter.id("item/wateringcan/netherite_watering_can"));
    }

    public void itemWithBlockTexturePath(BlockItem block) {
        ResourceLocation rl = BuiltInRegistries.BLOCK.getKey(block.getBlock());
        getBuilder(block.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(rl.getNamespace(),
                        "block/" + rl.getPath()));
    }
     */
}
