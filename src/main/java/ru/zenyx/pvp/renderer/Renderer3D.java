package ru.zenyx.pvp.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

import java.awt.Color;

/**
 * Draws 3D overlays (ESP boxes, tracers) using a fresh {@link BufferBuilder}
 * per call. Hooked via {@code WorldRenderEvents.LAST} so the world geometry has
 * already been emitted.
 */
public final class Renderer3D {
    private Renderer3D() {}

    private static final Vec3d ONE = new Vec3d(1, 1, 1);

    public static void drawBox(WorldRenderContext ctx, Vec3d min, Vec3d max, Color color, float lineWidth) {
        Vec3d cam = ctx.camera().getPos();
        MatrixStack matrices = ctx.matrixStack();
        matrices.push();
        matrices.translate(-cam.x, -cam.y, -cam.z);

        Vec3d[] corners = new Vec3d[] {
                new Vec3d(min.x, min.y, min.z),
                new Vec3d(max.x, min.y, min.z),
                new Vec3d(max.x, min.y, max.z),
                new Vec3d(min.x, min.y, max.z),
                new Vec3d(min.x, max.y, min.z),
                new Vec3d(max.x, max.y, min.z),
                new Vec3d(max.x, max.y, max.z),
                new Vec3d(min.x, max.y, max.z),
        };

        // Bottom rectangle
        line(matrices, corners[0], corners[1], color, lineWidth);
        line(matrices, corners[1], corners[2], color, lineWidth);
        line(matrices, corners[2], corners[3], color, lineWidth);
        line(matrices, corners[3], corners[0], color, lineWidth);
        // Top rectangle
        line(matrices, corners[4], corners[5], color, lineWidth);
        line(matrices, corners[5], corners[6], color, lineWidth);
        line(matrices, corners[6], corners[7], color, lineWidth);
        line(matrices, corners[7], corners[4], color, lineWidth);
        // Verticals
        line(matrices, corners[0], corners[4], color, lineWidth);
        line(matrices, corners[1], corners[5], color, lineWidth);
        line(matrices, corners[2], corners[6], color, lineWidth);
        line(matrices, corners[3], corners[7], color, lineWidth);

        matrices.pop();
    }

    public static void drawTracer(WorldRenderContext ctx, Vec3d from, Vec3d to, Color color, float lineWidth) {
        Vec3d cam = ctx.camera().getPos();
        MatrixStack matrices = ctx.matrixStack();
        matrices.push();
        matrices.translate(-cam.x, -cam.y, -cam.z);
        line(matrices, from, to, color, lineWidth);
        matrices.pop();
    }

    private static void line(MatrixStack matrices, Vec3d a, Vec3d b, Color color, float lineWidth) {
        RenderSystem.lineWidth(lineWidth);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);

        Matrix4f matrix = matrices.peek().getPositionMatrix();

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormat.POSITION_COLOR);
        buffer.vertex(matrix, (float) a.x, (float) a.y, (float) a.z).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        buffer.vertex(matrix, (float) b.x, (float) b.y, (float) b.z).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        BufferRenderer.drawWithGlobalProgram(buffer.end());

        RenderSystem.enableCull();
        RenderSystem.disableBlend();
    }
}