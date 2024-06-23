package dev.teamcitrus.demeter.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;
import java.util.Optional;

public record AnimalStats(EntityType<?> entity, Activity activity,
                          int daysPregnant, int daysToGrowUp, int minChildrenPerBirth, int maxChildrenPerBirth,
                          Optional<List<Ingredient>> breedingItems, Optional<MilkingCodec> milking
) implements IStats {
    public static final Codec<AnimalStats> CODEC = RecordCodecBuilder.create(func -> func.group(
            BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("entity").forGetter(AnimalStats::entity),
            Activity.CODEC.fieldOf("activity").forGetter(AnimalStats::activity),
            Codec.INT.optionalFieldOf("daysPregnant", 0).forGetter(AnimalStats::daysPregnant),
            Codec.INT.optionalFieldOf("daysToGrowUp", 0).forGetter(AnimalStats::daysToGrowUp),
            Codec.INT.optionalFieldOf("minChildrenPerBirth", 1).forGetter(AnimalStats::minChildrenPerBirth),
            Codec.INT.optionalFieldOf("maxChildrenPerBirth", 1).forGetter(AnimalStats::maxChildrenPerBirth),
            Ingredient.LIST_CODEC.optionalFieldOf("breedingItems").forGetter(AnimalStats::breedingItems),
            MilkingCodec.CODEC.optionalFieldOf("milking").forGetter(AnimalStats::milking)
    ).apply(func, AnimalStats::new));

    @Override
    public Codec<? extends IStats> getCodec() {
        return CODEC;
    }
}
