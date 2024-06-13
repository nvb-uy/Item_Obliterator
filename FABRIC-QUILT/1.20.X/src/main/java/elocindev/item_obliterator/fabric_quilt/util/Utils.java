package elocindev.item_obliterator.fabric_quilt.util;

import dev.emi.emi.api.stack.EmiStack;
import elocindev.item_obliterator.fabric_quilt.ItemObliterator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;

public class Utils {
    public static String getItemId(Item item) {
        return Registries.ITEM.getId(item).toString();
    }  

    public static boolean shouldRecipeBeDisabled(Item item) {
        return shouldRecipeBeDisabled(getItemId(item));
    }

    public static boolean shouldRecipeBeDisabled(String itemid) {
        if (isDisabled(itemid)) return true;

        if (!ItemObliterator.Config.use_hashmap_optimizations) {
            for (String blacklisted_id : ItemObliterator.Config.only_disable_recipes) {
                if (blacklisted_id == null) continue;
                if (blacklisted_id.startsWith("//")) continue;
                
                if (blacklisted_id.equals(itemid)) return true;

                if (blacklisted_id.startsWith("!")) {
                    blacklisted_id = blacklisted_id.substring(1);

                    if (itemid.matches(blacklisted_id)) return true;
                }
            }
        } else {
            if (ItemObliterator.only_disable_recipes.contains(itemid)) return true;
        }

        return false;
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

    // Emi compat stuff
    public static boolean isDisabled(EmiStack emiStack) {
        if (emiStack == null) return false;
    
        if (emiStack.hasNbt() && isDisabled(emiStack.getNbt())) return true;
    
        if (emiStack.getKey() instanceof Item item) {
            return isDisabled(getItemId(item));
        }
    
        return false;
    }
    

    public static boolean isDisabled(ItemStack stack) {
        if (stack == null || stack.isOf(Items.AIR)) return false;

        if (stack.hasNbt()) {
            isDisabled(stack.getNbt());
        }
        
        return isDisabled(getItemId(stack.getItem()));
    }

    public static boolean isDisabled(NbtCompound nbt) {
        if (nbt == null) return false;

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
        
        return false;
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
