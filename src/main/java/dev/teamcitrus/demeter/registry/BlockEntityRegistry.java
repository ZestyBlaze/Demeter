package dev.teamcitrus.demeter.registry;

import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.block.experimental.tile.CounterTileEntity;
import dev.teamcitrus.demeter.block.nest.NestBlockEntity;
import dev.teamcitrus.demeter.block.tray.FeedingTrayBlockEntity;
import dev.teamcitrus.demeter.block.trough.TroughBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Demeter.MODID);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CounterTileEntity>> COUNTER = BLOCK_ENTITIES.register(
            "counter",
            () -> new BlockEntityType<>(CounterTileEntity::new, BlockRegistry.COUNTER.get())
    );
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FeedingTrayBlockEntity>> FEEDING_TRAY = BLOCK_ENTITIES.register(
            "feeding_tray",
            () -> new BlockEntityType<>(FeedingTrayBlockEntity::new, BlockRegistry.FEEDING_TRAY.get())
    );
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<NestBlockEntity>> NEST = BLOCK_ENTITIES.register(
            "nest",
            () -> new BlockEntityType<>(NestBlockEntity::new, BlockRegistry.NEST.get())
    );
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TroughBlockEntity>> TROUGH = BLOCK_ENTITIES.register(
            "trough",
            () -> new BlockEntityType<>(TroughBlockEntity::new, BlockRegistry.TROUGH.get())
    );
}
