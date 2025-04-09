package dev.teamcitrus.demeter.datamaps;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.teamcitrus.citruslib.codec.CitrusCodecs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;
import java.util.List;

public record AnimalData(Activity activity, int minLifespan, int maxLifespan, int daysPregnant, int daysToGrowUp,
                         int minChildrenPerBirth, int maxChildrenPerBirth, List<Item> favouriteFoods,
                         List<ItemStack> diggableItems
) {
    public static final Codec<AnimalData> CODEC = RecordCodecBuilder.create(func -> func.group(
            Activity.CODEC.fieldOf("activity").forGetter(AnimalData::activity),
            Codec.INT.fieldOf("minLifespan").forGetter(AnimalData::minLifespan),
            Codec.INT.fieldOf("maxLifespan").forGetter(AnimalData::maxLifespan),
            Codec.INT.optionalFieldOf("daysPregnant", 0).forGetter(AnimalData::daysPregnant),
            Codec.INT.optionalFieldOf("daysToGrowUp", 0).forGetter(AnimalData::daysToGrowUp),
            Codec.INT.optionalFieldOf("minChildrenPerBirth", 1).forGetter(AnimalData::minChildrenPerBirth),
            Codec.INT.optionalFieldOf("maxChildrenPerBirth", 1).forGetter(AnimalData::maxChildrenPerBirth),
            BuiltInRegistries.ITEM.byNameCodec().listOf().optionalFieldOf("favouriteFoods", Collections.emptyList()).forGetter(AnimalData::favouriteFoods),
            ItemStack.CODEC.listOf().optionalFieldOf("diggableItems", Collections.emptyList()).forGetter(AnimalData::diggableItems)
    ).apply(func, AnimalData::new));

    public AnimalData(Activity activity, int minLifespan, int maxLifespan) {
        this(activity, minLifespan, maxLifespan, 0, 0, 1, 1, Collections.emptyList(), Collections.emptyList());
    }

    public AnimalData(Activity activity, int minLifespan, int maxLifespan, int daysPregnant, int daysToGrowUp) {
        this(activity, minLifespan, maxLifespan, daysPregnant, daysToGrowUp, 1, 1, Collections.emptyList(), Collections.emptyList());
    }

    public AnimalData(Activity activity, int minLifespan, int maxLifespan, int daysPregnant, int daysToGrowUp, int minChildrenPerBirth, int maxChildrenPerBirth, List<ItemStack> diggableItems) {
        this(activity, minLifespan, maxLifespan, daysPregnant, daysToGrowUp, minChildrenPerBirth, maxChildrenPerBirth, Collections.emptyList(), diggableItems);
    }

    public enum Activity {
        DIURNAL, NOCTURNAL;

        public static final Codec<Activity> CODEC = CitrusCodecs.enumCodec(Activity.class);
    }
}
