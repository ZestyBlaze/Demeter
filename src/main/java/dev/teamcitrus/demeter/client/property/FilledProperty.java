package dev.teamcitrus.demeter.client.property;

import com.mojang.serialization.MapCodec;
import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.registry.ComponentRegistry;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class FilledProperty implements ConditionalItemModelProperty {
    public static final ResourceLocation FILLED = Demeter.id("filled");
    public static final MapCodec<FilledProperty> CODEC = MapCodec.unit(new FilledProperty());

    @Override
    public MapCodec<? extends ConditionalItemModelProperty> type() {
        return CODEC;
    }

    @Override
    public boolean get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i, ItemDisplayContext itemDisplayContext) {
        return itemStack.get(ComponentRegistry.FILLED).filled();
    }
}
