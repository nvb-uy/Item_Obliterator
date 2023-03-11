package elocindev.item_obliterator.fabric_quilt.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.fabricmc.loader.api.FabricLoader;

public class ConfigBuilder {
    public static final Gson BUILDER = (new GsonBuilder()).setPrettyPrinting().create();
  
    public static final Path file = FabricLoader.getInstance().getConfigDir()
    .resolve("item_obliterator.json");
    
    public static ConfigEntries loadConfig() {
      try {
          if (Files.notExists(file)) {
              ConfigEntries exampleConfig = new ConfigEntries();
              exampleConfig.blacklisted_items.add("any_mod:example_item");
              String defaultJson = BUILDER.toJson(exampleConfig);
              Files.writeString(file, defaultJson);
          }

          return BUILDER.fromJson(Files.readString(file), ConfigEntries.class);

      } catch (IOException exception) {
          throw new RuntimeException(exception);
      }
  }
}
