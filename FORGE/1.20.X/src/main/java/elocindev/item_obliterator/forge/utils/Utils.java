package elocindev.item_obliterator.forge.utils;

import elocindev.item_obliterator.forge.ItemObliterator;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;

public class Utils {
    public static String getItemId(Item item) {
        return ForgeRegistries.ITEMS.getKey(item).toString();
    }

    public static boolean shouldRecipeBeDisabled(Item item) {
        return isDisabled(getItemId(item))
        || ItemObliterator.Config.only_disable_recipes.contains(getItemId(item));
    }

    public static boolean shouldRecipeBeDisabled(String itemid) {
        return isDisabled(itemid)
        || ItemObliterator.Config.only_disable_recipes.contains(itemid);
    }

    public static boolean isDisabled(String itemid) {
        if (itemid.equals("minecraft:air")) return false;

        if (!ItemObliterator.Config.use_hashmap_optimizations) {
            for (String blacklisted_id : ItemObliterator.Config.blacklisted_items) {
                if (blacklisted_id == null) continue;

                if (blacklisted_id.startsWith("//")) continue;
                
                if (blacklisted_id.equals(itemid)) return true;

                if (blacklisted_id.startsWith("!")) {
                    blacklisted_id = blacklisted_id.substring(1);

                    if (itemid.matches(blacklisted_id)) return true;
                }
            }
        } else {
            if (ItemObliterator.blacklisted_items.contains(itemid)) return true;
        }

        return false;
    }

    public static boolean isDisabled(ItemStack stack) {
        if (stack == null || stack.is(Items.AIR)) return false;

        CompoundTag nbt = stack.getTag();
        if (nbt != null) {
            for (String blacklisted_nbt : ItemObliterator.Config.blacklisted_nbt) {
                if (blacklisted_nbt == null) continue;
                if (blacklisted_nbt.startsWith("//")) continue;

                String nbtString = nbt.toString();

                if (nbtString.contains(blacklisted_nbt)) return true;

                if (blacklisted_nbt.startsWith("!")) {
                    blacklisted_nbt = blacklisted_nbt.substring(1);

                    if (nbtString.matches(blacklisted_nbt)) return true;
                }
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

    public static boolean isDisabledAttack(String itemid) {
        for (String blacklisted_id : ItemObliterator.Config.only_disable_attacks) {
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
}
