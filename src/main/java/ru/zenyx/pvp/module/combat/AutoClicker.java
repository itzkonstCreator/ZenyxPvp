package ru.zenyx.pvp.module.combat;

import ru.zenyx.pvp.module.Category;
import ru.zenyx.pvp.module.Module;
import ru.zenyx.pvp.settings.NumberSetting;
import ru.zenyx.pvp.settings.BooleanSetting;

/**
 * Holds the left mouse button at a configurable clicks-per-second. Acts as a
 * generic jitter generator by submitting fresh attack packets while the key is
 * held.
 */
public class AutoClicker extends Module {
    public final NumberSetting cps = register(new NumberSetting("CPS", "Average clicks per second", 9.0f, 1.0f, 20.0f, 0.5f));
    public final BooleanSetting jitter = register(new BooleanSetting("Jitter", "Randomise interval", true));

    private int delayMs = 110;

    public AutoClicker() {
        super("AutoClicker", "Auto clicks LMB at fixed CPS", Category.COMBAT);
    }

    @Override
    public void onTick() {
        if (mc.mouse == null || mc.player == null) return;
        float cpsValue = cps.getValue();
        long baseMs = Math.max(50, Math.round(1000.0f / cpsValue));

        if (jitter.isEnabled()) {
            // +/- 25% noise so packets look less regular.
            long noise = (long) (baseMs * 0.25 * (Math.random() - 0.5) * 2);
            delayMs = (int) Math.max(40, baseMs + noise);
        } else {
            delayMs = (int) baseMs;
        }
        // Real implementation uses a mixin on MinecraftClient to inject input events,
        // see MinecraftClientMixin#onTick. Here we only update the interval.
    }
}