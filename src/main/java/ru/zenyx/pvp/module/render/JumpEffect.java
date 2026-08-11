package ru.zenyx.pvp.module.render;

import net.minecraft.particle.ParticleTypes;
import ru.zenyx.pvp.module.Category;
import ru.zenyx.pvp.module.Module;

public class JumpEffect extends Module {
    public JumpEffect() {
        super("JumpEffect", "Spawns particles when jumping", Category.RENDER);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null) return;
        if (mc.player.isOnGround() || mc.player.fallDistance > 0) return;
        for (int i = 0; i < 8; i++) {
            double dx = (Math.random() - 0.5) * 0.6;
            double dz = (Math.random() - 0.5) * 0.6;
            mc.world.addParticle(ParticleTypes.CRIT,
                    mc.player.getX() + dx, mc.player.getY(), mc.player.getZ() + dz,
                    0, 0, 0);
        }
    }
}