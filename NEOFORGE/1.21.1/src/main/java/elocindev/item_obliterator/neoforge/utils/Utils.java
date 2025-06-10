package elocindev.item_obliterator.neoforge.utils;

import elocindev.item_obliterator.neoforge.ItemObliterator;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class Utils {

    public static String getItemId(Item item) {
        // BuiltinRegistries.ITEM is deprecated in newer versions, but still works for now. Will update later
        return BuiltInRegistries.ITEM.getKey(item).toString();
    }

    public static boolean shouldRecipeBeDisabled(Item item) {
        return shouldRecipeBeDisabled(getItemId(item));
    }

    public static boolean shouldRecipeBeDisabled(String itemid) {
        if (isDisabled(itemid)) return true;

        if (!ItemObliterator.Config.use_hashmap_optimizations) {
            for (String blacklisted_id : ItemObliterator.Config.only_disable_recipes) {
                if (blacklisted_id == null || blacklisted_id.startsWith("//")) continue;

                if (blacklisted_id.equals(itemid)) return true;

                if (blacklisted_id.startsWith("!")) {
                    String regex = blacklisted_id.substring(1);
                    if (itemid.matches(regex)) return true;
                }
            }
        } else {
            return ItemObliterator.only_disable_recipes.contains(itemid);
        }

        return false;
    }

    public static boolean isDisabled(String itemid) {
        if (itemid.equals("minecraft:air")) return false;

        if (!ItemObliterator.Config.use_hashmap_optimizations) {
            for (String blacklisted_id : ItemObliterator.Config.blacklisted_items) {
                if (blacklisted_id == null || blacklisted_id.startsWith("//")) continue;

                if (blacklisted_id.equals(itemid)) return true;

                if (blacklisted_id.startsWith("!")) {
                    String regex = blacklisted_id.substring(1);
                    if (itemid.matches(regex)) return true;
                }
            }
        } else {
            return ItemObliterator.blacklisted_items.contains(itemid);
        }

        return false;
    }

    public static boolean isDisabled(ItemStack stack) {
        if (stack == null || stack.is(Items.AIR)) return false;

        CompoundTag nbt = stack.getTag();
        if (nbt != null) {
            String nbtString = nbt.toString();
            for (String blacklisted_nbt : ItemObliterator.Config.blacklisted_nbt) {
                if (blacklisted_nbt == null || blacklisted_nbt.startsWith("//")) continue;

                if (nbtString.contains(blacklisted_nbt)) return true;

                if (blacklisted_nbt.startsWith("!")) {
                    String regex = blacklisted_nbt.substring(1);
                    if (nbtString.matches(regex)) return true;
                }
            }
        }

        return isDisabled(getItemId(stack.getItem()));
    }

    public static boolean isDisabledInteract(String itemid) {
        for (String blacklisted_id : ItemObliterator.Config.only_disable_interactions) {
            if (blacklisted_id == null || blacklisted_id.startsWith("//")) continue;

            if (blacklisted_id.equals(itemid)) return true;

            if (blacklisted_id.startsWith("!")) {
                String regex = blacklisted_id.substring(1);
                if (itemid.matches(regex)) return true;
            }
        }

        return false;
    }

    public static boolean isDisabledInteract(ItemStack stack) {
        return stack != null && isDisabledInteract(getItemId(stack.getItem()));
    }

    public static boolean isDisabledAttack(String itemid) {
        for (String blacklisted_id : ItemObliterator.Config.only_disable_attacks) {
            if (blacklisted_id == null || blacklisted_id.startsWith("//")) continue;

            if (blacklisted_id.equals(itemid)) return true;

            if (blacklisted_id.startsWith("!")) {
                String regex = blacklisted_id.substring(1);
                if (itemid.matches(regex)) return true;
            }
        }

        return false;
    }
}
