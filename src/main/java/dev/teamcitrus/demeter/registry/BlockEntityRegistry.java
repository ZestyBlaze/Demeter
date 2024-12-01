package dev.teamcitrus.demeter.registry;

import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.block.trough.TroughBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Demeter.MODID);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TroughBlockEntity>> TROUGH = BLOCK_ENTITIES.register(
            "trough",
            () -> BlockEntityType.Builder.of(TroughBlockEntity::new, BlockRegistry.TROUGH.get()).build(null)
    );
}
