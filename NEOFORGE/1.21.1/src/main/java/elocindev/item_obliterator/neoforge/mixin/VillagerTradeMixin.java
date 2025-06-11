package elocindev.item_obliterator.neoforge.mixin; // Keeping original structure

import elocindev.item_obliterator.neoforge.utils.Utils;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.minecraft.world.item.trading.MerchantOffer;

public class VillagerTradeMixin {
    public static void register(net.neoforged.api.modding.v1.ModLoader modLoader) {
        modLoader.getEnvironment().getEventBus().addListener(VillagerTradeMixin::onVillagerTrades);
    }

    private static void onVillagerTrades(VillagerTradesEvent event) {
        event.getTrades().forEach((level, trades) -> {
            trades.removeIf(offer -> Utils.isDisabled(offer.getResult()));
        });
    }
}
