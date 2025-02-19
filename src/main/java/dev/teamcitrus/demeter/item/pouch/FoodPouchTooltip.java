package dev.teamcitrus.demeter.item.pouch;

import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.component.ItemContainerContents;

public record FoodPouchTooltip(ItemContainerContents contents) implements TooltipComponent {
}
