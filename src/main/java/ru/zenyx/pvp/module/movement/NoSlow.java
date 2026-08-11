package ru.zenyx.pvp.module.movement;

import ru.zenyx.pvp.module.Category;
import ru.zenyx.pvp.module.Module;

public class NoSlow extends Module {
    public NoSlow() {
        super("NoSlow", "Removes slowdown when using items", Category.MOVEMENT);
    }

    // The actual hook lives in LivingEntityMixin; we only toggle the flag.
}