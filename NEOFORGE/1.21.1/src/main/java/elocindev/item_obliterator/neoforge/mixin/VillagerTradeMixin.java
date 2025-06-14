package elocindev.item_obliterator.neoforge.mixin;

import elocindev.item_obliterator.neoforge.utils.Utils;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.trading.MerchantOffers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = MerchantMenu.class, priority = 10000)
public class VillagerTradeMixin {
    @Inject(at = {@At("RETURN")}, method = "getOffers", cancellable = true)
    public void getRecipes(CallbackInfoReturnable<MerchantOffers> info) {
        if(info.getReturnValue() != null) {

            MerchantOffers Offers = new MerchantOffers();

            info.getReturnValue().forEach(offer -> {
                if(!Utils.isDisabled(offer.getResult()))
                    Offers.add(offer);
            });

            info.setReturnValue(Offers);
        }
    }
}
