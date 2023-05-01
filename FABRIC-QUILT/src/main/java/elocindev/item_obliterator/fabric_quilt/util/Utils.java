package elocindev.item_obliterator.fabric_quilt.util;

import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class Utils {
    public static String getItemId(Item item) {
        return Registry.ITEM.getId(item).toString();
    }  
}
