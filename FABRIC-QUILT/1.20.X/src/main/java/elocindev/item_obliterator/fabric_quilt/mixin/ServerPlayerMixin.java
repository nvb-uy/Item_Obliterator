package elocindev.item_obliterator.fabric_quilt.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import elocindev.item_obliterator.fabric_quilt.ItemObliterator;
import elocindev.item_obliterator.fabric_quilt.util.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerMixin {
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;"), method = "playerTick", locals = LocalCapture.CAPTURE_FAILHARD)
    public void item_obliterator$removeItemFromInventory(CallbackInfo info, int i) {
        ServerPlayerEntity player = ((ServerPlayerEntity)(Object)this);
        ItemStack item = player.getInventory().getStack(i);
        
        if (Utils.isDisabled(item)) {
            item.setCount(0);
            player.sendMessageToClient(Text.translatable("item_obliterator.disabled_item"), true);
        }
    }

    // Cancelling attacks if the item is in only_disable_attacks
    @Inject(method = "attack", at = @At("HEAD"), cancellable = true)
    private void item_obliterator$cancelItemUse(Entity target, CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity)(Object)this;
        Item item = player.getMainHandStack().getItem();
        String itemid = Utils.getItemId(item);

        if (check(itemid)) {
            player.sendMessageToClient(Text.of("This item's attacks are disabled."), true);
            ci.cancel();
        }
    }

    private static boolean check(String itemid) {
		if (ItemObliterator.Config.use_hashmap_optimizations) return ItemObliterator.only_disable_attacks.contains(itemid);
		else return Utils.isDisabledAttack(itemid);
	}
}