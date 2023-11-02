package elocindev.item_obliterator.forge.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import elocindev.item_obliterator.forge.ItemObliterator;
import elocindev.item_obliterator.forge.utils.Utils;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {

    @Inject(at = @At("HEAD"), method = "tick")
    public void tick(CallbackInfo info) {
        ItemEntity ent = ((ItemEntity)(Object)this);

        Item item = ent.getItem().getItem();
        if (ItemObliterator.Config.blacklisted_items.contains(Utils.getItemId(item))) {
            ent.discard();
        }
    }
}
