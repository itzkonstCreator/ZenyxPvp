package ru.zenyx.pvp.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import ru.zenyx.pvp.module.Category;
import ru.zenyx.pvp.module.Module;
import ru.zenyx.pvp.module.ModuleManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Vanilla-style click GUI: one draggable window per category on the screen,
 * with toggleable modules listed vertically.
 */
public class ZenyxClickGui extends Screen {
    private final ModuleManager moduleManager;
    private final List<CategoryFrame> frames = new ArrayList<>();

    protected ZenyxClickGui(ModuleManager moduleManager) {
        super(Text.literal("Zenyx ClickGui"));
        this.moduleManager = moduleManager;
    }

    @Override
    protected void init() {
        frames.clear();
        int x = 30;
        int y = 30;
        for (Category category : Category.values()) {
            CategoryFrame frame = new CategoryFrame(category, moduleManager.getModulesIn(category), x, y, 130, 16);
            frames.add(frame);
            x += frame.getWidth() + 16;
            if (x + frame.getWidth() > this.width) {
                x = 30;
                y += 200;
            }
        }
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        // Translucent background so the world is still visible behind the GUI.
        ctx.fill(0, 0, this.width, this.height, 0x55000000);
        for (CategoryFrame frame : frames) {
            frame.render(ctx, mouseX, mouseY);
        }
        super.render(ctx, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (CategoryFrame frame : frames) {
            if (frame.mouseClicked(mouseX, mouseY, button)) return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        for (CategoryFrame frame : frames) {
            frame.mouseReleased(mouseX, mouseY, button);
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dx, double dy) {
        for (CategoryFrame frame : frames) {
            frame.mouseDragged(mouseX, mouseY, button);
        }
        return super.mouseDragged(mouseX, mouseY, button, dx, dy);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
