package ru.zenyx.pvp.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;

import java.util.Comparator;
import java.util.List;

/** High-level wrappers around player inventory & combat checks. */
public final class PlayerUtils {
    private PlayerUtils() {}

    public static ClientPlayerEntity player() {
        return MinecraftClient.getInstance().player;
    }

    public static int findHotbarSlot(Item item) {
        ClientPlayerEntity p = player();
        if (p == null) return -1;
        for (int i = 0; i < 9; i++) {
            ItemStack s = p.getInventory().getStack(i);
            if (!s.isEmpty() && s.getItem() == item) return i;
        }
        return -1;
    }

    public static void swapToSlot(int slot) {
        ClientPlayerEntity p = player();
        if (p == null || slot < 0) return;
        p.getInventory().selectedSlot = slot;
    }

    public static List<PlayerEntity> playersInRange(double range) {
        ClientPlayerEntity self = player();
        if (self == null || self.clientWorld == null) return List.of();
        return self.clientWorld.getPlayers().stream()
                .filter(p -> p != self && p.isAlive())
                .filter(p -> p.squaredDistanceTo(self) <= range * range)
                .sorted(Comparator.comparingDouble(self::squaredDistanceTo))
                .toList();
    }

    public static double distanceTo(Entity e) {
        ClientPlayerEntity self = player();
        if (self == null) return Double.MAX_VALUE;
        return Math.sqrt(self.squaredDistanceTo(e));
    }

    public static boolean isTotemInHand() {
        ClientPlayerEntity p = player();
        return p != null
                && (p.getMainHandStack().getItem() == Items.TOTEM_OF_UNDYING
                || p.getOffHandStack().getItem() == Items.TOTEM_OF_UNDYING);
    }

    public static Hand getTotemHand() {
        ClientPlayerEntity p = player();
        if (p == null) return Hand.MAIN_HAND;
        return p.getOffHandStack().getItem() == Items.TOTEM_OF_UNDYING ? Hand.OFF_HAND : Hand.MAIN_HAND;
    }

    public static BlockPos feet() {
        ClientPlayerEntity p = player();
        return p != null ? p.getBlockPos() : BlockPos.ORIGIN;
    }
}