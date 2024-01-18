package elocindev.item_obliterator.fabric_quilt.plugin;

import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import elocindev.item_obliterator.fabric_quilt.util.Utils;

public class IOEmiPlugin implements EmiPlugin {

    @Override
    public void register(EmiRegistry registry) {
        registry.removeEmiStacks(stack -> Utils.isDisabled(stack.getItemStack()));
    }
}
