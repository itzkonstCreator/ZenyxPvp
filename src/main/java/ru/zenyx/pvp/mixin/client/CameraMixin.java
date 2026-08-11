package ru.zenyx.pvp.mixin.client;

import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.zenyx.pvp.module.movement.NoSlow;

/**
 * Disables the slowdown applied to the camera when the player uses an item
 * (e.g. drawing a bow). The actual velocity reduction is applied through the
 * mixin on {@code LivingEntity#tickMovement} — we keep the camera-side hook
 * here for future visual tweaks.
 */
@Mixin(Camera.class)
public class CameraMixin {
    @Inject(method = "update", at = @At("TAIL"))
    private void onUpdate(CallbackInfo ci) {
        // NoSlow uses the input mixin instead; left intentionally empty.
    }
}
