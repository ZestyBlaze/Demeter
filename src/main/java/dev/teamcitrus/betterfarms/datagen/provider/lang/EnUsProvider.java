package dev.teamcitrus.betterfarms.datagen.provider.lang;

import dev.teamcitrus.betterfarms.BetterFarms;
import dev.teamcitrus.betterfarms.quality.Quality;
import dev.teamcitrus.betterfarms.registry.BlockRegistry;
import dev.teamcitrus.betterfarms.registry.ItemRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class EnUsProvider extends LanguageProvider {
    public EnUsProvider(PackOutput output) {
        super(output, BetterFarms.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        Set<DeferredHolder<Block, ? extends Block>> blocks = new HashSet<>(BlockRegistry.BLOCKS.getEntries());
        Set<DeferredHolder<Item, ? extends Item>> items = new HashSet<>(ItemRegistry.ITEMS.getEntries());
        Quality[] qualities = Quality.values();

        takeAll(items, i -> i.get() instanceof BlockItem);

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
            name = toTitleCase(name, "_");
            add(i.get().getDescriptionId(), name);
        });
        items.forEach(i -> {
            String name = i.get().getDescriptionId().replaceFirst("item\\.betterfarms\\.", "");
            name = toTitleCase(name, "_");
            add(i.get().getDescriptionId(), name);
        });
        Arrays.stream(qualities).forEach(quality -> add("item.betterfarms.quality_tooltip." + quality.getName(), StringUtils.capitalize(quality.getName())));
    }

    /**
     * A method grabbed from Lodestone Lib by Egshels and SammySemiColon to capitalise names and replace [regex] with a space
     * <a href="https://github.com/LodestarMC/Lodestone/blob/65c087683cc1ac2698c4365d1026b00979234443/src/main/java/team/lodestar/lodestone/helpers/DataHelper.java#L36C30-L36C30">...</a>
     */
    private String toTitleCase(String givenString, String regex) {
        String[] stringArray = givenString.split(regex);
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : stringArray) {
            stringBuilder.append(Character.toUpperCase(string.charAt(0))).append(string.substring(1)).append(regex);
        }
        return stringBuilder.toString().trim().replaceAll(regex, " ").substring(0, stringBuilder.length() - 1);
    }

    /**
     * removes all entry from a collection based off of a predicate and returns all items removed in a new collection
     */
    public static <T> void takeAll(Collection<T> src, Predicate<T> pred) {
        src.removeIf(pred);
    }
}
