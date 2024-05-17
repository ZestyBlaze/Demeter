package dev.teamcitrus.betterfarms.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;
import java.util.Optional;

public record AnimalStats(
        int daysPregnant, int daysToGrowUp, int minChildrenPerBirth, int maxChildrenPerBirth,
        Optional<List<Ingredient>> breedingItems, Optional<MilkingCodec> milking
) {
    public static final Codec<AnimalStats> CODEC = RecordCodecBuilder.create(func -> func.group(
            Codec.INT.optionalFieldOf("daysPregnant", 0).forGetter(AnimalStats::daysPregnant),
            Codec.INT.optionalFieldOf("daysToGrowUp", 0).forGetter(AnimalStats::daysToGrowUp),
            Codec.INT.optionalFieldOf("minChildrenPerBirth", 1).forGetter(AnimalStats::minChildrenPerBirth),
            Codec.INT.optionalFieldOf("maxChildrenPerBirth", 1).forGetter(AnimalStats::maxChildrenPerBirth),
            Ingredient.LIST_CODEC.optionalFieldOf("breedingItems").forGetter(AnimalStats::breedingItems),
            MilkingCodec.CODEC.optionalFieldOf("milking").forGetter(AnimalStats::milking)
    ).apply(func, AnimalStats::new));

    public record MilkingCodec(Item input, Item output) {
        public static final Codec<MilkingCodec> CODEC = RecordCodecBuilder.create(func -> func.group(
                BuiltInRegistries.ITEM.byNameCodec().optionalFieldOf("input", Items.BUCKET).forGetter(m -> m.input),
                BuiltInRegistries.ITEM.byNameCodec().optionalFieldOf("output", Items.MILK_BUCKET).forGetter(m -> m.output)
        ).apply(func, MilkingCodec::new));
    }
}
