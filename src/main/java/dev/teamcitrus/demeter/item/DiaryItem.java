package dev.teamcitrus.demeter.item;

import dev.teamcitrus.demeter.Demeter;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.network.Filterable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.WrittenBookContent;
import net.minecraft.world.level.Level;

import java.util.List;

public class DiaryItem extends Item {
    public DiaryItem(Properties properties) {
        super(properties.component(DataComponents.WRITTEN_BOOK_CONTENT, WrittenBookContent.EMPTY)
                .requiredFeatures(Demeter.EXPERIMENTAL));
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        WrittenBookContent writtenBookContent = itemStack.get(DataComponents.WRITTEN_BOOK_CONTENT);
        if (writtenBookContent != null) {
            itemStack.set(DataComponents.WRITTEN_BOOK_CONTENT, new WrittenBookContent(Filterable.passThrough(
                    itemStack.getItemName().getString()),
                    "null", 1,
                    List.of(
                            Filterable.passThrough(Component.literal("Test test")),
                            Filterable.passThrough(Component.literal("Page 2"))
                    ),
                    false)
            );
            player.openItemGui(itemStack, hand);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
