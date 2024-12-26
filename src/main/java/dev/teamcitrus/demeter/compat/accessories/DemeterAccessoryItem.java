package dev.teamcitrus.demeter.compat.accessories;

import dev.teamcitrus.citruslib.item.CitrusItem;
import io.wispforest.accessories.api.Accessory;

import static dev.teamcitrus.demeter.registry.ItemRegistry.createID;

public class DemeterAccessoryItem extends CitrusItem implements Accessory {
    public DemeterAccessoryItem(Properties properties, String id) {
        super(properties.setId(createID(id)));
    }

    public DemeterAccessoryItem(String id) {
        this(new Properties(), id);
    }
}
