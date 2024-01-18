package elocindev.item_obliterator.fabric_quilt.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import elocindev.item_obliterator.fabric_quilt.ItemObliterator;
import elocindev.item_obliterator.fabric_quilt.util.Utils;
import net.minecraft.item.ItemStack;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    
    @Inject(method = "<init>", at = @At("RETURN"))
    private void item_obliterator$disableOnItemConstruct(CallbackInfo info) {
        ItemStack stack = (ItemStack)(Object)this;
        if (stack != null && Utils.isDisabled(Utils.getItemId(stack.getItem()))) {
            stack.setCount(0);
            ItemObliterator.LOGGER.debug(stack.getClass()+" was removed on creation. If this caused a crash, disable blacklist_on_creation in the config.");
        };
    }        
}