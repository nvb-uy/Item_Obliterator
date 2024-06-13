package elocindev.item_obliterator.fabric_quilt.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import elocindev.item_obliterator.fabric_quilt.ItemObliterator;
import net.minecraft.client.network.ClientPlayerEntity;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerMixin {


    private int tickCount = 0;

    @Inject(at = @At("TAIL"), method = "tick")
    public void item_obliterator$printDebug(CallbackInfo info) {
        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;

        if (!ItemObliterator.Config.debug_print_components) return;

        tickCount++;

        if (tickCount >= 20) {
            tickCount = 0;
            
            if (!player.getMainHandStack().isEmpty())
            for (var component : player.getMainHandStack().getComponents()) {
                ItemObliterator.LOGGER.info(component.toString());
            }
        }
    }
}
