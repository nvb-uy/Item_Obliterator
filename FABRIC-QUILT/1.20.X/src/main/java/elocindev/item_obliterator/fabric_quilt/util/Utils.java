package elocindev.item_obliterator.fabric_quilt.util;

import elocindev.item_obliterator.fabric_quilt.ItemObliterator;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;

public class Utils {
    public static String getItemId(Item item) {
        return Registries.ITEM.getId(item).toString();
    }  

    public static boolean shouldRecipeBeDisabled(Item item) {
        return isDisabled(getItemId(item))
        || ItemObliterator.Config.only_disable_recipes.contains(getItemId(item));
    }

    public static boolean isDisabled(String itemid) {
        for (String blacklisted_id : ItemObliterator.Config.blacklisted_items) {
            if (blacklisted_id.startsWith("//")) continue;
            
            if (blacklisted_id.equals(itemid)) return true;

            if (blacklisted_id.startsWith("$")) {
                String[] processed = processAllOf(blacklisted_id);
                if (itemid.startsWith(processed[0]) && itemid.endsWith(processed[1])) {
                    return true;
                }
            }
        }
        
        return false;
    }

    public static String[] processAllOf(String input)  {
        String[] result = new String[2];
        
        int firstIndex = input.indexOf('$');
        int lastIndex = input.lastIndexOf('$');
        
        if (firstIndex != -1 && lastIndex != -1 && firstIndex < lastIndex) {
            String modId = input.substring(firstIndex + 1, lastIndex);
            String finalText = input.substring(lastIndex + 1);
            
            result[0] = modId;
            result[1] = finalText;
        } else {
            result[0] = "null";
            result[1] = input;
        }
        
        return result;
    }
}
