package ru.zenyx.pvp.module;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import org.lwjgl.glfw.GLFW;
import ru.zenyx.pvp.ZenyxPvp;
import ru.zenyx.pvp.module.combat.AutoClicker;
import ru.zenyx.pvp.module.combat.Hitbox;
import ru.zenyx.pvp.module.combat.KillAura;
import ru.zenyx.pvp.module.combat.Reach;
import ru.zenyx.pvp.module.hud.ArmorHud;
import ru.zenyx.pvp.module.hud.BpsHud;
import ru.zenyx.pvp.module.hud.CompassHud;
import ru.zenyx.pvp.module.hud.CoordsHud;
import ru.zenyx.pvp.module.hud.CpsHud;
import ru.zenyx.pvp.module.hud.FpsHud;
import ru.zenyx.pvp.module.hud.KeystrokesHud;
import ru.zenyx.pvp.module.movement.AntiKnockback;
import ru.zenyx.pvp.module.movement.FastPlace;
import ru.zenyx.pvp.module.movement.Fly;
import ru.zenyx.pvp.module.movement.NoFall;
import ru.zenyx.pvp.module.movement.NoSlow;
import ru.zenyx.pvp.module.movement.Scaffold;
import ru.zenyx.pvp.module.movement.Speed;
import ru.zenyx.pvp.module.movement.Sprint;
import ru.zenyx.pvp.module.movement.Tower;
import ru.zenyx.pvp.module.player.AutoTotem;
import ru.zenyx.pvp.module.player.ChestStealer;
import ru.zenyx.pvp.module.player.NoWeather;
import ru.zenyx.pvp.module.render.BlockEsp;
import ru.zenyx.pvp.module.render.Crosshair;
import ru.zenyx.pvp.module.render.Esp;
import ru.zenyx.pvp.module.render.Fullbright;
import ru.zenyx.pvp.module.render.JumpEffect;
import ru.zenyx.pvp.module.render.NameTags;
import ru.zenyx.pvp.module.render.NoHurtCam;
import ru.zenyx.pvp.module.render.StorageEsp;
import ru.zenyx.pvp.module.render.Tracers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Owns the lifecycle of every {@link Module}: registration, ticking and routing
 * HUD/world render events.
 */
public class ModuleManager {
    public static final ModuleManager INSTANCE = new ModuleManager();

    private static final int CLICK_GUI_KEY = GLFW.GLFW_KEY_RIGHT_SHIFT;

    private final List<Module> modules = new ArrayList<>();
    private final Map<Category, List<Module>> byCategory = new EnumMap<>(Category.class);

    public void init() {
        for (Category c : Category.values()) {
            byCategory.put(c, new ArrayList<>());
        }
        register(new KillAura());
        register(new Reach());
        register(new Hitbox());
        register(new AutoClicker());

        register(new Scaffold());
        register(new Tower());
        register(new NoFall());
        register(new AntiKnockback());
        register(new Sprint());
        register(new Speed());
        register(new Fly());
        register(new NoSlow());
        register(new FastPlace());

        register(new Esp());
        register(new Tracers());
        register(new BlockEsp());
        register(new StorageEsp());
        register(new NameTags());
        register(new Fullbright());
        register(new NoHurtCam());
        register(new Crosshair());
        register(new JumpEffect());

        register(new AutoTotem());
        register(new ChestStealer());
        register(new NoWeather());

        register(new FpsHud());
        register(new BpsHud());
        register(new CoordsHud());
        register(new KeystrokesHud());
        register(new CpsHud());
        register(new ArmorHud());
        register(new CompassHud());

        modules.forEach(Module::onInit);
    }

    private void register(Module m) {
        modules.add(m);
        byCategory.get(m.getCategory()).add(m);
    }

    public void onTick() {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null) return;

        for (Module m : modules) {
            if (m.isEnabled()) m.onTick();
        }
    }

    public void onHudRender(DrawContext ctx) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null) return;
        // HUD modules, sorted top-to-bottom for predictable overlap.
        modules.stream()
                .filter(Module::isEnabled)
                .filter(m -> m.getCategory() == Category.HUD)
                .sorted(Comparator.comparingInt(m -> m.getRawName().hashCode()))
                .forEach(m -> m.onHudRender(ctx));
    }

    public void onWorldRender(WorldRenderContext ctx) {
        for (Module m : modules) {
            if (m.isEnabled()) m.onWorldRender(ctx);
        }
    }

    public List<Module> getModules() {
        return modules;
    }

    public List<Module> getModulesIn(Category category) {
        return byCategory.get(category);
    }

    @SuppressWarnings("unchecked")
    public <T extends Module> T get(Class<T> clazz) {
        for (Module m : modules) {
            if (clazz.isInstance(m)) return (T) m;
        }
        return null;
    }

    public boolean isClickGuiKeyPressed() {
        long handle = MinecraftClient.getInstance().getWindow().getHandle();
        return GLFW.glfwGetKey(handle, CLICK_GUI_KEY) == GLFW.GLFW_PRESS;
    }
}
