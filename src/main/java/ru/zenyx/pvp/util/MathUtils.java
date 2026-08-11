package ru.zenyx.pvp.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/** Small geometric / numeric helpers shared across modules. */
public final class MathUtils {
    private MathUtils() {}

    public static float clamp(float v, float min, float max) {
        return Math.max(min, Math.min(max, v));
    }

    public static int clamp(int v, int min, int max) {
        return Math.max(min, Math.min(max, v));
    }

    public static Vec3d interpolate(Vec3d prev, Vec3d current, float tickDelta) {
        double x = prev.x + (current.x - prev.x) * tickDelta;
        double y = prev.y + (current.y - prev.y) * tickDelta;
        double z = prev.z + (current.z - prev.z) * tickDelta;
        return new Vec3d(x, y, z);
    }

    public static double distanceTo(PlayerEntity player, Vec3d target) {
        Vec3d self = player.getPos();
        return Math.sqrt(self.squaredDistanceTo(target));
    }

    public static BlockPos blockPos(Vec3d vec) {
        return new BlockPos((int) Math.floor(vec.x), (int) Math.floor(vec.y), (int) Math.floor(vec.z));
    }

    public static boolean inRangeXZ(PlayerEntity a, PlayerEntity b, double range) {
        return a.squaredDistanceTo(b) <= range * range;
    }
}