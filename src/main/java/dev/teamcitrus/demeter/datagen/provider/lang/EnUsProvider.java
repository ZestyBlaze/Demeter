package dev.teamcitrus.demeter.datagen.provider.lang;

import dev.teamcitrus.citruslib.util.JavaUtil;
import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.component.Quality;
import dev.teamcitrus.demeter.registry.BlockRegistry;
import dev.teamcitrus.demeter.registry.FluidTypeRegistry;
import dev.teamcitrus.demeter.registry.ItemRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
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

        JavaUtil.takeAll(items, i -> i.get() instanceof BlockItem);
        JavaUtil.takeAll(blocks, i -> i.get() instanceof WallSignBlock);
        JavaUtil.takeAll(blocks, i -> i.get() instanceof WallHangingSignBlock);

        add("advancement.demeter.root", "BetterFarms");
        add("advancement.demeter.root.desc", "The Introduction to the Farming Overhaul!");
        add("advancement.demeter.milk_placed", "Splish Splash, Milk Bath");
        add("advancement.demeter.milk_placed.desc", "Empty out your Milk Bucket!");
        add("advancement.demeter.animal_brushed", "Neat and Tidy");
        add("advancement.demeter.animal_brushed.desc", "Brush an Animal!");
        add("advancement.demeter.animal_pet", "Easy Does It");
        add("advancement.demeter.animal_pet.desc", "Pet an Animal!");
        add("advancement.demeter.animal_love_max", "My Favourite Animal");
        add("advancement.demeter.animal_love_max.desc", "Get an Animal to max love!");
        add("advancement.demeter.spiteful_animal_brushed", "Malicious Brushing");
        add("advancement.demeter.spiteful_animal_brushed.desc", "Brush your Animal with Ill Intent");
        add("advancement.demeter.use_miracle_potion", "It's a Miracle!");
        add("advancement.demeter.use_miracle_potion.desc", "Use a Miracle Potion on an Animal");
        add("advancement.demeter.use_miracle_on_frog", "Chemicals in the Water");
        add("advancement.demeter.use_miracle_on_frog.desc", "'The Freaking Frogs are Gay!'");
        add("enchantment.demeter.barber", "Barber");
        add("enchantment.demeter.comfort", "Comfort");
        add("enchantment.demeter.spite", "Curse of Spite");
        add("error.demeter.maxhighermin", "'maxChildrenPerBirth' is higher than 'maxChildrenPerBirth' resulting in the birth failing!");
        add("error.demeter.namesloadfail", "Error while loading names");
        add("item.demeter.quality_tooltip", "Quality: %s");
        add("itemGroup.demeter", "Demeter");
        add("message.demeter.baby_spawned", "One of your animals has given birth!");
        add("message.demeter.animal_petted", "You pet your %s for today");
        add("message.demeter.milk.fail_daily", "This animal has already been milked today!");
        add("message.demeter.milk.fail_gender", "This animal is male and cannot be milked!");
        add("message.demeter.brush.fail_daily", "This animal has already been brushed today!");
        add("tag.item.c.tools.animal_brush", "Animal Brushes");
        add("tag.item.demeter.maple_logs", "Maple Logs");
        add("tag.item.demeter.quality_products", "Quality Products");

        blocks.forEach(i -> {
            String name = i.get().getDescriptionId().replaceFirst("block\\.demeter\\.", "");
            name = JavaUtil.toTitleCase(name, "_");
            add(i.get().getDescriptionId(), name);
        });
        items.forEach(i -> {
            String name = i.get().getDescriptionId().replaceFirst("item\\.demeter\\.", "");
            name = JavaUtil.toTitleCase(name, "_");
            add(i.get().getDescriptionId(), name);
        });
        fluidTypes.forEach(i -> {
            String name = i.get().getDescriptionId().replaceFirst("fluid_type\\.demeter\\.", "");
            name = JavaUtil.toTitleCase(name, "_");
            add(i.get().getDescriptionId(), name);
        });
        Arrays.stream(qualities).forEach(quality -> add("item.demeter.quality_tooltip." + quality.getName(), StringUtils.capitalize(quality.getName())));
    }
}
