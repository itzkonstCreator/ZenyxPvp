package ru.zenyx.pvp.module.movement;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import ru.zenyx.pvp.module.Category;
import ru.zenyx.pvp.module.Module;

/**
 * Prevents fall damage by spoofing an on-ground flag right before the player
 * hits the ground. Mixin on {@code LivingEntity#fall} would be a safer spot, but
 * a packet spoof is sufficient for most PvP scenarios.
 */
public class NoFall extends Module {
    public NoFall() {
        super("NoFall", "Spoofs on-ground to negate fall damage", Category.MOVEMENT);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.player.networkHandler == null) return;
        if (mc.player.fallDistance <= 2) return;
        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true, mc.player.horizontalCollision));
        mc.player.fallDistance = 0;
    }
}