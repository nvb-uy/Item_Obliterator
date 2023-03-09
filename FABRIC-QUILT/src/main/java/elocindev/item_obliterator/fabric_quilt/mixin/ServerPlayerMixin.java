package elocindev.item_obliterator.fabric_quilt.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import elocindev.item_obliterator.fabric_quilt.ItemObliterator;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.registry.Registry;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerMixin {
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;"), method = "playerTick", locals = LocalCapture.CAPTURE_FAILHARD)
    public void playerTick(CallbackInfo info, int i) {
        
        ItemStack item = ((ServerPlayerEntity)(Object)this).getInventory().getStack(i);
        String itemid = Registry.ITEM.getId(item.getItem()).toString();
        
        if (ItemObliterator.Config.blacklisted_items.contains(itemid)) {
            item.setCount(0);
        }
        
    }
}