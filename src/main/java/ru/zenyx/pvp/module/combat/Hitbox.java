package ru.zenyx.pvp.module.combat;

import ru.zenyx.pvp.module.Category;
import ru.zenyx.pvp.module.Module;
import ru.zenyx.pvp.settings.NumberSetting;

/**
 * Expands the hitbox of nearby players. Hooked via
 * {@link ru.zenyx.pvp.mixin.client.LivingEntityRendererMixin} which reads this
 * module's static field while rendering.
 */
public class Hitbox extends Module {
    public static final NumberSetting expansion = new NumberSetting("Expansion", "Extra half-width per axis (blocks)", 0.2f, 0.0f, 1.0f, 0.05f);

    public Hitbox() {
        super("Hitbox", "Expands player hitboxes", Category.COMBAT);
        register(expansion);
    }

    public static float expansion() {
        return expansion.getValue();
    }
}