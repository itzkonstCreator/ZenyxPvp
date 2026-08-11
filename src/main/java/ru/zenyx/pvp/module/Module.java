package ru.zenyx.pvp.module;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import ru.zenyx.pvp.module.ModuleManager;
import ru.zenyx.pvp.settings.Setting;
import ru.zenyx.pvp.settings.KeybindSetting;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for every toggleable feature.
 *
 * <p>Modules are ticked each client tick (when {@link #isEnabled()} is true)
 * and can additionally hook into HUD or world render passes.</p>
 */
public abstract class Module {
    protected static final MinecraftClient mc = MinecraftClient.getInstance();

    private final String name;
    private final String description;
    private final Category category;
    private final List<Setting<?>> settings = new ArrayList<>();

    private boolean enabled;
    private int keybind;
    private boolean keybindDown;

    protected Module(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.keybind = GLFW.GLFW_KEY_UNKNOWN;
    }

    /** Called once after the module is registered. */
    public void onInit() {
        // No-op by default.
    }

    /** Called every client tick while enabled. */
    public void onTick() {
        // No-op by default.
    }

    /** Called every HUD render while enabled. */
    public void onHudRender(net.minecraft.client.gui.DrawContext ctx) {
        // No-op by default.
    }

    /** Called every world render while enabled. */
    public void onWorldRender(net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext ctx) {
        // No-op by default.
    }

    public void onEnable() {
        // Override in subclasses.
    }

    public void onDisable() {
        // Override in subclasses.
    }

    public void toggle() {
        setEnabled(!enabled);
    }

    public void setEnabled(boolean value) {
        if (enabled == value) return;
        enabled = value;
        if (enabled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Text getName() {
        return Text.literal(name);
    }

    public String getRawName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public List<Setting<?>> getSettings() {
        return settings;
    }

    protected void register(Setting<?> setting) {
        settings.add(setting);
    }

    public int getKeybind() {
        return keybind;
    }

    public void setKeybind(int keybind) {
        this.keybind = keybind;
    }

    public boolean wasKeybindDown() { return keybindDown; }
    public void setKeybindDown(boolean down) { this.keybindDown = down; }

    /** Helper to grab the singleton manager from inside a module. */
    protected ModuleManager manager() {
        return ModuleManager.INSTANCE;
    }
}
