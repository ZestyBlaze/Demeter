package dev.teamcitrus.betterfarms.datagen.provider.lang;

import dev.teamcitrus.betterfarms.BetterFarms;
import dev.teamcitrus.betterfarms.registry.ItemRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.HashSet;
import java.util.Set;

public class EnUsProvider extends LanguageProvider {
    public EnUsProvider(PackOutput output) {
        super(output, BetterFarms.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        Set<DeferredHolder<Item, ? extends Item>> items = new HashSet<>(ItemRegistry.ITEMS.getEntries());

        add("itemGroup.betterfarms", "Better Farms");
        add("advancement.betterfarms.root", "BetterFarms");
        add("advancement.betterfarms.root.desc", "The Introduction to the Farming Overhaul!");
        add("message.betterfarms.milk.fail_daily", "This animal has already been milked today");
        add("message.betterfarms.milk.fail_gender", "This animal is male and cannot be milked");

        items.forEach(i -> {
            String name = i.get().getDescriptionId().replaceFirst("item\\.betterfarms\\.", "");
            name = toTitleCase(name, "_");
            add(i.get().getDescriptionId(), name);
        });
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
}
