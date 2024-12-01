package dev.teamcitrus.demeter.block.trough;

import net.neoforged.neoforge.items.ItemStackHandler;

public class TroughItemStackHandler extends ItemStackHandler {
    public TroughItemStackHandler(int size) {
        super(size);
    }

    @Override
    public int getSlotLimit(int slot) {
        return 1;
    }
}
