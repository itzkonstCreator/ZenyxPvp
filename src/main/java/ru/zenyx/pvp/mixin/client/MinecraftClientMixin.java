package ru.zenyx.pvp.mixin.client;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.zenyx.pvp.module.combat.Reach;

/**
 * Adds the {@link Reach} delta to the entity reach used by vanilla attack code.
 * In 1.21.4 that value is read from {@code GameRenderer.pick} via the entity
 * attribute, so we use a mixin on the entity interaction range instead.
 */
@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        // Per-module tick handling lives in ModuleManager; this hook is kept
        // as a placeholder for cross-cutting logic (e.g. AutoClicker that
        // needs to drive the real mouse handler).
    }

    @ModifyArg(
            method = "doItemUse",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;use(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/TypedActionResult;"),
            index = 0
    )
    private net.minecraft.world.World adjustWorldForReach(net.minecraft.world.World world) {
        // No-op for now; Reach is applied via attribute modifier at the
        // entity level by a future mixin.
        return world;
    }
}
