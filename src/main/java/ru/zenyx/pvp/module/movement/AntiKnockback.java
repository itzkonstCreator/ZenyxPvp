package ru.zenyx.pvp.module.movement;

import ru.zenyx.pvp.module.Category;
import ru.zenyx.pvp.module.Module;
import ru.zenyx.pvp.settings.NumberSetting;

/** Cancels horizontal knockback by zeroing the velocity contribution. */
public class AntiKnockback extends Module {
    public static final NumberSetting strength = new NumberSetting("Strength", "How much knockback to cancel (0..1)", 0.9f, 0.0f, 1.0f, 0.05f);

    public AntiKnockback() {
        super("AntiKnockback", "Reduces knockback from hits", Category.MOVEMENT);
        register(strength);
    }
}