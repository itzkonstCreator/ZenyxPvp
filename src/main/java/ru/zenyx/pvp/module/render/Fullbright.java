package ru.zenyx.pvp.module.render;

import ru.zenyx.pvp.module.Category;
import ru.zenyx.pvp.module.Module;

public class Fullbright extends Module {
    public Fullbright() {
        super("Fullbright", "Sets render gamma to maximum", Category.RENDER);
    }

    @Override
    public void onEnable() {
        if (mc.options != null) mc.options.getGamma().setValue(16.0);
    }

    @Override
    public void onDisable() {
        if (mc.options != null) mc.options.getGamma().setValue(1.0);
    }
}