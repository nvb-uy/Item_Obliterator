package elocindev.item_obliterator.fabric_quilt.util;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;

public class Utils {
    public static String getItemId(Item item) {
        return Registries.ITEM.getId(item).toString();
    }  
}
