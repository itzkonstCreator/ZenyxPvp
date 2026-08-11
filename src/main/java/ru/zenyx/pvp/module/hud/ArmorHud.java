package ru.zenyx.pvp.module.hud;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import ru.zenyx.pvp.module.Category;
import ru.zenyx.pvp.module.Module;

public class ArmorHud extends Module {
    public ArmorHud() {
        super("Armor", "Displays the equipped armour", Category.HUD);
    }

    @Override
    public void onHudRender(DrawContext ctx) {
        if (mc.player == null) return;
        int x = mc.getWindow().getScaledWidth() / 2 - 9;
        int y = mc.getWindow().getScaledHeight() - 58;
        for (ItemStack stack : mc.player.getArmorItems()) {
            if (stack.isEmpty()) {
                x += 18;
                continue;
            }
            ctx.drawItem(stack, x, y);
            x += 18;
        }
    }
}