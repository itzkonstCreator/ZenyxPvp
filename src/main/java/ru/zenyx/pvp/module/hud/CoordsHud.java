package ru.zenyx.pvp.module.hud;

import net.minecraft.client.gui.DrawContext;
import ru.zenyx.pvp.module.Category;
import ru.zenyx.pvp.module.Module;
import ru.zenyx.pvp.renderer.Renderer2D;

public class CoordsHud extends Module {
    public CoordsHud() {
        super("Coords", "Displays XYZ", Category.HUD);
    }

    @Override
    public void onHudRender(DrawContext ctx) {
        if (mc.player == null) return;
        String text = String.format("XYZ %d %d %d",
                (int) mc.player.getX(), (int) mc.player.getY(), (int) mc.player.getZ());
        Renderer2D.text(ctx, text, 4, 24, 0xFFFFFFFF);
    }
}