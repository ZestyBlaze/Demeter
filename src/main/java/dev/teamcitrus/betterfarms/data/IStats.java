package dev.teamcitrus.betterfarms.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.teamcitrus.citruslib.codec.CodecProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;
import java.util.Optional;

public interface IStats extends CodecProvider<IStats> {
    EntityType<?> entity();
    int daysPregnant();
    int daysToGrowUp();
    int minChildrenPerBirth();
    int maxChildrenPerBirth();
    Optional<List<Ingredient>> breedingItems();
    Optional<MilkingCodec> milking();

    record MilkingCodec(Item input, Item output) {
        public static final Codec<MilkingCodec> CODEC = RecordCodecBuilder.create(func -> func.group(
                BuiltInRegistries.ITEM.byNameCodec().optionalFieldOf("input", Items.BUCKET).forGetter(MilkingCodec::input),
                BuiltInRegistries.ITEM.byNameCodec().optionalFieldOf("output", Items.MILK_BUCKET).forGetter(MilkingCodec::output)
        ).apply(func, MilkingCodec::new));
    }
}
