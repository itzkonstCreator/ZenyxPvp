package ru.zenyx.pvp.renderer;

import net.minecraft.client.gui.DrawContext;

/**
 * Lightweight 2D drawing facade used by HUD modules. Backed by Minecraft's
 * {@link DrawContext} so we get baked-in text rendering, item icons and
 * rectangular fills.
 */
public final class Renderer2D {
    private Renderer2D() {}

    public static void rect(DrawContext ctx, int x, int y, int w, int h, int color) {
        ctx.fill(x, y, x + w, y + h, color);
    }

    public static void outline(DrawContext ctx, int x, int y, int w, int h, int color) {
        ctx.fill(x, y, x + w, y + 1, color);
        ctx.fill(x, y + h - 1, x + w, y + h, color);
        ctx.fill(x, y, x + 1, y + h, color);
        ctx.fill(x + w - 1, y, x + w, y + h, color);
    }

    public static void text(DrawContext ctx, String text, int x, int y, int color) {
        ctx.drawText(net.minecraft.client.MinecraftClient.getInstance().textRenderer, text, x, y, color, true);
    }

    public static int rgba(int r, int g, int b, int a) {
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    public static int withAlpha(int color, int a) {
        return (a << 24) | (color & 0x00FFFFFF);
    }
}