package ru.zenyx.pvp.module.combat;

import net.minecraft.entity.Entity;
import net.minecraft.util.Hand;
import ru.zenyx.pvp.module.Category;
import ru.zenyx.pvp.module.Module;
import ru.zenyx.pvp.settings.NumberSetting;

/**
 * Increases the effective reach of attack packets. Implemented via a
 * {@link ru.zenyx.pvp.mixin.client.MinecraftClientMixin} that swaps the value
 * when this module is enabled.
 */
public class Reach extends Module {
    public static final NumberSetting distance = new NumberSetting("Distance", "Extra blocks added to reach", 1.5f, 0.5f, 3.0f, 0.5f);

    public Reach() {
        super("Reach", "Adds extra reach", Category.COMBAT);
        register(distance);
    }

    /** Allows other modules to read the live reach delta without an instance reference. */
    public static float extraReach() {
        return distance.getValue();
    }
}