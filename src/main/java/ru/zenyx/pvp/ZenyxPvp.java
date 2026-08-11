package ru.zenyx.pvp;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zenyx.pvp.config.ConfigManager;
import ru.zenyx.pvp.gui.ZenyxClickGui;
import ru.zenyx.pvp.module.ModuleManager;

/**
 * Main entry point for the Zenyx PvP client.
 *
 * <p>The {@code onInitializeClient} method boots the module manager, loads the
 * persisted configuration and registers the per-frame tick + render hooks that
 * drive every active module.</p>
 */
public final class ZenyxPvp implements ClientModInitializer {
    public static final String MOD_ID = "zenyx_pvp";
    public static final Logger LOGGER = LoggerFactory.getLogger("ZenyxPvp");

    private static ZenyxPvp instance;

    private ModuleManager moduleManager;
    private ConfigManager configManager;
    private ZenyxClickGui clickGui;

    @Override
    public void onInitializeClient() {
        instance = this;

        LOGGER.info("[Zenyx] Booting Zenyx PvP client");

        this.moduleManager = ModuleManager.INSTANCE;
        this.configManager = new ConfigManager(moduleManager);
        this.clickGui = new ZenyxClickGui(moduleManager);

        this.moduleManager.init();
        this.configManager.load();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            // Right Shift opens the in-game ClickGUI. Pausing on the Gui screen
            // is required so vanilla doesn't close it instantly.
            if (ModuleManager.INSTANCE.isClickGuiKeyPressed()) {
                client.setScreen(clickGui);
            }

            // Process module keybinds (toggle on rising edge).
            long handle = client.getWindow().getHandle();
            for (var module : moduleManager.getModules()) {
                int key = module.getKeybind();
                if (key == org.lwjgl.glfw.GLFW.GLFW_KEY_UNKNOWN) continue;
                boolean pressed = org.lwjgl.glfw.GLFW.glfwGetKey(handle, key) == org.lwjgl.glfw.GLFW.GLFW_PRESS;
                if (pressed && !module.wasKeybindDown()) {
                    module.toggle();
                }
                module.setKeybindDown(pressed);
            }

            moduleManager.onTick();
        });

        // Persist config on game shutdown.
        net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents.CLIENT_STOPPING.register(client -> configManager.save());

        HudRenderCallback.EVENT.register((ctx, renderTickCounter) -> moduleManager.onHudRender(ctx));

        WorldRenderEvents.LAST.register(ctx -> {
            MinecraftClient mc = MinecraftClient.getInstance();
            if (mc.player == null || mc.world == null) return;
            moduleManager.onWorldRender(ctx);
        });
    }

    public static ZenyxPvp getInstance() {
        return instance;
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}
