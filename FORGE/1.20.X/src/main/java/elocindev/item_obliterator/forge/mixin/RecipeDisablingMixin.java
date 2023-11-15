package elocindev.item_obliterator.forge.mixin;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import elocindev.item_obliterator.forge.utils.Utils;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

@Mixin(value = RecipeManager.class, priority = 10000)
public class RecipeDisablingMixin {
    @Inject(at = @At(value = "RETURN"), method = "getRecipeFor(Lnet/minecraft/world/item/crafting/RecipeType;Lnet/minecraft/world/Container;Lnet/minecraft/world/level/Level;)Ljava/util/Optional;", cancellable = true)
    private <C extends Container, T extends Recipe<C>> void item_obliterator$getRecipeForRecipeType(RecipeType<T> recipe, C inventory, Level level, CallbackInfoReturnable<Optional<T>> cir) {
        cir.getReturnValue().ifPresent(value ->
                cir.setReturnValue(Utils.shouldRecipeBeDisabled(value.getResultItem(null).getItem()) ? Optional.empty() : Optional.of(value)));
    }
    
    @Inject(at = @At(value = "RETURN"), method = "getRecipesFor", cancellable = true)
    private <C extends Container, T extends Recipe<C>> void item_obliterator$getRecipesFor(RecipeType<T> recipe, C inventory, Level level, CallbackInfoReturnable<List<T>> cir) {
        cir.setReturnValue(cir.getReturnValue().stream()
                .filter(entry -> !Utils.shouldRecipeBeDisabled(entry.assemble(inventory, null).getItem()))
                .collect(Collectors.toList()));
    }
}