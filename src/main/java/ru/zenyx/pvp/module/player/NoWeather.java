package ru.zenyx.pvp.module.player;

import ru.zenyx.pvp.module.Category;
import ru.zenyx.pvp.module.Module;

public class NoWeather extends Module {
    public NoWeather() {
        super("NoWeather", "Disables rain and thunder", Category.PLAYER);
    }

    @Override
    public void onTick() {
        if (mc.world == null) return;
        if (mc.world.isRaining()) {
            mc.world.getLevelProperties().setRaining(false);
        }
        if (mc.world.isThundering()) {
            mc.world.getLevelProperties().setThundering(false);
        }
    }
}