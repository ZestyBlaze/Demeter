package dev.teamcitrus.demeter.util;

import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class PlayerUtil {
    public static boolean hasInHand(Player player, TagKey<Item> tag) {
        return player.getMainHandItem().is(tag) || player.getOffhandItem().is(tag);
    }
}
