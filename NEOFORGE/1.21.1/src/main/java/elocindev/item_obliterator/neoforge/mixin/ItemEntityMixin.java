package elocindev.item_obliterator.neoforge.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import elocindev.item_obliterator.forge.utils.Utils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {
    @Inject(at = @At("TAIL"), method = "readAdditionalSaveData", cancellable = true)
    public void item_obliterator$discardItemEntities(CompoundTag nbt, CallbackInfo info) {
        ItemStack item = ((ItemEntity)(Object)this).getItem();
        
        if (Utils.isDisabled(item)) {
            ((ItemEntity)(Object)this).remove(RemovalReason.DISCARDED);
        }
    }
}
