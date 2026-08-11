package ru.zenyx.pvp.module.render;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import ru.zenyx.pvp.module.Category;
import ru.zenyx.pvp.module.Module;
import ru.zenyx.pvp.renderer.Renderer3D;
import ru.zenyx.pvp.settings.ColorSetting;
import ru.zenyx.pvp.settings.NumberSetting;
import ru.zenyx.pvp.util.PlayerUtils;

import java.awt.Color;

public class BlockEsp extends Module {
    public final NumberSetting distance = register(new NumberSetting("Distance", "Search radius", 32.0f, 8.0f, 64.0f, 1.0f));
    public final ColorSetting color = register(new ColorSetting("Color", "Block highlight color", 0xFFC8FF7A));

    public BlockEsp() {
        super("BlockEsp", "Highlights diamond/emerald ore", Category.RENDER);
    }

    @Override
    public void onWorldRender(WorldRenderContext ctx) {
        if (mc.player == null || mc.world == null) return;
        int radius = Math.round(distance.getValue());
        Color c = new Color(color.getValue(), true);
        BlockPos.iterateOutwards(PlayerUtils.feet(), radius, radius, radius).forEach(pos -> {
            var state = mc.world.getBlockState(pos);
            if (state.isOf(Blocks.DIAMOND_ORE) || state.isOf(Blocks.DEEPSLATE_DIAMOND_ORE)
                    || state.isOf(Blocks.EMERALD_ORE) || state.isOf(Blocks.DEEPSLATE_EMERALD_ORE)
                    || state.isOf(Blocks.GOLD_ORE) || state.isOf(Blocks.IRON_ORE)) {
                Renderer3D.drawBox(ctx,
                        new Vec3d(pos.getX(), pos.getY(), pos.getZ()),
                        new Vec3d(pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1),
                        c, 2.0f);
            }
        });
    }
}