package ru.zenyx.pvp.module.render;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import ru.zenyx.pvp.module.Category;
import ru.zenyx.pvp.module.Module;
import ru.zenyx.pvp.util.PlayerUtils;

/**
 * Renders a 3D label above each nearby player during the LAST world render
 * callback. We leverage vanilla's {@code EntityVertexConsumers} so the text is
 * properly depth-tested and lit.
 */
public class NameTags extends Module {
    public NameTags() {
        super("NameTags", "Shows distance above players", Category.RENDER);
    }

    @Override
    public void onWorldRender(WorldRenderContext ctx) {
        if (mc.player == null) return;
        Camera camera = ctx.camera();
        MatrixStack matrices = ctx.matrixStack();
        VertexConsumerProvider.Immediate immediate = mc.worldRenderer.getEntityVertexConsumers();
        TextRenderer textRenderer = mc.textRenderer;

        for (PlayerEntity player : PlayerUtils.playersInRange(64)) {
            if (player == mc.player) continue;
            double distance = Math.round(mc.player.distanceTo(player));
            Text label = Text.literal(player.getName().getString() + " " + distance + "m");

            Vec3d pos = player.getPos().add(0, player.getHeight() + 0.4, 0).subtract(camera.getPos());

            matrices.push();
            matrices.translate(pos.x, pos.y, pos.z);
            matrices.multiply(camera.getRotation());
            matrices.scale(-0.025f, -0.025f, 0.025f);
            float x = -textRenderer.getWidth(label) / 2.0f;
            textRenderer.draw(label, x, 0, 0xFFFFFFFF, false,
                    matrices.peek().getPositionMatrix(),
                    immediate, TextRenderer.TextLayerType.SEE_THROUGH, 0, 0xF000F0);
            matrices.pop();
        }
    }
}
