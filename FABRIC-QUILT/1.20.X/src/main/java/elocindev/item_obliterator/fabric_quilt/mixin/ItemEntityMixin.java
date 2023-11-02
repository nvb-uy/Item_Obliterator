package elocindev.item_obliterator.fabric_quilt.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import elocindev.item_obliterator.fabric_quilt.util.Utils;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {

    @Inject(at = @At("HEAD"), method = "tick")
    public void item_obliterator$discardItemEntities(CallbackInfo info) {
        Item item = ((ItemEntity)(Object)this).getStack().getItem();
        if (Utils.isDisabled(Utils.getItemId(item))) {
            ((ItemEntity)(Object)this).discard();
        }
    }
}
