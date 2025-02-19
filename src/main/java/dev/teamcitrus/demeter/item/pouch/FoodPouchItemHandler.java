package dev.teamcitrus.demeter.item.pouch;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.MutableDataComponentHolder;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.items.ComponentItemHandler;

public class FoodPouchItemHandler extends ComponentItemHandler {
    public FoodPouchItemHandler(MutableDataComponentHolder parent) {
        super(parent, DataComponents.CONTAINER, 4);
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return super.isItemValid(slot, stack) && stack.is(Tags.Items.ANIMAL_FOODS);
    }
}
