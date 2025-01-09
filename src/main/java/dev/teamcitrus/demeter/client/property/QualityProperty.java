package dev.teamcitrus.demeter.client.property;

import com.mojang.serialization.MapCodec;
import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.component.QualityLevel;
import dev.teamcitrus.demeter.registry.ComponentRegistry;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class QualityProperty implements SelectItemModelProperty<QualityLevel> {
    public static final ResourceLocation QUALITY = Demeter.id("quality_property");
    public static final MapCodec<QualityProperty> CODEC = MapCodec.unit(new QualityProperty());
    public static final Type<QualityProperty, QualityLevel> TYPE = SelectItemModelProperty.Type.create(CODEC, QualityLevel.CODEC);

    @Override
    public @Nullable QualityLevel get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i, ItemDisplayContext itemDisplayContext) {
        return itemStack.get(ComponentRegistry.QUALITY_LEVEL).level();
    }

    @Override
    public Type<? extends SelectItemModelProperty<QualityLevel>, QualityLevel> type() {
        return TYPE;
    }
}
