package elocindev.item_obliterator.forge;

import com.mojang.logging.LogUtils;

import elocindev.item_obliterator.forge.config.ConfigEntries;
import elocindev.item_obliterator.forge.utils.Utils;
import elocindev.necronomicon.api.config.v1.NecConfigAPI;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

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

    public ItemObliterator() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        NecConfigAPI.registerConfig(ConfigEntries.class);
        Config = ConfigEntries.INSTANCE;
        LOGGER.info("Loaded Item Obliterator Config");

        ItemObliterator.reloadConfigHashsets();
    }

    @SubscribeEvent
    public void item_obliterator$removeFromInventory(PlayerContainerEvent event) {
        for (ItemStack item : event.getContainer().getItems()) {
            if (Config.blacklisted_items.contains(Utils.getItemId(item.getItem()))) {
                item.setCount(0);
            }
        }
    }

    @SubscribeEvent
    public void item_obliterator$removeFromWorld(EntityJoinLevelEvent event) {
        if (!(event.getEntity() instanceof ItemEntity item)) return;
            
        if (!item.getItem().isEmpty()) {
            if (Utils.isDisabled(item.getItem())) {                
                item.remove(Entity.RemovalReason.DISCARDED);
            }
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
