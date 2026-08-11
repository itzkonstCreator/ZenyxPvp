package ru.zenyx.pvp.module.movement;

import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import ru.zenyx.pvp.module.Category;
import ru.zenyx.pvp.module.Module;
import ru.zenyx.pvp.settings.BooleanSetting;
import ru.zenyx.pvp.settings.ModeSetting;
import ru.zenyx.pvp.util.BlockUtils;

/**
 * Auto-places blocks under the player's feet so they can walk over void.
 * Targets the block directly below the player; when standing on air, a side
 * block is chosen instead.
 */
public class Scaffold extends Module {
    public final ModeSetting mode = register(new ModeSetting("Mode", "Placement strategy", "Normal", "Normal", "Tower", "Godbridge"));
    public final BooleanSetting swing = register(new BooleanSetting("Swing", "Animate hand on placement", true));

    private int delayTicks = 0;

    public Scaffold() {
        super("Scaffold", "Auto-places blocks below you", Category.MOVEMENT);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null) return;
        if (delayTicks > 0) { delayTicks--; return; }

        BlockPos feet = BlockPos.ofFloored(mc.player.getPos());
        BlockPos target = feet.down();
        if (!mc.world.getBlockState(target).isAir()) return;

        // Look for the first non-air neighbour if the block below is somehow
        // solid (e.g. cobblestone path). Should never trigger, but defensive.
        for (Direction dir : Direction.Type.HORIZONTAL) {
            if (!mc.world.getBlockState(target.offset(dir)).isAir()) {
                target = target.offset(dir);
                break;
            }
        }

        if (!BlockUtils.isReplaceable(mc.world.getBlockState(target).getBlock())) return;

        Vec3d hit = new Vec3d(target.getX() + 0.5, target.getY() + 0.5, target.getZ() + 0.5);
        boolean placed = mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND,
                new net.minecraft.util.hit.BlockHitResult(hit, Direction.UP, target, false));
        if (placed) {
            if (swing.isEnabled()) mc.player.swingHand(Hand.MAIN_HAND);
            delayTicks = 1;
        }
    }
}