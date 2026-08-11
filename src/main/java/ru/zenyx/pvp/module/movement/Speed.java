package ru.zenyx.pvp.module.movement;

import ru.zenyx.pvp.module.Category;
import ru.zenyx.pvp.module.Module;
import ru.zenyx.pvp.settings.ModeSetting;
import ru.zenyx.pvp.settings.NumberSetting;

public class Speed extends Module {
    public final ModeSetting mode = register(new ModeSetting("Mode", "Movement multiplier source", "Walk", "Walk", "Sprint"));
    public final NumberSetting multiplier = register(new NumberSetting("Multiplier", "Speed boost factor", 1.3f, 1.0f, 3.0f, 0.1f));

    public Speed() {
        super("Speed", "Boosts walking speed", Category.MOVEMENT);
    }

    @Override
    public void onTick() {
        if (mc.player == null) return;
        if (!mc.options.forwardKey.isPressed()) return;

        float factor = multiplier.getValue();
        if ("Sprint".equals(mode.getValue())) factor *= 1.3f;
        mc.player.setSprinting(true);
        mc.player.setVelocity(
                mc.player.getVelocity().x * factor,
                mc.player.getVelocity().y,
                mc.player.getVelocity().z * factor
        );
    }
}