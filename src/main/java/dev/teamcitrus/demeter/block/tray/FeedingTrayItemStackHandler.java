package dev.teamcitrus.demeter.block.tray;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.items.ItemStackHandler;

public class FeedingTrayItemStackHandler extends ItemStackHandler {
    public FeedingTrayItemStackHandler(int size) {
        super(size);
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return stack.is(Tags.Items.SEEDS);
    }

    @Override
    public int getSlotLimit(int slot) {
        return 1;
    }
}
