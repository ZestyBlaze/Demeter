package dev.teamcitrus.demeter.datagen.provider.lang;

import dev.teamcitrus.citruslib.util.CollectionUtil;
import dev.teamcitrus.citruslib.util.StringUtil;
import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.quality.Quality;
import dev.teamcitrus.demeter.registry.BlockRegistry;
import dev.teamcitrus.demeter.registry.FluidTypeRegistry;
import dev.teamcitrus.demeter.registry.ItemRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class EnUsProvider extends LanguageProvider {
    public EnUsProvider(PackOutput output) {
        super(output, Demeter.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        Set<DeferredHolder<Block, ? extends Block>> blocks = new HashSet<>(BlockRegistry.BLOCKS.getEntries());
        Set<DeferredHolder<Item, ? extends Item>> items = new HashSet<>(ItemRegistry.ITEMS.getEntries());
        Set<DeferredHolder<FluidType, ? extends FluidType>> fluidTypes = new HashSet<>(FluidTypeRegistry.FLUID_TYPES.getEntries());
        Quality[] qualities = Quality.values();

        CollectionUtil.takeAll(items, i -> i.get() instanceof BlockItem);

        items.addAll(ItemRegistry.DEV_ITEMS.getEntries());

        add("advancement.demeter.root", "BetterFarms");
        add("advancement.demeter.root.desc", "The Introduction to the Farming Overhaul!");
        add("advancement.demeter.milk_placed", "Milky Time");
        add("advancement.demeter.milk_placed.desc", "Victorians used to bathe in this stuff");
        add("error.demeter.maxhighermin", "'maxChildrenPerBirth' is higher than 'maxChildrenPerBirth' resulting in the birth failing!");
        add("error.demeter.namesloadfail", "Error while loading names");
        add("item.demeter.quality_tooltip", "Quality: %s");
        add("itemGroup.demeter", "Better Farms");
        add("itemGroup.demeter.dev", "Better Farms - Dev");
        add("message.demeter.baby_spawned", "One of your animals has given birth!");
        add("message.demeter.animal_petted", "You pet your %s for today");
        add("message.demeter.milk.fail_daily", "This animal has already been milked today!");
        add("message.demeter.milk.fail_gender", "This animal is male and cannot be milked!");
        add("message.demeter.brush.fail_daily", "This animal has already been brushed today!");

        add("enchantment.demeter.barber", "Barber");

        blocks.forEach(i -> {
            String name = i.get().getDescriptionId().replaceFirst("block\\.demeter\\.", "");
            name = StringUtil.toTitleCase(name, "_");
            add(i.get().getDescriptionId(), name);
        });
        items.forEach(i -> {
            String name = i.get().getDescriptionId().replaceFirst("item\\.demeter\\.", "");
            name = StringUtil.toTitleCase(name, "_");
            add(i.get().getDescriptionId(), name);
        });
        fluidTypes.forEach(i -> {
            String name = i.get().getDescriptionId().replaceFirst("fluid_type\\.demeter\\.", "");
            name = StringUtil.toTitleCase(name, "_");
            add(i.get().getDescriptionId(), name);
        });
        Arrays.stream(qualities).forEach(quality -> add("item.demeter.quality_tooltip." + quality.getName(), StringUtils.capitalize(quality.getName())));
    }
}
