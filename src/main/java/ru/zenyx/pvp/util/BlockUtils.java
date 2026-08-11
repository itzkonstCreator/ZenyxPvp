package ru.zenyx.pvp.util;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

/** Block helpers used by Scaffold and ESP-style modules. */
public final class BlockUtils {
    private BlockUtils() {}

    public static boolean isReplaceable(Block b) {
        return b == Blocks.AIR
                || b == Blocks.WATER
                || b == Blocks.LAVA
                || b == Blocks.SHORT_GRASS
                || b == Blocks.TALL_GRASS
                || b == Blocks.FERN
                || b == Blocks.LARGE_FERN
                || b == Blocks.SNOW
                || b == Blocks.VINE
                || b == Blocks.DEAD_BUSH
                || b == Blocks.SEAGRASS
                || b == Blocks.TALL_SEAGRASS
                || b == Blocks.KELP
                || b == Blocks.KELP_PLANT;
    }

    public static List<BlockPos> nearbyBlocks(World world, BlockPos origin, int radius) {
        List<BlockPos> out = new ArrayList<>();
        BlockPos.stream(origin, radius).forEach(pos -> out.add(pos.toImmutable()));
        return out;
    }
}