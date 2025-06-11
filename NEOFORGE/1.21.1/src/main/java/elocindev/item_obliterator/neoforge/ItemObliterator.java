package elocindev.item_obliterator.neoforge;

import com.mojang.logging.LogUtils;

import elocindev.item_obliterator.neoforge.config.ConfigEntries;
import elocindev.item_obliterator.neoforge.utils.Utils;
import elocindev.necronomicon.api.config.v1.NecConfigAPI;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerContainerEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;

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

    public ItemObliterator(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        NeoForge.EVENT_BUS.register(this);

        //modContainer.registerConfig(ModConfig.Type.COMMON, elocindev.item_obliterator.neoforge.config.ConfigHandler.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            NecConfigAPI.registerConfig(ConfigEntries.class);
            Config = ConfigEntries.INSTANCE;
            LOGGER.info("Loaded NeoForge Item Obliterator config.");

            ItemObliterator.reloadConfigHashsets();
        });
    }

    @SubscribeEvent
    public void onPlayerContainer(PlayerContainerEvent event) {
        for (ItemStack item : event.getContainer().getItems()) {
            if (Config.blacklisted_items.contains(Utils.getItemId(item.getItem()))) {
                item.setCount(0);
            }
        }
    }

    @SubscribeEvent
    public void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if (!(event.getEntity() instanceof ItemEntity item)) return;

        if (!item.getItem().isEmpty() && Utils.isDisabled(item.getItem())) {
            item.remove(Entity.RemovalReason.DISCARDED);
        }
    }

    public static void reloadConfigHashsets() {
        blacklisted_items = new HashSet<>(ItemObliterator.Config.blacklisted_items);
        blacklisted_nbt = new HashSet<>(ItemObliterator.Config.blacklisted_nbt);
        only_disable_interactions = new HashSet<>(ItemObliterator.Config.only_disable_interactions);
        only_disable_attacks = new HashSet<>(ItemObliterator.Config.only_disable_attacks);
        only_disable_recipes = new HashSet<>(ItemObliterator.Config.only_disable_recipes);
    }
}
