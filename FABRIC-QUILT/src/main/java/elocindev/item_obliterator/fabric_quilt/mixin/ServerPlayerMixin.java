package elocindev.item_obliterator.fabric_quilt.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import elocindev.item_obliterator.fabric_quilt.ItemObliterator;
import elocindev.item_obliterator.fabric_quilt.util.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerMixin {
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;"), method = "playerTick", locals = LocalCapture.CAPTURE_FAILHARD)
    public void playerTick(CallbackInfo info, int i) {
        
        ItemStack item = ((ServerPlayerEntity)(Object)this).getInventory().getStack(i);
        String itemid = Utils.getItemId(item.getItem());
        
        if (ItemObliterator.Config.blacklisted_items.contains(itemid)) {
            item.setCount(0);
            ((ServerPlayerEntity)(Object)this).sendMessageToClient(Text.of("This item is disabled."), true);
        }
        
    }

    // Cancelling attacks if the item is in only_disable_attacks
    @Inject(method = "attack", at = @At("HEAD"), cancellable = true)
    private void onCancelItemUse(Entity target, CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity)(Object)this;
        Item item = player.getMainHandStack().getItem();
        String itemid = Utils.getItemId(item);

        if (ItemObliterator.Config.only_disable_attacks.contains(itemid)) {
            player.sendMessageToClient(Text.of("This item's attacks are disabled."), true);
            ci.cancel();
        }
    }
    @Inject(method = "swingHand", at = @At("HEAD"), cancellable = true)
    private void cancelSwing(Hand hand, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity)(Object)this;
        Item item = player.getMainHandStack().getItem();
        String itemid = Utils.getItemId(item);

        if (ItemObliterator.Config.only_disable_attacks.contains(itemid)) {
            ci.cancel();
        }
    }
}