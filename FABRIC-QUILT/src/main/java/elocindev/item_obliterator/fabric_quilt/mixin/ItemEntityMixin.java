package elocindev.item_obliterator.fabric_quilt.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import elocindev.item_obliterator.fabric_quilt.ItemObliterator;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {

    @Inject(at = @At("HEAD"), method = "tick")
    public void tick(CallbackInfo info) {
        Item item = ((ItemEntity)(Object)this).getStack().getItem();
        if (ItemObliterator.Config.blacklisted_items.contains(
            Registries.ITEM.getId(item).toString()
        )) {
            ((ItemEntity)(Object)this).discard();
        }
    }
}
