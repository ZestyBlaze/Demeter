package dev.teamcitrus.demeter.util;

import com.google.common.collect.Streams;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;
import java.util.stream.Stream;

public class PlayerUtil {
    public static boolean hasInHand(Player player, TagKey<Item> tag) {
        return player.getMainHandItem().is(tag) || player.getOffhandItem().is(tag);
    }

    public static Stream<ItemStack> getInventoryStream(Player player) {
        return Streams.concat(player.getInventory().items.stream(), player.getInventory().armor.stream(), player.getInventory().offhand.stream());
    }

    public static boolean hasInInventory(Player player, TagKey<Item> tag, int amount) {
        return hasInInventory(player, (stack) -> stack.is(tag), amount);
    }

    public static boolean hasInInventory(Player player, Predicate<ItemStack> predicate, int amount) {
        return getInventoryStream(player).mapToInt(stack -> predicate.test(stack) ? stack.getCount() : 0).sum() >= amount;
    }
}
