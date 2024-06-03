package elocindev.item_obliterator.forge.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import elocindev.item_obliterator.forge.utils.Utils;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraftforge.registries.ForgeRegistries;

@Mixin(value = MerchantMenu.class, priority = 199)
public class VillagerTradeMixin {
    @Inject(at = {@At("RETURN")}, method = "getOffers", cancellable = true)
    public void getRecipes(CallbackInfoReturnable<MerchantOffers> info) {
        if(info.getReturnValue() != null) {
            
            MerchantOffers Offers = new MerchantOffers(Util.make(new CompoundTag(), 
                tag -> tag.put("Recipes", new ListTag())));

            info.getReturnValue().forEach(offer -> {
                if(!Utils.isDisabled(ForgeRegistries.ITEMS.getKey(offer.getResult().getItem()).toString()))
                    Offers.add(offer);
            });
            
            info.setReturnValue(Offers);
        }
    }
}
