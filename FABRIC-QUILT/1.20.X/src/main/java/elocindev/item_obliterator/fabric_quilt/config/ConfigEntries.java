package elocindev.item_obliterator.fabric_quilt.config;

import java.util.ArrayList;
import java.util.List;

import elocindev.necronomicon.api.config.v1.NecConfigAPI;
import elocindev.necronomicon.config.Comment;
import elocindev.necronomicon.config.NecConfig;


public class ConfigEntries {
	@NecConfig
	public static ConfigEntries INSTANCE;
	
	public static String getFile() {
		return NecConfigAPI.getFile("item_obliterator.json5");
	}
	@Comment("-----------------------------------------------------------")
	@Comment("             Item Obliterator by ElocinDev")
	@Comment("-----------------------------------------------------------")
	@Comment(" ")
	@Comment("How to add items?")
	@Comment("  - They are json strings, so you need to separate each")
	@Comment("    entry with a comma, except the last")
	@Comment("  - If you start an entry with !, it will be treated as a regular expression")
	@Comment("    Example: \"!minecraft:.*_sword\" to disable all swords")
	@Comment(" ")
	@Comment("-----------------------------------------------------------")
	@Comment("Do not touch this")
	public String configVersion = "2.0.0";

	@Comment("-----------------------------------------------------------")

	@Comment("Items here will be unusable completely")
	@Comment("   Example: minecraft:diamond")
	public List<String> blacklisted_items = new ArrayList<>() {{
		add("examplemod:example_item");
	}};

	@Comment("-----------------------------------------------------------")

	@Comment("Add an effect to make the potion turn into a water bottle if it matches.")
	@Comment("   Example: minecraft:water_breathing")
	public List<String> blacklisted_potions = new ArrayList<>() {{
		add("examplemod:example_effect");
	}};

	@Comment("-----------------------------------------------------------")

	@Comment("Items here will not be able to be right-clicked (Interact)")
	@Comment("   Example: minecraft:apple")
  	public List<String> only_disable_interactions = new ArrayList<>() {{
		add("examplemod:example_item");
    }};

	@Comment("-----------------------------------------------------------")

	@Comment("Items here will not be able to be used to attack")
	@Comment("   Example: minecraft:diamond_sword")
	public List<String> only_disable_attacks = new ArrayList<>() {{
		add("examplemod:example_item");
	}};

	@Comment("-----------------------------------------------------------")

	@Comment("Items here will get their recipes disabled")
	@Comment("Keep in mind this already is applied to blacklisted items")
	public List<String> only_disable_recipes = new ArrayList<>() {{
		add("examplemod:example_item");
	}};

	@Comment("-----------------------------------------------------------")

	@Comment("If true, the mod will use a hashmap to handle the blacklisted items")
	@Comment("This is a more optimized approach only if you have a lot of items blacklisted")
	@Comment("If you have a small amount of items blacklisted, it is recommended to keep this false")
	public boolean use_hashmap_optimizations = false;

	@Comment("-----------------------------------------------------------")

	@Comment("Removes the item when the itemstack is created, instead of when it's on the player's inventory")
	@Comment("The most optimized approach and most seamless, but it might cause some side effects, so beware")
	public boolean blacklist_on_creation = true;
}