package ru.zenyx.pvp.gui;

import net.minecraft.client.gui.DrawContext;
import ru.zenyx.pvp.module.Category;
import ru.zenyx.pvp.module.Module;
import ru.zenyx.pvp.renderer.Renderer2D;

import java.util.List;

/**
 * A draggable window that hosts the modules for a single {@link Category}.
 * Click-and-drag the title bar to move; click a module to toggle.
 */
public class CategoryFrame {
    private final Category category;
    private final List<Module> modules;
    private int x, y;
    private final int width;
    private int height;
    private final int headerHeight;
    private boolean dragging;
    private int dragOffsetX, dragOffsetY;
    private boolean expanded = true;

    public CategoryFrame(Category category, List<Module> modules, int x, int y, int width, int headerHeight) {
        this.category = category;
        this.modules = modules;
        this.x = x;
        this.y = y;
        this.width = width;
        this.headerHeight = headerHeight;
        updateHeight();
    }

    private void updateHeight() {
        height = headerHeight + 2 + (expanded ? modules.size() * 14 + 4 : 0);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void render(DrawContext ctx, int mouseX, int mouseY) {
        // Window body
        Renderer2D.rect(ctx, x, y, width, height, 0xBB1A1F26);
        Renderer2D.outline(ctx, x, y, width, height, 0xFF78FAFF);
        // Title bar
        Renderer2D.rect(ctx, x, y, width, headerHeight, 0xFF78FAFF);
        Renderer2D.text(ctx, category.getDisplayName(), x + 4, y + 4, 0xFF101010);

        if (expanded) {
            int rowY = y + headerHeight + 2;
            for (Module module : modules) {
                int color = module.isEnabled() ? 0xFF78FAFF : 0xFF222831;
                Renderer2D.rect(ctx, x + 2, rowY, width - 4, 12, color);
                Renderer2D.text(ctx, module.getRawName(), x + 6, rowY + 2,
                        module.isEnabled() ? 0xFF101010 : 0xFFE0E0E0);
                rowY += 14;
            }
        }
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + headerHeight) {
                dragging = true;
                dragOffsetX = (int) (mouseX - x);
                dragOffsetY = (int) (mouseY - y);
                return true;
            }
            // Module toggles
            if (expanded && mouseX >= x + 2 && mouseX <= x + width - 2
                    && mouseY > y + headerHeight && mouseY < y + height) {
                int row = (int) ((mouseY - (y + headerHeight + 2)) / 14);
                if (row >= 0 && row < modules.size()) {
                    modules.get(row).toggle();
                    return true;
                }
            }
        }
        if (button == 1 && mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + headerHeight) {
            expanded = !expanded;
            updateHeight();
            return true;
        }
        return false;
    }

    public void mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0) dragging = false;
    }

    public void mouseDragged(double mouseX, double mouseY, int button) {
        if (dragging && button == 0) {
            x = (int) (mouseX - dragOffsetX);
            y = (int) (mouseY - dragOffsetY);
        }
    }
}