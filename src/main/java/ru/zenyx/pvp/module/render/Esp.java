package ru.zenyx.pvp.module.render;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.entity.player.PlayerEntity;
import ru.zenyx.pvp.module.Category;
import ru.zenyx.pvp.module.Module;
import ru.zenyx.pvp.renderer.Renderer3D;
import ru.zenyx.pvp.settings.ColorSetting;
import ru.zenyx.pvp.settings.ModeSetting;
import ru.zenyx.pvp.settings.NumberSetting;
import ru.zenyx.pvp.util.PlayerUtils;

import java.awt.Color;

public class Esp extends Module {
    public final ModeSetting mode = register(new ModeSetting("Mode", "ESP visualisation", "Box", "Box", "2D"));
    public final NumberSetting lineWidth = register(new NumberSetting("LineWidth", "Thickness of outline", 2.0f, 1.0f, 5.0f, 0.5f));
    public final ColorSetting color = register(new ColorSetting("Color", "Outline color", 0xFF78FAFF));

    public Esp() {
        super("Esp", "Highlights player hitboxes", Category.RENDER);
    }

    @Override
    public void onWorldRender(WorldRenderContext ctx) {
        int c = color.getValue();
        Color base = new Color(c, true);
        for (PlayerEntity p : PlayerUtils.playersInRange(64)) {
            double x = p.getX() - p.getWidth() / 2.0;
            double y = p.getY();
            double z = p.getZ() - p.getWidth() / 2.0;
            double w = p.getWidth();
            double h = p.getHeight();
            Renderer3D.drawBox(ctx,
                    new net.minecraft.util.math.Vec3d(x, y, z),
                    new net.minecraft.util.math.Vec3d(x + w, y + h, z + w),
                    base, lineWidth.getValue());
        }
    }
}