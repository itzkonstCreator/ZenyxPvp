package ru.zenyx.pvp.module.movement;

import net.minecraft.util.math.Vec3d;
import ru.zenyx.pvp.module.Category;
import ru.zenyx.pvp.module.Module;
import ru.zenyx.pvp.settings.ModeSetting;
import ru.zenyx.pvp.settings.NumberSetting;

public class Fly extends Module {
    public final ModeSetting mode = register(new ModeSetting("Mode", "How to lift the player", "Static", "Static", "Velocity", "Bounce"));
    public final NumberSetting speed = register(new NumberSetting("Speed", "Upward speed (blocks/sec)", 0.5f, 0.05f, 2.0f, 0.05f));

    public Fly() {
        super("Fly", "Creative-style flight on survival", Category.MOVEMENT);
    }

    @Override
    public void onTick() {
        if (mc.player == null) return;
        mc.player.getAbilities().flying = true;
        mc.player.getAbilities().allowFlying = true;
        switch (mode.getValue()) {
            case "Velocity" -> mc.player.addVelocity(0, speed.getValue() * 0.05, 0);
            case "Bounce" -> {
                if (mc.player.isOnGround()) mc.player.addVelocity(0, 0.42, 0);
                else mc.player.addVelocity(0, -0.02, 0);
            }
            default -> mc.player.setVelocity(mc.player.getVelocity().x,
                    mc.options.jumpKey.isPressed() ? speed.getValue()
                            : mc.options.sneakKey.isPressed() ? -speed.getValue() : 0,
                    mc.player.getVelocity().z);
        }
    }

    @Override
    public void onDisable() {
        if (mc.player != null) {
            mc.player.getAbilities().flying = false;
            mc.player.getAbilities().allowFlying = false;
        }
    }
}