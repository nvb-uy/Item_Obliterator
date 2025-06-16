package elocindev.item_obliterator.neoforge.event;

import elocindev.item_obliterator.neoforge.utils.Utils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;

public class VillagerTradeEvent {

    @SubscribeEvent
    public static void onVillagerTrades(VillagerTradesEvent event) {
        event.getTrades().forEach((level, trades) -> {
            trades.removeIf(itemListing -> {
                try {
                    MerchantOffer offer = itemListing.getOffer(null, RandomSource.create());
                    if (offer == null) return false;
                    return Utils.isDisabled(offer);
                } catch (NullPointerException e) {
                    return false;
                }
            });
        });
    }
}
