package ru.zenyx.pvp.module.hud;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.MathHelper;
import ru.zenyx.pvp.module.Category;
import ru.zenyx.pvp.module.Module;
import ru.zenyx.pvp.renderer.Renderer2D;

public class CompassHud extends Module {
    public CompassHud() {
        super("Compass", "Cardinal direction at top of screen", Category.HUD);
    }

    @Override
    public void onHudRender(DrawContext ctx) {
        if (mc.player == null) return;
        int width = mc.getWindow().getScaledWidth();
        float yaw = MathHelper.wrapDegrees(mc.player.getYaw());
        Renderer2D.text(ctx, String.format("Facing %.0f°", yaw), width / 2 - 30, 4, 0xFFFFFFFF);
    }
}