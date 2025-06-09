package elocindev.item_obliterator.neoforge;

import com.mojang.logging.LogUtils;
import elocindev.item_obliterator.neoforge.config.ConfigEntries; // 🔄 Changed to neoforge package
import elocindev.item_obliterator.neoforge.utils.Utils;
import elocindev.necronomicon.api.config.v1.NecConfigAPI;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;

import net.neoforged.neoforge.event.lifecycle.FMLCommonSetupEvent;

import net.neoforged.bus.api.EventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.player.PlayerContainerEvent;

import org.slf4j.Logger;

import java.util.HashSet;
import java.util.Set;

@Mod(ItemObliterator.MODID)
public class ItemObliterator {
    public static final String MODID = "item_obliterator";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static ConfigEntries Config = ConfigEntries.INSTANCE;

    public static Set<String> blacklisted_items;
    public static Set<String> blacklisted_nbt;
    public static Set<String> only_disable_interactions;
    public static Set<String> only_disable_attacks;
    public static Set<String> only_disable_recipes;

    public ItemObliterator() {
        // NeoForge auto-registers @SubscribeEvent handlers on this class via mod constructor
        EventBus modBus = net.neoforged.fml.ModLoadingContext.get().getModEventBus();
        modBus.addListener(this::commonSetup);

        NeoForge.EVENT_BUS.register(this); // Replaces MinecraftForge.EVENT_BUS
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        NecConfigAPI.registerConfig(ConfigEntries.class);
        Config = ConfigEntries.INSTANCE;
        LOGGER.info("Loaded Item Obliterator Config");

        ItemObliterator.reloadConfigHashsets();
    }

    @net.neoforged.bus.api.SubscribeEvent
    public void removeFromInventory(PlayerContainerEvent event) {
        for (ItemStack item : event.getContainer().getItems()) {
            if (Config.blacklisted_items.contains(Utils.getItemId(item.getItem()))) {
                item.setCount(0);
            }
        }
    }

    @net.neoforged.bus.api.SubscribeEvent
    public void removeFromWorld(EntityJoinLevelEvent event) {
        if (!(event.getEntity() instanceof ItemEntity item)) return;

        if (!item.getItem().isEmpty()) {
            if (Utils.isDisabled(item.getItem())) {
                item.remove(Entity.RemovalReason.DISCARDED);
            }
        }
    }

    public static void reloadConfigHashsets() {
        blacklisted_items = new HashSet<>(Config.blacklisted_items);
        blacklisted_nbt = new HashSet<>(Config.blacklisted_nbt);
        only_disable_interactions = new HashSet<>(Config.only_disable_interactions);
        only_disable_attacks = new HashSet<>(Config.only_disable_attacks);
        only_disable_recipes = new HashSet<>(Config.only_disable_recipes);
    }
}
