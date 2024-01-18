package elocindev.item_obliterator.fabric_quilt.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import elocindev.item_obliterator.fabric_quilt.util.Utils;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.item.ItemStack;

@Mixin(LootableContainerBlockEntity.class)
public class LootContainerMixin {
    @Inject(at = @At("HEAD"), method = "setStack", cancellable = true)
    public void item_obliterator$disableItemfromLootContainer(int slot, ItemStack stack, CallbackInfo info) {
        if (Utils.isDisabled(stack)) {
            stack.setCount(0);
        }
    }
}
