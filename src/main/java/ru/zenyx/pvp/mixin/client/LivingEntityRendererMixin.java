package ru.zenyx.pvp.mixin.client;

import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.zenyx.pvp.module.combat.Hitbox;

/**
 * Expands the hitbox used for entity rendering and raycasting when {@link Hitbox}
 * is enabled. Vanilla bounds are multiplied by 1 + expansion.
 */
@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {
    @ModifyVariable(method = "render", at = @At("HEAD"), argsOnly = true, index = 5)
    private float scaleBBox(float width) {
        var hitbox = ru.zenyx.pvp.module.ModuleManager.INSTANCE.get(Hitbox.class);
        if (hitbox != null && hitbox.isEnabled()) {
            return width + Hitbox.expansion.getValue();
        }
        return width;
    }
}
