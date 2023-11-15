package elocindev.item_obliterator.fabric_quilt;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import elocindev.item_obliterator.fabric_quilt.config.ConfigEntries;
import elocindev.item_obliterator.fabric_quilt.plugin.FDCompatibility;
import elocindev.necronomicon.api.config.v1.NecConfigAPI;

public class ItemObliterator implements ModInitializer {
	public static final String MODID = "item_obliterator";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
	
	public static ConfigEntries Config = ConfigEntries.INSTANCE;

	@Override
	public void onInitialize() {
		// renaming old pre 1.5.0 file (item-obliterator.json) to new file (item_obliterator.json)
		Path oldFile = FabricLoader.getInstance().getConfigDir().resolve("item-obliterator.json");
		
		if (oldFile.toFile().exists()) oldFile.toFile().renameTo(FabricLoader.getInstance().getConfigDir().resolve("item_obliterator.json").toFile());

		ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((server, resourceManager, success) -> {
			NecConfigAPI.registerConfig(ConfigEntries.class);
			Config = ConfigEntries.INSTANCE;
		});
	
		NecConfigAPI.registerConfig(ConfigEntries.class);
		Config = ConfigEntries.INSTANCE;
		
		LOGGER.info("Item Obliterator Config Loaded");
		FDCompatibility.init();
	}
}
