package elocindev.item_obliterator.fabric_quilt.mixin;

import java.util.Collection;
import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import elocindev.item_obliterator.fabric_quilt.util.Utils;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemStackSet;

@Mixin(ItemGroup.class)
public abstract class ItemGroupMixin {
    @Shadow private Collection<ItemStack> displayStacks = ItemStackSet.create();
    @Shadow private Set<ItemStack> searchTabStacks = ItemStackSet.create();

    @Inject(method = "updateEntries", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemGroup;reloadSearchProvider()V"))
    private void updateEntriesMixin(ItemGroup.DisplayContext displayContext, CallbackInfo ci) {
        displayStacks.removeIf(stack -> Utils.isDisabled(Utils.getItemId(stack.getItem())));
        searchTabStacks.removeIf(stack -> Utils.isDisabled(Utils.getItemId(stack.getItem())));
    }
}
