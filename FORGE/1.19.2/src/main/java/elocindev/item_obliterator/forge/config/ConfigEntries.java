package elocindev.item_obliterator.forge.config;

import java.util.ArrayList;
import java.util.List;

import elocindev.necronomicon.api.config.v1.NecConfigAPI;
import elocindev.necronomicon.config.NecConfig;

public class ConfigEntries {
  	@NecConfig
  	public static ConfigEntries INSTANCE;

  	public static String getFile() {
		return NecConfigAPI.getFile("item_obliterator.json");
	}
  
  	public List<String> blacklisted_items = new ArrayList<>() {{
		add("//Items here will be unusable completely");
		add("examplemod:example_item");
	}};

  	public List<String> only_disable_interactions = new ArrayList<>() {{
		add("//Items here will not be able to be right-clicked (Interact)");
		add("//Keep in mind this already is applied to blacklisted items");
		add("examplemod:example_item");
    }};

	public List<String> only_disable_attacks = new ArrayList<>() {{
		add("//Items here will not be able to be used to attack");
		add("//Keep in mind this already is applied to blacklisted items");
		add("examplemod:example_item");
	}};

	public List<String> only_disable_recipes = new ArrayList<>() {{
		add("//Items here will get their recipes disabled");
		add("//Keep in mind this already is applied to blacklisted items");
		add("examplemod:example_item");
	}};
}