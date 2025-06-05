package dev.teamcitrus.demeter.compat.curios;

import dev.teamcitrus.citruslib.item.CitrusItem;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import static dev.teamcitrus.demeter.registry.ItemRegistry.createID;

public class DemeterCuriosItem extends CitrusItem implements ICurioItem {
    public DemeterCuriosItem(Properties properties, String id) {
        super(properties.setId(createID(id)));
    }

    public DemeterCuriosItem(String id) {
        this(new Properties(), id);
    }
}
