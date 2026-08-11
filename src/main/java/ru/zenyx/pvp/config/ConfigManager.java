package ru.zenyx.pvp.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.MinecraftClient;
import ru.zenyx.pvp.ZenyxPvp;
import ru.zenyx.pvp.module.Module;
import ru.zenyx.pvp.module.ModuleManager;
import ru.zenyx.pvp.settings.BooleanSetting;
import ru.zenyx.pvp.settings.ColorSetting;
import ru.zenyx.pvp.settings.KeybindSetting;
import ru.zenyx.pvp.settings.ModeSetting;
import ru.zenyx.pvp.settings.NumberSetting;
import ru.zenyx.pvp.settings.Setting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

/**
 * Persists every module's enabled flag, keybind and per-setting value to
 * {@code config/zenyx_pvp.json} under the Minecraft directory. Hand-rolled JSON
 * keeps the dependency surface to a minimum.
 */
public class ConfigManager {
    private static final String FILE_NAME = "zenyx_pvp.json";

    private final ModuleManager moduleManager;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public ConfigManager(ModuleManager moduleManager) {
        this.moduleManager = moduleManager;
    }

    private Path getConfigPath() {
        return MinecraftClient.getInstance().runDirectory.toPath().resolve("config").resolve(FILE_NAME);
    }

    public void load() {
        Path path = getConfigPath();
        if (!Files.exists(path)) return;
        try {
            String raw = Files.readString(path);
            JsonObject root = JsonParser.parseString(raw).getAsJsonObject();
            for (Module module : moduleManager.getModules()) {
                JsonObject entry = root.has(module.getRawName()) ? root.getAsJsonObject(module.getRawName()) : null;
                if (entry == null) continue;

                if (entry.has("enabled") && !entry.get("enabled").getAsBoolean()) {
                    module.setEnabled(false);
                }
                if (entry.has("keybind")) {
                    module.setKeybind(entry.get("keybind").getAsInt());
                }
                JsonObject settings = entry.has("settings") ? entry.getAsJsonObject("settings") : new JsonObject();
                for (Setting<?> setting : module.getSettings()) {
                    if (!settings.has(setting.getName())) continue;
                    apply(setting, settings.get(setting.getName()));
                }
            }
            ZenyxPvp.LOGGER.info("[Zenyx] Config loaded");
        } catch (IOException ex) {
            ZenyxPvp.LOGGER.warn("[Zenyx] Failed to load config", ex);
        }
    }

    private void apply(Setting<?> setting, com.google.gson.JsonElement element) {
        if (setting instanceof BooleanSetting b) {
            b.setValue(element.getAsBoolean());
        } else if (setting instanceof NumberSetting n) {
            n.setValue(element.getAsFloat());
        } else if (setting instanceof ModeSetting m) {
            m.setValue(element.getAsString());
        } else if (setting instanceof ColorSetting c) {
            c.setValue(element.getAsInt());
        } else if (setting instanceof KeybindSetting k) {
            k.setValue(element.getAsInt());
        }
    }

    public void save() {
        Path path = getConfigPath();
        try {
            Files.createDirectories(path.getParent());
            JsonObject root = new JsonObject();
            for (Module module : moduleManager.getModules()) {
                JsonObject entry = new JsonObject();
                entry.addProperty("enabled", module.isEnabled());
                entry.addProperty("keybind", module.getKeybind());
                JsonObject settings = new JsonObject();
                for (Setting<?> setting : module.getSettings()) {
                    settings.add(setting.getName(), toJson(setting));
                }
                entry.add("settings", settings);
                root.add(module.getRawName(), entry);
            }
            Files.writeString(path, gson.toJson(root));
            ZenyxPvp.LOGGER.info("[Zenyx] Config saved");
        } catch (IOException ex) {
            ZenyxPvp.LOGGER.warn("[Zenyx] Failed to save config", ex);
        }
    }

    private com.google.gson.JsonElement toJson(Setting<?> setting) {
        if (setting instanceof BooleanSetting b) return new com.google.gson.JsonPrimitive(b.getValue());
        if (setting instanceof NumberSetting n) return new com.google.gson.JsonPrimitive(n.getValue());
        if (setting instanceof ModeSetting m) return new com.google.gson.JsonPrimitive(m.getValue());
        if (setting instanceof ColorSetting c) return new com.google.gson.JsonPrimitive(c.getValue());
        if (setting instanceof KeybindSetting k) return new com.google.gson.JsonPrimitive(k.getValue());
        return com.google.gson.JsonNull.INSTANCE;
    }
}