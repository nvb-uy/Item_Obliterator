package elocindev.item_obliterator.fabric_quilt.mixin;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import elocindev.item_obliterator.fabric_quilt.util.Utils;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.world.World;

@Mixin(RecipeManager.class)
public class RecipeDisablingMixin {
    @Inject(at = @At(value = "RETURN"), method = "getFirstMatch(Lnet/minecraft/recipe/RecipeType;Lnet/minecraft/inventory/Inventory;Lnet/minecraft/world/World;)Ljava/util/Optional;", cancellable = true)
    private <I extends Inventory, T extends Recipe<I>> void item_obliterator$getRecipeForRecipeType(RecipeType<T> recipe, I inventory, World world, CallbackInfoReturnable<Optional<T>> cir) {
        cir.getReturnValue().ifPresent(value ->
                cir.setReturnValue(Utils.shouldRecipeBeDisabled(value.getOutput(null).getItem()) ? Optional.empty() : Optional.of(value)));
    }
    
    @Inject(at = @At(value = "RETURN"), method = "getAllMatches", cancellable = true)
    private <I extends Inventory, T extends Recipe<I>> void item_obliterator$getRecipesFor(RecipeType<T> recipe, I inventory, World world, CallbackInfoReturnable<List<T>> cir) {
        cir.setReturnValue(cir.getReturnValue().stream()
                .filter(entry -> Utils.shouldRecipeBeDisabled(entry.craft(inventory, null).getItem()))
                .collect(Collectors.toList()));
    }
}