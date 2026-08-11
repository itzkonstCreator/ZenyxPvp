package ru.zenyx.pvp.module.movement;

import ru.zenyx.pvp.module.Category;
import ru.zenyx.pvp.module.Module;

/**
 * Removes the cooldown between block placements by cancelling the vanilla
 * delay packet. Done through a mixin on {@code PlayerActionC2SPacket} handler.
 */
public class FastPlace extends Module {
    public FastPlace() {
        super("FastPlace", "Removes block place cooldown", Category.MOVEMENT);
    }
}