package elocindev.item_obliterator.fabric_quilt.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import elocindev.item_obliterator.fabric_quilt.ItemObliterator;
import elocindev.item_obliterator.fabric_quilt.util.Utils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

@Mixin(ServerPlayerInteractionManager.class)
public class InteractionManagerMixin {
  	@Inject(method = "interactItem", at = @At("HEAD"), cancellable = true)
    private void item_obliterator$cancelItemInteraction(ServerPlayerEntity player, World world, ItemStack stack, Hand hand, CallbackInfoReturnable<ActionResult> ci) {
        Item item = stack.getItem();
        String itemid = Registries.ITEM.getId(item).toString();

        if (check(itemid)) {
            player.sendMessageToClient(Text.translatable("item_obliterator.disabled_interaction"), true);
            ci.setReturnValue(ActionResult.FAIL);
        }
	}

	@Inject(method = "interactBlock", at = @At("HEAD"), cancellable = true)
    private void item_obliterator$cancelBlockInteraction(ServerPlayerEntity player, World world, ItemStack stack, Hand hand, BlockHitResult hitResult, CallbackInfoReturnable<ActionResult> ci) {
        Item item = stack.getItem();
        String itemid = Registries.ITEM.getId(item).toString();

        if (check(itemid)) {
            player.sendMessageToClient(Text.translatable("item_obliterator.disabled_interaction"), true);
            ci.setReturnValue(ActionResult.FAIL);
        }
	}

	private static boolean check(String itemid) {
		if (ItemObliterator.Config.use_hashmap_optimizations) return ItemObliterator.only_disable_interactions.contains(itemid);
		else return Utils.isDisabledInteract(itemid);
	}
}