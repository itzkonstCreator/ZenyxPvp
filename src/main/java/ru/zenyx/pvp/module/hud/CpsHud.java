package ru.zenyx.pvp.module.hud;

import net.minecraft.client.gui.DrawContext;
import org.lwjgl.glfw.GLFW;
import ru.zenyx.pvp.module.Category;
import ru.zenyx.pvp.module.Module;
import ru.zenyx.pvp.renderer.Renderer2D;

import java.util.ArrayDeque;
import java.util.Deque;

public class CpsHud extends Module {
    private final Deque<Long> leftClicks = new ArrayDeque<>();
    private final Deque<Long> rightClicks = new ArrayDeque<>();

    public CpsHud() {
        super("Cps", "Shows clicks-per-second", Category.HUD);
    }

    @Override
    public void onHudRender(DrawContext ctx) {
        long handle = mc.getWindow().getHandle();
        long now = System.currentTimeMillis();
        if (GLFW.glfwGetMouseButton(handle, GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS) leftClicks.push(now);
        if (GLFW.glfwGetMouseButton(handle, GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS) rightClicks.push(now);
        while (!leftClicks.isEmpty() && now - leftClicks.peekLast() > 1000) leftClicks.pollLast();
        while (!rightClicks.isEmpty() && now - rightClicks.peekLast() > 1000) rightClicks.pollLast();
        Renderer2D.text(ctx, "LMB " + leftClicks.size(), 4, 34, 0xFFFFFFFF);
        Renderer2D.text(ctx, "RMB " + rightClicks.size(), 4, 44, 0xFFFFFFFF);
    }
}