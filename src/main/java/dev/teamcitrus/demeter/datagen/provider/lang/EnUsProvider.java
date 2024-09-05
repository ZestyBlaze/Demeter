package dev.teamcitrus.demeter.datagen.provider.lang;

import dev.teamcitrus.citruslib.datagen.CitrusLanguageProvider;
import dev.teamcitrus.citruslib.util.JavaUtil;
import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.component.Quality;
import dev.teamcitrus.demeter.datagen.provider.DemeterItemTagsProvider;
import dev.teamcitrus.demeter.registry.BlockRegistry;
import dev.teamcitrus.demeter.registry.FluidTypeRegistry;
import dev.teamcitrus.demeter.registry.ItemRegistry;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EnUsProvider extends CitrusLanguageProvider {
    public EnUsProvider(PackOutput output) {
        super(output, Demeter.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        Set<DeferredHolder<FluidType, ? extends FluidType>> fluidTypes = new HashSet<>(FluidTypeRegistry.FLUID_TYPES.getEntries());
        Quality[] qualities = Quality.values();

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
        add("item.demeter.watering_can", "%s Watering Can");
        add("item.demeter.watering_can.uses", "Uses: %s");
        add("itemGroup.demeter", "Demeter");
        add("message.demeter.animal_petted", "You pet your %s for today");
        add("message.demeter.baby_spawned", "One of your animals has given birth!");
        add("message.demeter.brush.fail_daily", "This animal has already been brushed today!");
        add("message.demeter.milk.fail_daily", "This animal has already been milked today!");
        add("message.demeter.milk.fail_gender", "This animal is male and cannot be milked!");
        add(DemeterItemTagsProvider.TOOLS_ANIMAL_BRUSH, "Animal Brushes");
        add(DemeterItemTagsProvider.ACCESSORIES_CHARM, "Charm");
        add(DemeterItemTagsProvider.MAPLE_LOGS, "Maple Logs");
        add(DemeterItemTagsProvider.QUALITY_PRODUCTS, "Quality Products");

        fluidTypes.forEach(i -> {
            String name = i.get().getDescriptionId().replaceFirst("fluid_type\\.demeter\\.", "");
            name = JavaUtil.toTitleCase(name, "_");
            add(i.get().getDescriptionId(), name);
        });
        generateBlockLanguageKeys(BlockRegistry.BLOCKS);
        generateItemLanguageKeys(ItemRegistry.ITEMS, List.of(ItemRegistry.WATERING_CAN.get()));
        Arrays.stream(qualities).forEach(quality -> add("item.demeter.quality_tooltip." + quality.getName(), StringUtils.capitalize(quality.getName())));
    }
}
