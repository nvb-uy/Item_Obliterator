package elocindev.item_obliterator.fabric_quilt.mixin;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;

import java.util.Iterator;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import elocindev.item_obliterator.fabric_quilt.config.ConfigBuilder;
import elocindev.item_obliterator.fabric_quilt.config.ConfigEntries;

@Mixin(ItemGroup.class)
public class ItemGroupMixin {
	
	@Inject(at = @At("HEAD"), method = "appendStacks(Lnet/minecraft/util/collection/DefaultedList;)V", cancellable = true)
	private void appendStacks(DefaultedList<ItemStack> stacks, CallbackInfo info) {
		ConfigEntries Config = ConfigBuilder.loadConfig();

		Iterator var2 = Registry.ITEM.iterator();

		while(var2.hasNext()) {
			Config = ConfigBuilder.loadConfig();
			
			Item item = (Item)var2.next();
			String itemid = Registry.ITEM.getId(item).toString();

			if (!(Config.blacklisted_items.contains(itemid))) {
				item.appendStacks((ItemGroup)(Object)this, stacks);	
			}
		}
		info.cancel();
	}
}





