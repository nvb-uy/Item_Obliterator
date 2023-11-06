package elocindev.item_obliterator.forge.mixin;

import java.util.Iterator;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import elocindev.item_obliterator.forge.ItemObliterator;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

@Mixin(value = CreativeModeTab.class, priority = 10000)
public abstract class ItemGroupMixin {
    @SuppressWarnings("rawtypes")
    @Inject(at = @At("HEAD"), method = "fillItemList(Lnet/minecraft/core/NonNullList;)V", cancellable = true)
	private void appendStacks(NonNullList<ItemStack> stacks, CallbackInfo info) {

		Iterator var2 = ForgeRegistries.ITEMS.iterator();

		while(var2.hasNext()) {			
			Item item = (Item)var2.next();
			String itemid = ForgeRegistries.ITEMS.getKey(item).toString();

			if (!(ItemObliterator.Config.blacklisted_items.contains(itemid))) {
				item.fillItemCategory((CreativeModeTab)(Object)this, stacks);	
			}
		}
		info.cancel();
	}
}