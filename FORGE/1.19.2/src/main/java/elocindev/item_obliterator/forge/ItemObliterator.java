package elocindev.item_obliterator.forge;

import com.mojang.logging.LogUtils;

import elocindev.item_obliterator.forge.config.ConfigEntries;
import elocindev.necronomicon.api.config.v1.NecConfigAPI;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
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

}
