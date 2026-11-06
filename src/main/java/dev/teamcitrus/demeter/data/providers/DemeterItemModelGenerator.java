package dev.teamcitrus.demeter.data.providers;

import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.client.property.FilledProperty;
import dev.teamcitrus.demeter.registry.BlockRegistry;
import dev.teamcitrus.demeter.registry.ItemRegistry;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.function.BiConsumer;

public class DemeterItemModelGenerator extends ItemModelGenerators {
    public DemeterItemModelGenerator(ItemModelOutput itemModelOutput, BiConsumer<ResourceLocation, ModelInstance> modelOutput) {
        super(itemModelOutput, modelOutput);
    }

    @Override
    public void run() {
        generateFlatItem(ItemRegistry.DEV_DEBUG_ITEM.get(), ModelTemplates.FLAT_ITEM);
        generateFlatItem(ItemRegistry.ANIMAL_TAG.get(), ModelTemplates.FLAT_ITEM);
        generateFlatItem(ItemRegistry.ANIMAL_BRUSH.get(), ModelTemplates.FLAT_ITEM);
        generateFlatItem(ItemRegistry.MILK_BOTTLE.get(), ModelTemplates.FLAT_ITEM);
        generateFlatItem(ItemRegistry.MAPLE_SYRUP_BOTTLE.get(), ModelTemplates.FLAT_ITEM);
        generateFlatItem(ItemRegistry.MIRACLE_POTION.get(), ModelTemplates.FLAT_ITEM);
        generateFlatItem(ItemRegistry.BUTTER.get(), ModelTemplates.FLAT_ITEM);
        generateFlatItem(ItemRegistry.MAPLE_BOAT.get(), ModelTemplates.FLAT_ITEM);
        generateFlatItem(ItemRegistry.MAPLE_CHEST_BOAT.get(), ModelTemplates.FLAT_ITEM);
        generateFlatItem(ItemRegistry.FOOD_POUCH.get(), ModelTemplates.FLAT_ITEM);
        generateFlatItem(ItemRegistry.TRUFFLE.get(), ModelTemplates.FLAT_ITEM);
        createWateringCanItem(ItemRegistry.COPPER_WATERING_CAN.get());
        createWateringCanItem(ItemRegistry.IRON_WATERING_CAN.get());
        createWateringCanItem(ItemRegistry.NETHERITE_WATERING_CAN.get());
        itemModelOutput.accept(BlockRegistry.COUNTER.asItem(), ItemModelUtils.plainModel(getPath("counter_straight")));
    }

    private void createWateringCanItem(Item wateringCanItem) {
        ItemModel.Unbaked model$filled = ItemModelUtils.plainModel(createFlatItemModel(wateringCanItem, "_filled", ModelTemplates.FLAT_ITEM));
        ItemModel.Unbaked model$unfilled = ItemModelUtils.plainModel(createFlatItemModel(wateringCanItem, "_unfilled", ModelTemplates.FLAT_ITEM));

        itemModelOutput.accept(
                wateringCanItem,
                ItemModelUtils.conditional(new FilledProperty(), model$filled, model$unfilled)
        );
    }

    private ResourceLocation getPath(String id) {
        return Demeter.id("block/" + id);
    }
}
