package ru.zenyx.pvp.module.combat;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import ru.zenyx.pvp.module.Category;
import ru.zenyx.pvp.module.Module;
import ru.zenyx.pvp.settings.BooleanSetting;
import ru.zenyx.pvp.settings.NumberSetting;
import ru.zenyx.pvp.util.PlayerUtils;

import java.util.List;

/**
 * Lightweight KillAura: attacks the closest player in range every
 * {@link #attackDelayTicks} ticks. Stops the moment the player releases attack.
 */
public class KillAura extends Module {
    private final NumberSetting range = register(new NumberSetting("Range", "Attack reach in blocks", 4.0f, 1.0f, 6.0f, 0.1f));
    private final NumberSetting delay = register(new NumberSetting("Delay", "Ticks between attacks", 5.0f, 1.0f, 20.0f, 1.0f));
    private final BooleanSetting crit = register(new BooleanSetting("Critical", "Try to land crits by jumping", true));

    private int ticksSinceAttack = 0;

    public KillAura() {
        super("KillAura", "Attacks the closest player", Category.COMBAT);
    }

    @Override
    public void onTick() {
        if (mc.options.attackKey.isPressed() && mc.player != null) {
            ticksSinceAttack++;
            if (ticksSinceAttack < delay.getValue().intValue()) return;
            ticksSinceAttack = 0;

            List<PlayerEntity> players = PlayerUtils.playersInRange(range.getValue());
            if (players.isEmpty()) return;
            PlayerEntity target = players.get(0);
            mc.interactionManager.attackEntity(mc.player, target);
            mc.player.swingHand(Hand.MAIN_HAND);

            if (crit.isEnabled() && mc.player.isOnGround()) {
                mc.player.jump();
            }
        }
    }
}