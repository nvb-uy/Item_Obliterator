package elocindev.item_obliterator.forge.mixin;

import java.util.HashMap;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import elocindev.item_obliterator.forge.ItemObliterator;
import elocindev.item_obliterator.forge.utils.Utils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.crafting.RecipeManager;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

@Mixin(value = RecipeManager.class, priority = 199)
public class RecipeDisablingMixin {

    @Inject(at = @At("HEAD"), method = "apply", cancellable = true)
    private void item_obliterator$apply(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager, ProfilerFiller profiler, CallbackInfo ci) {
        Map<ResourceLocation, JsonElement> filteredMap = new HashMap<>();

        for (Map.Entry<ResourceLocation, JsonElement> entry : map.entrySet()) {
            ResourceLocation ResourceLocation = entry.getKey();
            JsonElement jsonElement = entry.getValue();

            try {
                JsonElement resultElement = GsonHelper.convertToJsonObject(jsonElement, "top element").get("result");

                if (resultElement == null) {
                    filteredMap.put(ResourceLocation, jsonElement);
                    continue;
                }

                boolean shouldDisable = false;

                if (resultElement.isJsonObject()) {
                    JsonObject resultObject = resultElement.getAsJsonObject();
                    String itemId = GsonHelper.getAsString(resultObject, "item");

                    if (itemId != null && Utils.shouldRecipeBeDisabled(itemId)) {
                        shouldDisable = true;
                    }
                } else if (resultElement.isJsonPrimitive() && resultElement.getAsJsonPrimitive().isString()) {
                    String itemId = resultElement.getAsString();

                    if (itemId != null && Utils.shouldRecipeBeDisabled(itemId)) {
                        shouldDisable = true;
                    }
                } else if (resultElement.isJsonArray()) {
                    JsonArray resultArray = resultElement.getAsJsonArray();

                    for (JsonElement element : resultArray) {
                        if (element.isJsonObject()) {
                            JsonObject resultObject = element.getAsJsonObject();
                            String itemId = GsonHelper.getAsString(resultObject, "item");

                            if (itemId != null && Utils.shouldRecipeBeDisabled(itemId)) {
                                shouldDisable = true;
                                break;
                            }
                        } else if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isString()) {
                            String itemId = element.getAsString();

                            if (itemId != null && Utils.shouldRecipeBeDisabled(itemId)) {
                                shouldDisable = true;
                                break;
                            }
                        }
                    }
                }

                if (!shouldDisable) {
                    filteredMap.put(ResourceLocation, jsonElement);
                }

            } catch (IllegalArgumentException | JsonParseException e) {
                ItemObliterator.LOGGER.debug("Parsing error loading recipe {}", ResourceLocation, e);
                filteredMap.put(ResourceLocation, jsonElement);
            }
        }

        map.clear();
        map.putAll(filteredMap);
    }

}
