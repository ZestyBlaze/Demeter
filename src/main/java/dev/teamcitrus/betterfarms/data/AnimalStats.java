package dev.teamcitrus.betterfarms.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.Optional;

public record AnimalStats(int daysPregnant, int maxChildrenPerBirth, Optional<MilkingCodec> milking) {
    public static final Codec<AnimalStats> CODEC = RecordCodecBuilder.create(func -> func.group(
            Codec.INT.optionalFieldOf("daysPregnant", 0).forGetter(a -> a.daysPregnant),
            Codec.INT.optionalFieldOf("maxChildrenPerBirth", 1).forGetter(a -> a.maxChildrenPerBirth),
            MilkingCodec.CODEC.optionalFieldOf("milking").forGetter(a -> a.milking)
    ).apply(func, AnimalStats::new));

    public record MilkingCodec(Item input, Item output) {
        public static final Codec<MilkingCodec> CODEC = RecordCodecBuilder.create(func -> func.group(
                BuiltInRegistries.ITEM.byNameCodec().optionalFieldOf("input", Items.BUCKET).forGetter(m -> m.input),
                BuiltInRegistries.ITEM.byNameCodec().optionalFieldOf("output", Items.MILK_BUCKET).forGetter(m -> m.output)
        ).apply(func, MilkingCodec::new));
    }
}
