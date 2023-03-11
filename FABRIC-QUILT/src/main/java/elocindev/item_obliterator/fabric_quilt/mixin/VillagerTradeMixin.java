package elocindev.item_obliterator.fabric_quilt.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import elocindev.item_obliterator.fabric_quilt.ItemObliterator;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.MerchantScreenHandler;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.TradeOfferList;

@Mixin(MerchantScreenHandler.class)
public class VillagerTradeMixin {
    // Credits to https://github.com/pitbox46/ItemBlacklist
    // wouldn't know how to disable the trades without that mod.

    @Inject(at = {@At("RETURN")}, method = {"getRecipes"}, cancellable = true)
    public void getRecipes(CallbackInfoReturnable<TradeOfferList> info) {
        if(info.getReturnValue() != null) {
            
            TradeOfferList Offers = new TradeOfferList(Util.make(new NbtCompound(), 
                tag -> tag.put("Recipes", new NbtList())));

            info.getReturnValue().forEach(offer -> {
                if(!ItemObliterator.Config.blacklisted_items.contains(Registry.ITEM.getId(offer.getSellItem().getItem()).toString()))
                    Offers.add(offer);
            });
            
            info.setReturnValue(Offers);
        }
    }
}
