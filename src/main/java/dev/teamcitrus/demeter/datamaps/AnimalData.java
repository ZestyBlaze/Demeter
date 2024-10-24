package dev.teamcitrus.demeter.datamaps;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.teamcitrus.citruslib.codec.CitrusCodecs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.List;
import java.util.Optional;

public record AnimalData(Activity activity, int daysPregnant, int daysToGrowUp,
        int minChildrenPerBirth, int maxChildrenPerBirth, List<Item> favouriteFoods,
        Optional<MilkingCodec> milking
) {
    //TODO: New field for dig products?
    public static final Codec<AnimalData> CODEC = RecordCodecBuilder.create(func -> func.group(
            Activity.CODEC.fieldOf("activity").forGetter(AnimalData::activity),
            Codec.INT.optionalFieldOf("daysPregnant", 0).forGetter(AnimalData::daysPregnant),
            Codec.INT.optionalFieldOf("daysToGrowUp", 0).forGetter(AnimalData::daysToGrowUp),
            Codec.INT.optionalFieldOf("minChildrenPerBirth", 1).forGetter(AnimalData::minChildrenPerBirth),
            Codec.INT.optionalFieldOf("maxChildrenPerBirth", 1).forGetter(AnimalData::maxChildrenPerBirth),
            BuiltInRegistries.ITEM.byNameCodec().listOf().optionalFieldOf("favouriteFoods", List.of()).forGetter(AnimalData::favouriteFoods),
            MilkingCodec.CODEC.optionalFieldOf("milking").forGetter(AnimalData::milking)
    ).apply(func, AnimalData::new));

    public AnimalData(Activity activity, int daysPregnant, int daysToGrowUp) {
        this(activity, daysPregnant, daysToGrowUp, 1, 1, List.of(), Optional.empty());
    }

    public AnimalData(Activity activity, int daysPregnant, int daysToGrowUp, int minChildrenPerBirth, int maxChildrenPerBirth) {
        this(activity, daysPregnant, daysToGrowUp, minChildrenPerBirth, maxChildrenPerBirth, List.of(), Optional.empty());
    }

    public AnimalData(Activity activity, int daysPregnant, int daysToGrowUp, MilkingCodec milking) {
        this(activity, daysPregnant, daysToGrowUp, 1, 1, List.of(), Optional.of(milking));
    }

    public enum Activity {
        DIURNAL, NOCTURNAL;

        public static final Codec<Activity> CODEC = CitrusCodecs.enumCodec(Activity.class);
    }

    public record MilkingCodec(Item input, Item output) {
        public static final Codec<MilkingCodec> CODEC = RecordCodecBuilder.create(func -> func.group(
                BuiltInRegistries.ITEM.byNameCodec().optionalFieldOf("input", Items.BUCKET).forGetter(MilkingCodec::input),
                BuiltInRegistries.ITEM.byNameCodec().optionalFieldOf("output", Items.MILK_BUCKET).forGetter(MilkingCodec::output)
        ).apply(func, MilkingCodec::new));

        public MilkingCodec() {
            this(Items.BUCKET, Items.MILK_BUCKET);
        }
    }
}
