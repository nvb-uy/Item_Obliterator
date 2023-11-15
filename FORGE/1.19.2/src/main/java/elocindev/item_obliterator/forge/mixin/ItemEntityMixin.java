package elocindev.item_obliterator.forge.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import elocindev.item_obliterator.forge.utils.Utils;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.item.ItemEntity;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {
    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void tick(CallbackInfo info) {
        ItemEntity item = (ItemEntity)(Object)this;

        if (!item.getItem().isEmpty()) {
            if (Utils.isDisabled(Utils.getItemId(item.getItem().getItem()))) {                
                item.remove(RemovalReason.DISCARDED);
            }
        }
    }
}
