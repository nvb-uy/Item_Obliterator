package elocindev.item_obliterator.neoforge.mixin;

import java.util.Collection;
import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import elocindev.item_obliterator.neoforge.utils.Utils;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackLinkedSet;

@Mixin(value = CreativeModeTab.class, priority = 10000)
public abstract class ItemGroupMixin {
    @Shadow private Collection<ItemStack> displayItems = ItemStackLinkedSet.createTypeAndComponentsSet();
    @Shadow private Set<ItemStack> displayItemsSearchTab = ItemStackLinkedSet.createTypeAndComponentsSet();

    @Inject(method = "buildContents", at = @At(value = "RETURN"))
    private void item_obliterator_buildContents(CreativeModeTab.ItemDisplayParameters displayContext, CallbackInfo ci) {
        displayItems.removeIf(Utils::isDisabled);
        displayItemsSearchTab.removeIf(Utils::isDisabled);
    }
}