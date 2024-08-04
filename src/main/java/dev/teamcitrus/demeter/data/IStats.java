package dev.teamcitrus.demeter.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.teamcitrus.citruslib.codec.CitrusCodecs;
import dev.teamcitrus.citruslib.codec.CodecProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.Optional;

public interface IStats extends CodecProvider<IStats> {
    EntityType<?> entity();

    Activity activity();

    int daysPregnant();

    int daysToGrowUp();

    int minChildrenPerBirth();

    int maxChildrenPerBirth();

    Optional<MilkingCodec> milking();

    enum Activity {
        DIURNAL, NOCTURNAL;

        public static final Codec<Activity> CODEC = CitrusCodecs.enumCodec(Activity.class);
    }

    record MilkingCodec(Item input, Item output) {
        public static final Codec<MilkingCodec> CODEC = RecordCodecBuilder.create(func -> func.group(
                BuiltInRegistries.ITEM.byNameCodec().optionalFieldOf("input", Items.BUCKET).forGetter(MilkingCodec::input),
                BuiltInRegistries.ITEM.byNameCodec().optionalFieldOf("output", Items.MILK_BUCKET).forGetter(MilkingCodec::output)
        ).apply(func, MilkingCodec::new));
    }
}
