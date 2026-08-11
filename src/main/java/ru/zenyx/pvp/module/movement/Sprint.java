package ru.zenyx.pvp.module.movement;

import ru.zenyx.pvp.module.Category;
import ru.zenyx.pvp.module.Module;

public class Sprint extends Module {
    public Sprint() {
        super("Sprint", "Forces sprint", Category.MOVEMENT);
    }

    @Override
    public void onTick() {
        if (mc.player != null && !mc.player.isSprinting()) {
            mc.player.setSprinting(true);
        }
    }
}