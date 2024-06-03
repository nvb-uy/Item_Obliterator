package elocindev.item_obliterator.fabric_quilt.plugin;

import elocindev.item_obliterator.fabric_quilt.util.Utils;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;

public class FDCompatibility {
    public static void init() {
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (entity instanceof ItemEntity && world instanceof ServerWorld) {
                ItemStack item = ((ItemEntity)entity).getStack();
                
                if (Utils.isDisabled(item)) {
                    entity.discard();
                }
            }
        });
    }
}
