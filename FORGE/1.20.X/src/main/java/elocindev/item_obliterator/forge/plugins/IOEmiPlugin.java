package elocindev.item_obliterator.forge.plugins;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiInitRegistry;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import elocindev.item_obliterator.forge.utils.Utils;

@EmiEntrypoint
public class IOEmiPlugin implements EmiPlugin {
    @Override
    public void initialize(EmiInitRegistry registry) { }

    @Override
    public void register(EmiRegistry emiRegistry) {
        emiRegistry.removeEmiStacks(emiStack -> Utils.isDisabled(emiStack.getId().toString()));
    }
}
