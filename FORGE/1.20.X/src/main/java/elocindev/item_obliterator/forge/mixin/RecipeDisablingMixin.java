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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

@Mixin(net.minecraft.world.item.crafting.RecipeManager.class)
public class RecipeDisablingMixin {
    // Old runtime method, not as good, breaks lots of compat and doesn't perform as good

    // @Inject(at = @At(value = "RETURN"), method = "getFirstMatch(Lnet/minecraft/recipe/RecipeType;Lnet/minecraft/inventory/Inventory;Lnet/minecraft/world/World;)Ljava/util/Optional;", cancellable = true)
    // private <I extends Inventory, T extends Recipe<I>> void item_obliterator$getRecipeForRecipeType(RecipeType<T> recipe, I inventory, World world, CallbackInfoReturnable<Optional<T>> cir) {
    //     cir.getReturnValue().ifPresent(value ->
    //         cir.setReturnValue(Utils.shouldRecipeBeDisabled(value.getOutput(world.getRegistryManager()).getItem()) ? Optional.empty() : Optional.of(value)));
    // }
    
    // @Inject(at = @At(value = "RETURN"), method = "getAllMatches", cancellable = true)
    // private <I extends Inventory, T extends Recipe<I>> void item_obliterator$getRecipesFor(RecipeType<T> recipe, I inventory, World world, CallbackInfoReturnable<List<T>> cir) {
    //     cir.setReturnValue(cir.getReturnValue().stream()
    //             .filter(entry -> !Utils.shouldRecipeBeDisabled(entry.craft(inventory, world.getRegistryManager()).getItem()))
    //             .collect(Collectors.toList()));
    // }

    @Inject(at = @At("HEAD"), method = "apply", cancellable = true)
    private void item_obliterator$apply(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager, ProfilerFiller profiler, CallbackInfo ci) {
        Map<ResourceLocation, JsonElement> filteredMap = new HashMap<>();

        for (Map.Entry<ResourceLocation, JsonElement> entry : map.entrySet()) {
            ResourceLocation ResourceLocation = entry.getKey();
            JsonElement jsonElement = entry.getValue();

            try {
                JsonObject jsonObject = GsonHelper.convertToJsonObject(jsonElement, "top element");
                JsonElement resultElement = jsonObject.get("result");

                if (resultElement == null) {
                    if (jsonObject.get("output") != null) {
                        resultElement = jsonObject.get("output");
                    } else {
                        filteredMap.put(ResourceLocation, jsonElement);
                        continue;
                    }
                }

                boolean shouldDisable = false;

                if (resultElement.isJsonObject()) {
                    String itemId = getResultItemId(resultElement.getAsJsonObject());

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
                            String itemId = getResultItemId(element.getAsJsonObject());

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

    private String getResultItemId(JsonObject resultObject) {
        if (resultObject.has("item")) {
            return GsonHelper.getAsString(resultObject, "item");
        } else if (resultObject.has("id")) {
            return GsonHelper.getAsString(resultObject, "id");
        } else if (resultObject.has("result")) {
            return GsonHelper.getAsString(resultObject, "result");
        } else if (resultObject.has("output")) {
            return GsonHelper.getAsString(resultObject, "output");
        }
        
        return null;
    }
}