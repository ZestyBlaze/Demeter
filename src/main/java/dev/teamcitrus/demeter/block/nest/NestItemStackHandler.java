package dev.teamcitrus.demeter.block.nest;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.items.ItemStackHandler;

public class NestItemStackHandler extends ItemStackHandler {
    public NestItemStackHandler() {
        super(1);
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return stack.is(Tags.Items.EGGS);
    }

    @Override
    public int getSlotLimit(int slot) {
        return 1;
    }
}
