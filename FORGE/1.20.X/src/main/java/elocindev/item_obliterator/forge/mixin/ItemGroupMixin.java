package elocindev.item_obliterator.forge.mixin;

import java.util.Collection;
import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import elocindev.item_obliterator.forge.ItemObliterator;
import elocindev.item_obliterator.forge.utils.Utils;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackLinkedSet;

@Mixin(CreativeModeTab.class)
public abstract class ItemGroupMixin {
    @Shadow private Collection<ItemStack> displayItems = ItemStackLinkedSet.createTypeAndTagSet();
    @Shadow private Set<ItemStack> displayItemsSearchTab = ItemStackLinkedSet.createTypeAndTagSet();

    @Inject(method = "buildContents", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/CreativeModeTab;rebuildSearchTree()V"))
    private void item_obliterator_buildContents(CreativeModeTab.ItemDisplayParameters displayContext, CallbackInfo ci) {
        displayItems.removeIf(stack -> ItemObliterator.Config.blacklisted_items.contains(Utils.getItemId(stack.getItem())));
        displayItemsSearchTab.removeIf(stack -> ItemObliterator.Config.blacklisted_items.contains(Utils.getItemId(stack.getItem())));
    }
}