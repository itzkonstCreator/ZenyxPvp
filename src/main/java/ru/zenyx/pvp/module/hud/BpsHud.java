package ru.zenyx.pvp.module.hud;

import net.minecraft.client.gui.DrawContext;
import ru.zenyx.pvp.module.Category;
import ru.zenyx.pvp.module.Module;
import ru.zenyx.pvp.renderer.Renderer2D;

public class BpsHud extends Module {
    private double lastX, lastZ;
    private double bps = 0.0;

    public BpsHud() {
        super("Bps", "Shows blocks-per-second", Category.HUD);
    }

    @Override
    public void onHudRender(DrawContext ctx) {
        if (mc.player == null) return;
        double x = mc.player.getX(), z = mc.player.getZ();
        if (lastX != 0) {
            bps = 20.0 * Math.sqrt((x - lastX) * (x - lastX) + (z - lastZ) * (z - lastZ));
        }
        lastX = x;
        lastZ = z;
        Renderer2D.text(ctx, String.format("BPS %.1f", bps), 4, 14, 0xFFFFFFFF);
    }
}