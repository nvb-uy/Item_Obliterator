package elocindev.item_obliterator.fabric_quilt.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import elocindev.item_obliterator.fabric_quilt.util.Utils;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {
    @Inject(at = @At("HEAD"), method = "tick", cancellable = true)
    public void discardItemEntities(CallbackInfo info) {
        ItemStack item = ((ItemEntity)(Object)this).getStack();
        if (Utils.isDisabled(item)) {
            ((ItemEntity)(Object)this).discard();
        }
    }
}
