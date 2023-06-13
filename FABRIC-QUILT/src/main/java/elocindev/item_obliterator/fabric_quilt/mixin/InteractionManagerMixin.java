package elocindev.item_obliterator.fabric_quilt.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import elocindev.item_obliterator.fabric_quilt.ItemObliterator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

@Mixin(ServerPlayerInteractionManager.class)
public class InteractionManagerMixin {
  
  // Cancelling interaction when it's on only_disable_interactions
  @Inject(method = "interactItem", at = @At("HEAD"), cancellable = true)
  private void cancelInteraction(ServerPlayerEntity player, World world, ItemStack stack, Hand hand, CallbackInfoReturnable<ActionResult> ci) {
    Item item = stack.getItem();
    String itemid = Registries.ITEM.getId(item).toString();

    if (ItemObliterator.Config.only_disable_interactions.contains(itemid)) {
      player.sendMessageToClient(Text.of("This item's interactions are disabled."), true);
      ci.setReturnValue(ActionResult.FAIL);
    }
  }
}

