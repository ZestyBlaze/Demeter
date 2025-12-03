package dev.teamcitrus.demeter.block.trough;

import dev.teamcitrus.demeter.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;

public class TroughBlockEntity extends BlockEntity {
    public final ItemStackHandler HANDLER = new TroughItemStackHandler(4);

    public TroughBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntityRegistry.TROUGH.get(), pos, blockState);
    }

    public IItemHandler getCapability(Direction direction) {
        return HANDLER;
    }

    public ItemStack insertItem(ItemStack stack) {
        int i = getFreeSlot();
        if (i >= 0) {
            setChanged();
            return HANDLER.insertItem(i, stack, false);
        }
        return stack;
    }

    public int getFreeSlot() {
        for(int i = 0; i < HANDLER.getSlots(); ++i) {
            if (HANDLER.getStackInSlot(i).isEmpty()) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        IItemHandler stackHandler = getCapability(null);
        NonNullList<ItemStack> items = NonNullList.create();
        for (int i = 0; i < stackHandler.getSlots(); i++) {
            ItemStack slotItem = stackHandler.getStackInSlot(i);
            if (!slotItem.isEmpty()) items.add(slotItem);
        }
        Containers.dropContents(level, pos, items);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        HANDLER.deserializeNBT(registries, tag);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        HANDLER.serializeNBT(registries);
    }
}
