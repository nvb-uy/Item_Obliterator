package elocindev.item_obliterator.forge.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import elocindev.item_obliterator.forge.utils.Utils;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

@Mixin(value = ServerPlayer.class, priority = 10000)
public class ServerPlayerMixin {
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getItem()Lnet/minecraft/world/item/Item;"), method = "doTick", locals = LocalCapture.CAPTURE_FAILHARD)
    public void playerTick(CallbackInfo info, int i) {
        
        ItemStack item = ((ServerPlayer)(Object)this).getInventory().getItem(i);
        String itemid = Utils.getItemId(item.getItem());
        
        if (Utils.isDisabled(itemid)) {
            item.setCount(0);
            ((ServerPlayer)(Object)this).sendSystemMessage(Component.literal("This item is disabled."), true);
        }
    }

    // Cancelling attacks if the item is in only_disable_attacks
    @Inject(method = "attack", at = @At("HEAD"), cancellable = true)
    private void onCancelItemUse(Entity target, CallbackInfo ci) {
        ServerPlayer player = (ServerPlayer)(Object)this;
        Item item = player.getMainHandItem().getItem();
        String itemid = Utils.getItemId(item);

        if (Utils.isDisabledAttack(itemid)) {
            player.sendSystemMessage(Component.literal("This item's attacks are disabled."), true);
            ci.cancel();
        }
    }
    @Inject(method = "swing", at = @At("HEAD"), cancellable = true)
    private void cancelSwing(InteractionHand hand, CallbackInfo ci) {
        Player player = (Player)(Object)this;
        Item item = player.getMainHandItem().getItem();
        String itemid = Utils.getItemId(item);

        if (Utils.isDisabledAttack(itemid)) {
            ci.cancel();
        }
    }
}