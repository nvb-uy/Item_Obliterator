package elocindev.item_obliterator.fabric_quilt;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import elocindev.item_obliterator.fabric_quilt.config.ConfigBuilder;
import elocindev.item_obliterator.fabric_quilt.config.ConfigEntries;

public class ItemObliterator implements ModInitializer {
	public static final String MODID = "item_obliterator";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
	
	public static ConfigEntries Config = ConfigBuilder.loadConfig();

	@Override
	public void onInitialize() {
		ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((server, resourceManager, success)
		-> Config = ConfigBuilder.loadConfig());
		LOGGER.info("Item Obliterator Config Loaded");
	}
}
