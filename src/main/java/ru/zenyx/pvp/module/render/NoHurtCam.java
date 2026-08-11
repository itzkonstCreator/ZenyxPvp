package ru.zenyx.pvp.module.render;

import ru.zenyx.pvp.module.Category;
import ru.zenyx.pvp.module.Module;

public class NoHurtCam extends Module {
    public NoHurtCam() {
        super("NoHurtCam", "Removes the camera tilt when taking damage", Category.RENDER);
    }
}