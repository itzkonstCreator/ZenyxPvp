package ru.zenyx.pvp.mixin.client;

import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.At;
import ru.zenyx.pvp.module.render.NoHurtCam;

/**
 * Removes the camera tilt that normally happens when the player is hurt.
 */
@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @ModifyVariable(method = "tiltViewWhenHurt", at = @At("HEAD"), index = 0, argsOnly = true)
    private float cancelHurtTilt(float value) {
        var hurt = ru.zenyx.pvp.module.ModuleManager.INSTANCE.get(NoHurtCam.class);
        return hurt != null && hurt.isEnabled() ? 0.0f : value;
    }
}
