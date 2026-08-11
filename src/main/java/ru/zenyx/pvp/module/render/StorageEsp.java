package ru.zenyx.pvp.module.render;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import ru.zenyx.pvp.module.Category;
import ru.zenyx.pvp.module.Module;
import ru.zenyx.pvp.renderer.Renderer3D;
import ru.zenyx.pvp.settings.ColorSetting;
import ru.zenyx.pvp.util.PlayerUtils;

import java.awt.Color;

public class StorageEsp extends Module {
    public final ColorSetting color = register(new ColorSetting("Color", "Storage block color", 0xFFFFC65C));

    public StorageEsp() {
        super("StorageEsp", "Highlights chests, barrels, hoppers", Category.RENDER);
    }

    @Override
    public void onWorldRender(WorldRenderContext ctx) {
        if (mc.player == null || mc.world == null) return;
        Color c = new Color(color.getValue(), true);
        int radius = 32;
        BlockPos.iterateOutwards(PlayerUtils.feet(), radius, radius, radius).forEach(pos -> {
            var state = mc.world.getBlockState(pos);
            if (state.isOf(Blocks.CHEST) || state.isOf(Blocks.TRAPPED_CHEST)
                    || state.isOf(Blocks.BARREL) || state.isOf(Blocks.HOPPER)
                    || state.isOf(Blocks.DISPENSER) || state.isOf(Blocks.DROPPER)
                    || state.isOf(Blocks.SHULKER_BOX)) {
                Renderer3D.drawBox(ctx,
                        new Vec3d(pos.getX(), pos.getY(), pos.getZ()),
                        new Vec3d(pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1),
                        c, 2.0f);
            }
        });
    }
}