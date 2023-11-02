package elocindev.item_obliterator.forge.utils;

import elocindev.item_obliterator.forge.ItemObliterator;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public class Utils {
    public static String getItemId(Item item) {
        return ForgeRegistries.ITEMS.getKey(item).toString();
    }

    public static boolean shouldRecipeBeDisabled(Item item) {
        return ItemObliterator.Config.blacklisted_items.contains(getItemId(item))
        || ItemObliterator.Config.only_disable_recipes.contains(getItemId(item));
    }
}
