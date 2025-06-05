package dev.teamcitrus.demeter.client.tooltip;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;

import java.util.List;

public class ClientFoodPouchTooltip implements ClientTooltipComponent {
    private static final ResourceLocation SLOT_BACKGROUND_SPRITE = ResourceLocation.withDefaultNamespace("container/bundle/slot_background");
    private static final Component BUNDLE_EMPTY_DESCRIPTION = Component.translatable("item.demeter.food_pouch.tooltip");
    private final ItemContainerContents contents;

    public ClientFoodPouchTooltip(ItemContainerContents contents) {
        this.contents = contents;
    }

    @Override
    public int getHeight(Font font) {
        return isEmpty() ? getEmptyBundleBackgroundHeight(font) : backgroundHeight();
    }

    @Override
    public int getWidth(Font font) {
        return 96;
    }

    @Override
    public boolean showTooltipWithItemInHand() {
        return true;
    }

    private static int getEmptyBundleBackgroundHeight(Font font) {
        return getEmptyBundleDescriptionTextHeight(font);
    }

    @Override
    public void renderImage(Font font, int x, int y, int width, int height, GuiGraphics guiGraphics) {
        if (isEmpty()) {
            renderEmptyBundleTooltip(font, x, y, width, height, guiGraphics);
        } else {
            renderBundleWithItemsTooltip(font, x, y, width, height, guiGraphics);
        }
    }

    private void renderEmptyBundleTooltip(Font font, int x, int y, int width, int height, GuiGraphics guiGraphics) {
        drawEmptyBundleDescriptionText(x + this.getContentXOffset(width), y, font, guiGraphics);
    }

    private void renderBundleWithItemsTooltip(Font font, int x, int y, int width, int height, GuiGraphics guiGraphics) {
        boolean flag = contents.getSlots() > 12;
        List<ItemStack> list = contents.stream().toList().subList(0, Math.min(contents.getSlots(), 5));
        int i = x + getContentXOffset(width) + 96;
        int j = y + gridSizeY() * 24;
        int k = 1;

        for(int l = 1; l <= gridSizeY(); ++l) {
            for(int i1 = 1; i1 <= 4; ++i1) {
                int j1 = i - i1 * 24;
                int k1 = j - l * 24;
                if (shouldRenderSurplusText(flag, i1, l)) {
                    renderCount(j1, k1, getAmountOfHiddenItems(list), font, guiGraphics);
                } else if (shouldRenderItemSlot(list, k)) {
                    renderSlot(k, j1, k1, list, k, font, guiGraphics);
                    ++k;
                }
            }
        }
    }

    private static boolean shouldRenderSurplusText(boolean hasEnoughItems, int cellX, int cellY) {
        return hasEnoughItems && cellX * cellY == 1;
    }

    private static boolean shouldRenderItemSlot(List<ItemStack> shownItems, int slotIndex) {
        return shownItems.size() >= slotIndex;
    }

    private int getAmountOfHiddenItems(List<ItemStack> shownItems) {
        return contents.stream().skip(shownItems.size()).mapToInt(ItemStack::getCount).sum();
    }

    private void renderSlot(int slotIndex, int x, int y, List<ItemStack> shownItems, int seed, Font font, GuiGraphics guiGraphics) {
        int i = shownItems.size() - slotIndex;
        ItemStack itemstack = shownItems.get(i);

        guiGraphics.blitSprite(RenderType::guiTextured, SLOT_BACKGROUND_SPRITE, x, y, 24, 24);

        guiGraphics.renderItem(itemstack, x + 4, y + 4, seed);
        guiGraphics.renderItemDecorations(font, itemstack, x + 4, y + 4);
    }

    private static void renderCount(int slotX, int slotY, int count, Font font, GuiGraphics guiGraphics) {
        guiGraphics.drawCenteredString(font, "+" + count, slotX + 12, slotY + 10, 16777215);
    }

    private int backgroundHeight() {
        return this.itemGridHeight();
    }

    private int itemGridHeight() {
        return this.gridSizeY() * 24;
    }

    private int gridSizeY() {
        return Mth.positiveCeilDiv(Math.min(4, contents.getSlots()), 4);
    }

    private int getContentXOffset(int width) {
        return (width - 96) / 2;
    }

    private static void drawEmptyBundleDescriptionText(int x, int y, Font font, GuiGraphics guiGraphics) {
        guiGraphics.drawWordWrap(font, BUNDLE_EMPTY_DESCRIPTION, x, y, 96, 11184810);
    }

    private static int getEmptyBundleDescriptionTextHeight(Font font) {
        return font.split(BUNDLE_EMPTY_DESCRIPTION, 96).size() * 9;
    }

    private boolean isEmpty() {
        return contents.stream().findAny().isEmpty();
    }
}
