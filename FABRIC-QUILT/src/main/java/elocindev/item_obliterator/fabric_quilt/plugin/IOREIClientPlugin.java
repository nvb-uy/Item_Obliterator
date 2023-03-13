package elocindev.item_obliterator.fabric_quilt.plugin;

import java.util.List;
import java.util.stream.Stream;

import elocindev.item_obliterator.fabric_quilt.ItemObliterator;
import me.shedaniel.rei.api.client.entry.filtering.base.BasicFilteringRule;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.entry.EntryRegistry;
import me.shedaniel.rei.api.common.entry.EntryStack;

public class IOREIClientPlugin implements REIClientPlugin {
    @Override
    public void registerBasicEntryFiltering(BasicFilteringRule<?> rule) {
        Stream<EntryStack<?>> stacks = EntryRegistry.getInstance().getEntryStacks();
        List<String> Items = ItemObliterator.Config.blacklisted_items;

        stacks.forEach(entryStack -> {
            if (Items.contains(entryStack.getIdentifier().toString())) {
                rule.hide(entryStack);
            }
        });
        
    }
}