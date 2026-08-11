package ru.zenyx.pvp.module.hud;

import net.minecraft.client.gui.DrawContext;
import ru.zenyx.pvp.module.Category;
import ru.zenyx.pvp.module.Module;
import ru.zenyx.pvp.renderer.Renderer2D;

public class FpsHud extends Module {
    private long lastTime = System.nanoTime();
    private int fps = 0;
    private int frames = 0;

    public FpsHud() {
        super("Fps", "Shows FPS in top-left", Category.HUD);
    }

    @Override
    public void onHudRender(DrawContext ctx) {
        frames++;
        long now = System.nanoTime();
        if (now - lastTime >= 1_000_000_000L) {
            fps = frames;
            frames = 0;
            lastTime = now;
        }
        Renderer2D.text(ctx, "FPS " + fps, 4, 4, 0xFFFFFFFF);
    }
}