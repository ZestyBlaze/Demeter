package dev.teamcitrus.betterfarms.datagen.provider.lang;

import dev.teamcitrus.betterfarms.BetterFarms;
import dev.teamcitrus.betterfarms.quality.Quality;
import dev.teamcitrus.betterfarms.registry.BlockRegistry;
import dev.teamcitrus.betterfarms.registry.ItemRegistry;
import dev.teamcitrus.citruslib.util.DatagenUtils;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class EnUsProvider extends LanguageProvider {
    public EnUsProvider(PackOutput output) {
        super(output, BetterFarms.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        Set<DeferredHolder<Block, ? extends Block>> blocks = new HashSet<>(BlockRegistry.BLOCKS.getEntries());
        Set<DeferredHolder<Item, ? extends Item>> items = new HashSet<>(ItemRegistry.ITEMS.getEntries());
        Quality[] qualities = Quality.values();

        DatagenUtils.takeAll(items, i -> i.get() instanceof BlockItem);

        items.addAll(ItemRegistry.DEV_ITEMS.getEntries());

        add("advancement.betterfarms.root", "BetterFarms");
        add("advancement.betterfarms.root.desc", "The Introduction to the Farming Overhaul!");
        add("advancement.betterfarms.milk_placed", "Milky Time");
        add("advancement.betterfarms.milk_placed.desc", "Victorians used to bathe in this stuff");
        add("error.betterfarms.maxhighermin", "'maxChildrenPerBirth' is higher than 'maxChildrenPerBirth' resulting in the birth failing!");
        add("error.betterfarms.namesloadfail", "Error while loading names");
        add("item.betterfarms.quality_tooltip", "Quality: %s");
        add("itemGroup.betterfarms", "Better Farms");
        add("itemGroup.betterfarms.dev", "Better Farms - Dev");
        add("message.betterfarms.baby_spawned", "One of your animals has given birth!");
        add("message.betterfarms.animal_petted", "You pet your %s for today");
        add("message.betterfarms.milk.fail_daily", "This animal has already been milked today");
        add("message.betterfarms.milk.fail_gender", "This animal is male and cannot be milked");

        blocks.forEach(i -> {
            String name = i.get().getDescriptionId().replaceFirst("block\\.betterfarms\\.", "");
            name = DatagenUtils.toTitleCase(name, "_");
            add(i.get().getDescriptionId(), name);
        });
        items.forEach(i -> {
            String name = i.get().getDescriptionId().replaceFirst("item\\.betterfarms\\.", "");
            name = DatagenUtils.toTitleCase(name, "_");
            add(i.get().getDescriptionId(), name);
        });
        Arrays.stream(qualities).forEach(quality -> add("item.betterfarms.quality_tooltip." + quality.getName(), StringUtils.capitalize(quality.getName())));
    }
}
