package dev.teamcitrus.demeter.registry;

import com.google.common.collect.ImmutableSet;
import dev.teamcitrus.demeter.Demeter;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Set;

public class PoiTypeRegistry {
    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(Registries.POINT_OF_INTEREST_TYPE, Demeter.MODID);

    public static final DeferredHolder<PoiType, PoiType> FARMLAND = POI_TYPES.register(
            "farmland",
            () -> new PoiType(getBlockStates(Blocks.FARMLAND), 0, 1)
    );

    private static Set<BlockState> getBlockStates(Block block) {
        return ImmutableSet.copyOf(block.getStateDefinition().getPossibleStates());
    }
}
