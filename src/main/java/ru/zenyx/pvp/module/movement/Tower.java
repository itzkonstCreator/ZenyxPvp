package ru.zenyx.pvp.module.movement;

import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import ru.zenyx.pvp.module.Category;
import ru.zenyx.pvp.module.Module;
import ru.zenyx.pvp.settings.BooleanSetting;
import ru.zenyx.pvp.settings.NumberSetting;
import ru.zenyx.pvp.util.BlockUtils;

/**
 * Combines Scaffold and a jump-on-place rhythm so the player climbs up
 * indefinitely while holding Space.
 */
public class Tower extends Module {
    public final NumberSetting speed = register(new NumberSetting("Speed", "Ticks between placements", 1.0f, 1.0f, 10.0f, 1.0f));
    public final BooleanSetting sneak = register(new BooleanSetting("SneakMode", "Tower while sneaking (vanilla behaviour)", false));

    private int cooldown = 0;

    public Tower() {
        super("Tower", "Build up while jumping", Category.MOVEMENT);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null) return;
        if (!mc.options.jumpKey.isPressed() || (!sneak.isEnabled() && !mc.player.isSneaking())) {
            cooldown = 0;
            return;
        }
        if (cooldown > 0) { cooldown--; return; }

        BlockPos feet = BlockPos.ofFloored(mc.player.getPos()).down();
        if (!BlockUtils.isReplaceable(mc.world.getBlockState(feet).getBlock())) return;

        Vec3d hitVec = new Vec3d(feet.getX() + 0.5, feet.getY() + 1, feet.getZ() + 0.5);
        mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND,
                new net.minecraft.util.hit.BlockHitResult(hitVec, Direction.UP, feet, false));
        mc.player.jump();
        mc.player.setVelocity(mc.player.getVelocity().x, 0.42, mc.player.getVelocity().z);
        cooldown = Math.round(speed.getValue());
    }
}