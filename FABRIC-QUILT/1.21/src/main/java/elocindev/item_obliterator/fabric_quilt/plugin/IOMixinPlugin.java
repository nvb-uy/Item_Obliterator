package elocindev.item_obliterator.fabric_quilt.plugin;

import java.util.List;
import java.util.Set;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import elocindev.item_obliterator.fabric_quilt.ItemObliterator;
import elocindev.item_obliterator.fabric_quilt.config.ConfigEntries;
import elocindev.necronomicon.api.config.v1.NecConfigAPI;

public class IOMixinPlugin implements IMixinConfigPlugin  {

    @Override
    public void onLoad(String mixinPackage) {
        NecConfigAPI.registerConfig(ConfigEntries.class);
		ItemObliterator.Config = ConfigEntries.INSTANCE;

        if (ItemObliterator.Config.use_hashmap_optimizations)
            ItemObliterator.reloadConfigHashsets();
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
    
}
