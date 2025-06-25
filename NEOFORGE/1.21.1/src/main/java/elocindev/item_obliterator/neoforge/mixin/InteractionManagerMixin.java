package elocindev.item_obliterator.neoforge.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import elocindev.item_obliterator.neoforge.utils.Utils;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;


@Mixin(value = ServerPlayerGameMode.class, priority = 10000)
public class InteractionManagerMixin {
  
  // Cancelling interaction when it's on only_disable_interactions
  @Inject(method = "useItem", at = @At("HEAD"), cancellable = true)
  private void cancelInteraction(ServerPlayer player, Level world, ItemStack stack, InteractionHand hand, CallbackInfoReturnable<InteractionResult> ci) {
    if (Utils.isDisabledInteract(stack)) {
      player.sendSystemMessage(Component.literal("this item's interaction has been disabled"), true);
      ci.setReturnValue(InteractionResult.FAIL);
    }
  }
}

