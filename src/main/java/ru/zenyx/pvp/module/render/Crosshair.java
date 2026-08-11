package ru.zenyx.pvp.module.render;

import net.minecraft.client.gui.DrawContext;
import ru.zenyx.pvp.module.Category;
import ru.zenyx.pvp.module.Module;
import ru.zenyx.pvp.renderer.Renderer2D;
import ru.zenyx.pvp.settings.ColorSetting;
import ru.zenyx.pvp.settings.ModeSetting;

public class Crosshair extends Module {
    public final ModeSetting shape = register(new ModeSetting("Shape", "Crosshair style", "Cross", "Cross", "Dot", "Circle"));
    public final ColorSetting color = register(new ColorSetting("Color", "Crosshair color", 0xFFFFFFFF));

    public Crosshair() {
        super("Crosshair", "Custom crosshair overlay", Category.RENDER);
    }

    @Override
    public void onHudRender(DrawContext ctx) {
        if (mc.player == null) return;
        int cx = mc.getWindow().getScaledWidth() / 2;
        int cy = mc.getWindow().getScaledHeight() / 2;
        int color = this.color.getValue();
        switch (shape.getValue()) {
            case "Dot" -> Renderer2D.rect(ctx, cx - 1, cy - 1, 2, 2, color);
            case "Circle" -> {
                for (int i = 0; i < 16; i++) {
                    double angle = 2 * Math.PI * i / 16;
                    int px = (int) (cx + Math.cos(angle) * 5);
                    int py = (int) (cy + Math.sin(angle) * 5);
                    Renderer2D.rect(ctx, px, py, 1, 1, color);
                }
            }
            default -> {
                Renderer2D.rect(ctx, cx - 8, cy - 1, 16, 2, color);
                Renderer2D.rect(ctx, cx - 1, cy - 8, 2, 16, color);
            }
        }
    }
}