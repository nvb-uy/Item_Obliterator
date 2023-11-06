package elocindev.item_obliterator.forge;

import com.mojang.logging.LogUtils;

import elocindev.item_obliterator.forge.config.ConfigEntries;
import elocindev.item_obliterator.forge.utils.Utils;
import elocindev.necronomicon.api.config.v1.NecConfigAPI;
import net.minecraft.world.entity.Entity.RemovalReason;
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
import org.slf4j.Logger;

@Mod(ItemObliterator.MODID)
public class ItemObliterator {
    public static final String MODID = "item_obliterator";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static ConfigEntries Config = ConfigEntries.INSTANCE;

    public ItemObliterator() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        NecConfigAPI.registerConfig(ConfigEntries.class);
        Config = ConfigEntries.INSTANCE;
        LOGGER.info("Loaded Item Obliterator Config");
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
            if (Config.blacklisted_items.contains(Utils.getItemId(item.getItem().getItem()))) {                
                item.remove(RemovalReason.DISCARDED);
            }
        }
    }
}
