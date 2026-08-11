package ru.zenyx.pvp.module.player;

import net.minecraft.item.Items;
import ru.zenyx.pvp.module.Category;
import ru.zenyx.pvp.module.Module;
import ru.zenyx.pvp.util.PlayerUtils;

public class AutoTotem extends Module {
    public AutoTotem() {
        super("AutoTotem", "Equips a totem in the offhand", Category.PLAYER);
    }

    @Override
    public void onTick() {
        if (mc.player == null) return;
        if (PlayerUtils.isTotemInHand()) return;

        int slot = PlayerUtils.findHotbarSlot(Items.TOTEM_OF_UNDYING);
        if (slot != -1 && mc.player.getOffHandStack().isEmpty()) {
            mc.interactionManager.swapItems(slot, 40); // swap hotbar slot with offhand
        }
    }
}