package dev.teamcitrus.demeter.data.providers;

import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.block.trough.TroughBlock;
import dev.teamcitrus.demeter.registry.BlockFamilyRegistry;
import dev.teamcitrus.demeter.registry.BlockRegistry;
import dev.teamcitrus.demeter.registry.ItemRegistry;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.*;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.core.Holder;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
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

        BlockFamilyRegistry.getAllFamilies()
                .filter(BlockFamily::shouldGenerateModel)
                .forEach(p_386718_ -> blockModels.family(p_386718_.getBaseBlock()).generateFor(p_386718_));
        blockModels.woodProvider(BlockRegistry.MAPLE_LOG.get()).logWithHorizontal(BlockRegistry.MAPLE_LOG.get()).wood(BlockRegistry.MAPLE_WOOD.get());
        blockModels.woodProvider(BlockRegistry.STRIPPED_MAPLE_LOG.get()).logWithHorizontal(BlockRegistry.STRIPPED_MAPLE_LOG.get()).wood(BlockRegistry.STRIPPED_MAPLE_WOOD.get());
        blockModels.createTrivialBlock(BlockRegistry.MAPLE_LEAVES.get(), TexturedModel.LEAVES);
        blockModels.createHangingSign(BlockRegistry.STRIPPED_MAPLE_LOG.get(), BlockRegistry.MAPLE_HANGING_SIGN.get(), BlockRegistry.MAPLE_WALL_HANGING_SIGN.get());
        blockModels.createPlantWithDefaultItem(BlockRegistry.MAPLE_SAPLING.get(), BlockRegistry.POTTED_MAPLE_SAPLING.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        troughBlock(blockModels);

        createCrossBlock(blockModels, BlockRegistry.DEAD_CROP.get(), BlockModelGenerators.PlantType.NOT_TINTED, "cutout");
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
        return COPY.stream();
    }

    @Override
    protected Stream<? extends Holder<Item>> getKnownItems() {
        Collection<DeferredHolder<Item, ? extends Item>> ITEMS = ItemRegistry.ITEMS.getEntries();
        Set<DeferredHolder<Item, ? extends Item>> COPY = new HashSet<>(ITEMS);
        COPY.remove(ItemRegistry.WATERING_CAN);
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
