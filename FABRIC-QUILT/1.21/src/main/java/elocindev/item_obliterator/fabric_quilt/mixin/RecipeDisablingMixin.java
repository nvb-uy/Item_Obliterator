package elocindev.item_obliterator.fabric_quilt.mixin;

import java.util.HashMap;
import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import elocindev.item_obliterator.fabric_quilt.ItemObliterator;
import elocindev.item_obliterator.fabric_quilt.util.Utils;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.profiler.Profiler;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

@Mixin(RecipeManager.class)
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
    private void item_obliterator$apply(Map<Identifier, JsonElement> map, ResourceManager resourceManager, Profiler profiler, CallbackInfo ci) {
        Map<Identifier, JsonElement> filteredMap = new HashMap<>();
    
        for (Map.Entry<Identifier, JsonElement> entry : map.entrySet()) {
            Identifier identifier = entry.getKey();
            JsonElement jsonElement = entry.getValue();
    
            try {
                JsonObject jsonObject = JsonHelper.asObject(jsonElement, "top element");
                JsonElement resultElement = jsonObject.get("result");
    
                if (resultElement == null) {
                    if (jsonObject.get("output") != null) {
                        resultElement = jsonObject.get("output");
                    } else {
                        filteredMap.put(identifier, jsonElement);
                        continue;
                    }
                }
    
                boolean shouldDisable = false;
    
                if (resultElement.isJsonObject()) {
                    JsonObject resultObject = resultElement.getAsJsonObject();
                    String itemId = getResultItemId(resultObject);
    
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
                            String itemId = getResultItemId(resultObject);
    
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
                    filteredMap.put(identifier, jsonElement);
                }
            } catch (IllegalArgumentException | JsonParseException e) {
                ItemObliterator.LOGGER.debug("Parsing error loading recipe {}", identifier, e);
                filteredMap.put(identifier, jsonElement);
            }
        }
    
        map.clear();
        map.putAll(filteredMap);
    }
    
    private String getResultItemId(JsonObject resultObject) {
        if (resultObject.has("item")) {
            return JsonHelper.getString(resultObject, "item");
        } else if (resultObject.has("id")) {
            return JsonHelper.getString(resultObject, "id");
        } else if (resultObject.has("result")) {
            return JsonHelper.getString(resultObject, "result");
        } else if (resultObject.has("output")) {
            return JsonHelper.getString(resultObject, "output");
        }
        
        return null;
    }
    
}