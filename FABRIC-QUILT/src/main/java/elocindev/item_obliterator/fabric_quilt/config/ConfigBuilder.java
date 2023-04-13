package elocindev.item_obliterator.fabric_quilt.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.fabricmc.loader.api.FabricLoader;

public class ConfigBuilder {
    public static final Gson BUILDER = (new GsonBuilder()).setPrettyPrinting().create();

    public static final Path file = FabricLoader.getInstance().getConfigDir()
            .resolve("item-obliterator.json");

    public static ConfigEntries loadConfig() {
        try {
            if (Files.notExists(file)) {
                ConfigEntries exampleConfig = new ConfigEntries();

                exampleConfig.blacklisted_items.add("examplemod:example_item");
                exampleConfig.only_disable_interactions.add("examplemod:example_item");
                exampleConfig.only_disable_attacks.add("examplemod:example_item");

                String defaultJson = BUILDER.toJson(exampleConfig);
                Files.writeString(file, defaultJson);
            } else {
                String json = Files.readString(file);
                ConfigEntries configEntries = BUILDER.fromJson(json, ConfigEntries.class);

                // Prevent crashing when upgrading from old versions that didn't have the disabled_interactions stuff
                if (configEntries.only_disable_interactions == null) {
                    configEntries.only_disable_interactions = new ArrayList<>();
                    configEntries.only_disable_interactions.add("anymod:example_item");
                }

                if (configEntries.only_disable_attacks == null) {
                    configEntries.only_disable_attacks = new ArrayList<>();
                    configEntries.only_disable_attacks.add("anymod:example_item");
                }

                String updatedJson = BUILDER.toJson(configEntries);
                Files.writeString(file, updatedJson);
                return configEntries;
            }
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        return null;
    }
}

