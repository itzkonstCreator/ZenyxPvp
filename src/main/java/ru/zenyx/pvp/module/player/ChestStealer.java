package ru.zenyx.pvp.module.player;

import net.minecraft.block.Blocks;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import ru.zenyx.pvp.module.Category;
import ru.zenyx.pvp.module.Module;
import ru.zenyx.pvp.settings.NumberSetting;

public class ChestStealer extends Module {
    public final NumberSetting delay = register(new NumberSetting("Delay", "Ticks between steals", 1.0f, 1.0f, 10.0f, 1.0f));

    private int cooldown = 0;

    public ChestStealer() {
        super("ChestStealer", "Steals all items from open chests", Category.PLAYER);
    }

    @Override
    public void onTick() {
        if (mc.player == null) return;
        if (cooldown-- > 0) return;
        cooldown = Math.round(delay.getValue());

        ScreenHandler sh = mc.player.currentScreenHandler;
        if (!(sh instanceof GenericContainerScreenHandler container)) return;

        // Skip if the chest still contains rows of the same valuable item,
        // but always steal once we see no valuables left.
        boolean any = false;
        for (Slot slot : container.slots) {
            if (!slot.hasStack()) continue;
            if (!slot.getStack().getItem().getDefaultStack().isOf(Blocks.AIR.asItem())) {
                mc.interactionManager.clickSlot(container.syncId, slot.id, 0, net.minecraft.screen.slot.SlotActionType.QUICK_MOVE, mc.player);
                any = true;
            }
        }
        if (!any && mc.currentScreen != null) {
            mc.player.closeHandledScreen();
        }
    }
}