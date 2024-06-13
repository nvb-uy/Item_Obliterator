package elocindev.item_obliterator.fabric_quilt.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import elocindev.item_obliterator.fabric_quilt.util.Utils;
import net.minecraft.screen.MerchantScreenHandler;
import net.minecraft.village.TradeOfferList;

@Mixin(MerchantScreenHandler.class)
public class VillagerTradeMixin {
    @Inject(at = {@At("RETURN")}, method = "getRecipes", cancellable = true)
    public void item_obliterator$removeTrades(CallbackInfoReturnable<TradeOfferList> info) {
        if(info.getReturnValue() != null) {
            
            // TradeOfferList Offers = new TradeOfferList(Util.make(new NbtCompound(), 
            //     tag -> tag.put("Recipes", new NbtList())));
            TradeOfferList Offers = new TradeOfferList();

            info.getReturnValue().forEach(offer -> {
                if(!Utils.isDisabled(offer.getSellItem()))
                    Offers.add(offer);
            });
            
            info.setReturnValue(Offers);
        }
    }
}
