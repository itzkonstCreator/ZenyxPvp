package ru.zenyx.pvp.module.hud;

import net.minecraft.client.gui.DrawContext;
import org.lwjgl.glfw.GLFW;
import ru.zenyx.pvp.module.Category;
import ru.zenyx.pvp.module.Module;
import ru.zenyx.pvp.renderer.Renderer2D;

import java.awt.Color;

/**
 * Renders a small WASD + mouse-keys overlay in the bottom-left corner. The
 * per-key background turns the foreground colour when the corresponding key is
 * held. Shift + LMB drag will be handled by the ClickGUI editor.
 */
public class KeystrokesHud extends Module {
    public KeystrokesHud() {
        super("Keystrokes", "Shows held movement keys", Category.HUD);
    }

    @Override
    public void onHudRender(DrawContext ctx) {
        long handle = mc.getWindow().getHandle();
        int x = 4;
        int y = mc.getWindow().getScaledHeight() - 60;
        int size = 18;
        drawKey(ctx, x, y, "W", GLFW.glfwGetKey(handle, GLFW.GLFW_KEY_W) == GLFW.GLFW_PRESS);
        drawKey(ctx, x - size - 2, y + size + 2, "A", GLFW.glfwGetKey(handle, GLFW.GLFW_KEY_A) == GLFW.GLFW_PRESS);
        drawKey(ctx, x, y + size + 2, "S", GLFW.glfwGetKey(handle, GLFW.GLFW_KEY_S) == GLFW.GLFW_PRESS);
        drawKey(ctx, x + size + 2, y + size + 2, "D", GLFW.glfwGetKey(handle, GLFW.GLFW_KEY_D) == GLFW.GLFW_PRESS);
        drawKey(ctx, x + (size + 2) * 2, y, "LMB", GLFW.glfwGetMouseButton(handle, GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS);
        drawKey(ctx, x + (size + 2) * 3, y, "RMB", GLFW.glfwGetMouseButton(handle, GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS);
    }

    private void drawKey(DrawContext ctx, int x, int y, String label, boolean pressed) {
        Renderer2D.rect(ctx, x, y, 18, 18, pressed ? 0xAA78FAFF : 0x55000000);
        int tx = x + 4;
        int ty = y + 5;
        Renderer2D.text(ctx, label, tx, ty, pressed ? 0xFFFFFFFF : 0xFFAAAAAA);
    }
}