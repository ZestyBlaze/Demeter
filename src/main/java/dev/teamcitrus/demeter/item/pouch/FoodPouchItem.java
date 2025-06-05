package dev.teamcitrus.demeter.item.pouch;

import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;

import java.util.Optional;

import static dev.teamcitrus.demeter.registry.ItemRegistry.createID;

public class FoodPouchItem extends Item {
    public FoodPouchItem() {
        super(new Properties().component(DataComponents.CONTAINER, ItemContainerContents.EMPTY)
                .stacksTo(1).setId(createID("food_pouch")));
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide()) {
            ItemStack stack = player.getItemInHand(hand);
            IItemHandler handler = stack.getCapability(Capabilities.ItemHandler.ITEM);
            if (handler != null) {
                for (int i = 0; i < handler.getSlots(); i++) {
                    player.drop(handler.extractItem(i, handler.getStackInSlot(i).getCount(), false), true);
                    playDropContentsSound(level, player);
                    player.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction action, Player player) {
        IItemHandler handler = stack.getCapability(Capabilities.ItemHandler.ITEM);
        if (handler != null && stack.getCount() == 1) {
            ItemStack itemStack = slot.getItem();
            if (action == ClickAction.PRIMARY && !itemStack.isEmpty()) {
                int freeSlot = getFreeSlotOrMatchingSlot(handler, itemStack);
                if (freeSlot >= 0) {
                    ItemStack remaining = handler.insertItem(freeSlot, itemStack.copy(), false);
                    itemStack.setCount(remaining.getCount());

                    if (remaining.getCount() != itemStack.getCount()) {
                        playInsertSound(player);
                    } else {
                        playInsertFailSound(player);
                    }

                    broadcastChangesOnContainerMenu(player);
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access) {
        if (other.isEmpty()) {
            return false;
        }
        IItemHandler handler = stack.getCapability(Capabilities.ItemHandler.ITEM);
        if (handler != null && action == ClickAction.PRIMARY && !stack.isEmpty()) {
            int freeSlot = getFreeSlotOrMatchingSlot(handler, other);
            if (freeSlot >= 0) {
                ItemStack remaining = handler.insertItem(freeSlot, other.copy(), false);
                other.setCount(remaining.getCount());

                if (remaining.getCount() != other.getCount()) {
                    playInsertSound(player);
                } else {
                    playInsertFailSound(player);
                }

                broadcastChangesOnContainerMenu(player);
                return true;
            }
        }
        return false;
    }

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        return Optional.ofNullable(stack.get(DataComponents.CONTAINER)).map(FoodPouchTooltip::new);
    }

    private static void playInsertSound(Entity entity) {
        entity.playSound(SoundEvents.BUNDLE_INSERT, 0.8F, 0.8F + entity.level().getRandom().nextFloat() * 0.4F);
    }

    private static void playInsertFailSound(Entity entity) {
        entity.playSound(SoundEvents.BUNDLE_INSERT_FAIL, 1.0F, 1.0F);
    }

    private static void playDropContentsSound(Level level, Entity entity) {
        level.playSound(null, entity.blockPosition(), SoundEvents.BUNDLE_DROP_CONTENTS, SoundSource.PLAYERS, 0.8F, 0.8F + entity.level().getRandom().nextFloat() * 0.4F);
    }

    public int getFreeSlotOrMatchingSlot(IItemHandler handler, ItemStack stack) {
        for(int i = 0; i < handler.getSlots(); ++i) {
            ItemStack slotStack = handler.getStackInSlot(i);
            if (ItemStack.isSameItem(slotStack, stack) && slotStack.getCount() != slotStack.getMaxStackSize() || slotStack.isEmpty()) {
                return i;
            }
        }

        return -1;
    }

    private void broadcastChangesOnContainerMenu(Player player) {
        AbstractContainerMenu abstractcontainermenu = player.containerMenu;
        abstractcontainermenu.slotsChanged(player.getInventory());
    }
}
