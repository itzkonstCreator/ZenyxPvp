package ru.zenyx.pvp.mixin.client;

import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.DrawContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.zenyx.pvp.ZenyxPvp;

/**
 * Renders the entire HUD module suite after the vanilla HUD elements. (We use
 * the post-hud layer so overlays sit on top of the hotbar / health bar.)
 */
@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Inject(method = "render", at = @At("TAIL"))
    private void renderHudOverlay(DrawContext ctx, net.minecraft.client.render.RenderTickCounter tickCounter, CallbackInfo ci) {
        ZenyxPvp.getInstance().getModuleManager().onHudRender(ctx);
    }
}
