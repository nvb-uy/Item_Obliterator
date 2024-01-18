package elocindev.item_obliterator.fabric_quilt.util;

import elocindev.item_obliterator.fabric_quilt.ItemObliterator;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;

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
            if (blacklisted_id == null) continue;

            if (blacklisted_id.startsWith("//")) continue;
            
            if (blacklisted_id.equals(itemid)) return true;

            if (blacklisted_id.startsWith("!")) {
                blacklisted_id = blacklisted_id.substring(1);

                if (itemid.matches(blacklisted_id)) return true;
            }
        }
        
        return false;
    }

    public static boolean isDisabled(ItemStack stack) {
        if (stack == null) return false;

        if (stack.getNbt() != null) {
            for (String blacklisted_nbt : ItemObliterator.Config.blacklisted_nbt) {
                if (blacklisted_nbt == null) continue;
                if (blacklisted_nbt.startsWith("//")) continue;

                if (stack.getNbt().toString().contains(blacklisted_nbt)) return true;
                if (stack.getNbt().toString().equals(blacklisted_nbt)) return true;

                if (blacklisted_nbt.startsWith("!")) {
                    blacklisted_nbt = blacklisted_nbt.substring(1);

                    if (stack.getNbt().toString().matches(blacklisted_nbt)) return true;
                }
            }

            if (stack.getHolder() instanceof PlayerEntity player) {
                player.sendMessage(Text.literal(stack.getNbt().toString()));
            }
        }
        
        return isDisabled(getItemId(stack.getItem()));
    }

    public static boolean isDisabledInteract(String itemid) {
        for (String blacklisted_id : ItemObliterator.Config.only_disable_interactions) {
            if (blacklisted_id == null) continue;
            if (blacklisted_id.startsWith("//")) continue;
            
            if (blacklisted_id.equals(itemid)) return true;

            if (blacklisted_id.startsWith("!")) {
                blacklisted_id = blacklisted_id.substring(1);

                if (itemid.matches(blacklisted_id)) return true;
            }
        }
        
        return false;
    }

    public static boolean isDisabledInteract(ItemStack stack) {
        if (stack == null) return false;

        return isDisabledInteract(getItemId(stack.getItem()));
    }
}
