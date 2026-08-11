package ru.zenyx.pvp.module.render;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import ru.zenyx.pvp.module.Category;
import ru.zenyx.pvp.module.Module;
import ru.zenyx.pvp.renderer.Renderer3D;
import ru.zenyx.pvp.settings.ColorSetting;
import ru.zenyx.pvp.settings.NumberSetting;
import ru.zenyx.pvp.util.PlayerUtils;

import java.awt.Color;

public class Tracers extends Module {
    public final NumberSetting lineWidth = register(new NumberSetting("LineWidth", "Tracer thickness", 1.5f, 1.0f, 5.0f, 0.5f));
    public final ColorSetting color = register(new ColorSetting("Color", "Tracer color", 0xFFFF7676));

    public Tracers() {
        super("Tracers", "Draws lines to players", Category.RENDER);
    }

    @Override
    public void onWorldRender(WorldRenderContext ctx) {
        if (mc.player == null) return;
        Vec3d camera = ctx.camera().getPos();
        // Origin roughly at the camera, then offset to eye level
        Vec3d origin = new Vec3d(camera.x, camera.y, camera.z);
        Color color = new Color(this.color.getValue(), true);
        for (PlayerEntity p : PlayerUtils.playersInRange(96)) {
            Vec3d target = p.getPos().add(0, p.getHeight() / 2, 0);
            Renderer3D.drawTracer(ctx, origin, target, color, lineWidth.getValue());
        }
    }
}