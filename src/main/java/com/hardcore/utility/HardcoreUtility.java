package com.hardcore.utility;

import com.hardcore.utility.gui.ConfigScreen;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.LiteralText;
import org.lwjgl.glfw.GLFW;

public class HardcoreUtility implements ModInitializer {
    public static boolean godMode = false, flight = false, killAura = false, fullBright = false, speed = false, autoTotem = false, spider = false, fastPlace = false;
    public static boolean noFall = false, jesus = false, step = false, xray = false;

    private static KeyBinding godModeKey, flightKey, killAuraKey, fullBrightKey, speedKey, autoTotemKey, spiderKey, fastPlaceKey;
    private static KeyBinding noFallKey, jesusKey, stepKey, xrayKey, menuKey;

    @Override
    public void onInitialize() {
        godModeKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.hardcoreutility.godmode", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, "category.hardcoreutility"));
        flightKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.hardcoreutility.flight", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F, "category.hardcoreutility"));
        killAuraKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.hardcoreutility.killaura", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_K, "category.hardcoreutility"));
        fullBrightKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.hardcoreutility.fullbright", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_B, "category.hardcoreutility"));
        speedKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.hardcoreutility.speed", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_V, "category.hardcoreutility"));
        autoTotemKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.hardcoreutility.autototem", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_T, "category.hardcoreutility"));
        spiderKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.hardcoreutility.spider", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_Y, "category.hardcoreutility"));
        fastPlaceKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.hardcoreutility.fastplace", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_P, "category.hardcoreutility"));
        noFallKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.hardcoreutility.nofall", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_N, "category.hardcoreutility"));
        jesusKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.hardcoreutility.jesus", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_J, "category.hardcoreutility"));
        stepKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.hardcoreutility.step", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_H, "category.hardcoreutility"));
        xrayKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.hardcoreutility.xray", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_X, "category.hardcoreutility"));
        menuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.hardcoreutility.menu", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, "category.hardcoreutility"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.world == null) return;

            if (menuKey.wasPressed()) {
                client.openScreen(new ConfigScreen());
            }

            while (godModeKey.wasPressed()) { godMode = !godMode; client.player.sendMessage(new LiteralText("§6[Utility] §fGod Mode: " + (godMode ? "§aON" : "§cOFF")), true); }
            while (flightKey.wasPressed()) { flight = !flight; client.player.sendMessage(new LiteralText("§6[Utility] §fFlight: " + (flight ? "§aON" : "§cOFF")), true); }
            while (killAuraKey.wasPressed()) { killAura = !killAura; client.player.sendMessage(new LiteralText("§6[Utility] §fKillAura: " + (killAura ? "§aON" : "§cOFF")), true); }
            while (fullBrightKey.wasPressed()) { fullBright = !fullBright; client.player.sendMessage(new LiteralText("§6[Utility] §fFullBright: " + (fullBright ? "§aON" : "§cOFF")), true); }
            while (speedKey.wasPressed()) { speed = !speed; client.player.sendMessage(new LiteralText("§6[Utility] §fSpeed: " + (speed ? "§aON" : "§cOFF")), true); }
            while (autoTotemKey.wasPressed()) { autoTotem = !autoTotem; client.player.sendMessage(new LiteralText("§6[Utility] §fAutoTotem: " + (autoTotem ? "§aON" : "§cOFF")), true); }
            while (spiderKey.wasPressed()) { spider = !spider; client.player.sendMessage(new LiteralText("§6[Utility] §fSpider: " + (spider ? "§aON" : "§cOFF")), true); }
            while (fastPlaceKey.wasPressed()) { fastPlace = !fastPlace; client.player.sendMessage(new LiteralText("§6[Utility] §fFastPlace: " + (fastPlace ? "§aON" : "§cOFF")), true); }
            while (noFallKey.wasPressed()) { noFall = !noFall; client.player.sendMessage(new LiteralText("§6[Utility] §fNoFall: " + (noFall ? "§aON" : "§cOFF")), true); }
            while (jesusKey.wasPressed()) { jesus = !jesus; client.player.sendMessage(new LiteralText("§6[Utility] §fJesus: " + (jesus ? "§aON" : "§cOFF")), true); }
            while (stepKey.wasPressed()) { step = !step; client.player.sendMessage(new LiteralText("§6[Utility] §fStep: " + (step ? "§aON" : "§cOFF")), true); }
            while (xrayKey.wasPressed()) { xray = !xray; client.player.sendMessage(new LiteralText("§6[Utility] §fX-Ray: " + (xray ? "§aON" : "§cOFF")), true); client.worldRenderer.reload(); }

            // Logic: NoFall
            if (noFall && client.player.fallDistance > 2.0f) {
                client.player.networkHandler.sendPacket(new net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket(true));
                client.player.fallDistance = 0;
            }

            // Logic: Jesus (Walk on Water)
            if (jesus && client.player.isTouchingWater()) {
                net.minecraft.util.math.Vec3d v = client.player.getVelocity();
                client.player.setVelocity(v.x, 0.1, v.z);
            }

            // Logic: Step
            client.player.stepHeight = step ? 1.0f : 0.6f;

            // Logic: AutoTotem, Spider, Flight, etc. (Previous Logic Combined)
            if (autoTotem && client.player.getOffHandStack().getItem() != net.minecraft.item.Items.TOTEM_OF_UNDYING) {
                for (int i = 0; i < 36; i++) {
                    if (client.player.inventory.getStack(i).getItem() == net.minecraft.item.Items.TOTEM_OF_UNDYING) {
                        client.interactionManager.clickSlot(client.player.currentScreenHandler.syncId, i < 9 ? i + 36 : i, 45, net.minecraft.screen.slot.SlotActionType.SWAP, client.player);
                        break;
                    }
                }
            }
            if (spider && client.player.horizontalCollision) {
                client.player.setVelocity(client.player.getVelocity().x, 0.2, client.player.getVelocity().z);
            }
            client.player.abilities.allowFlying = flight;
            if (!flight && !client.player.isCreative()) client.player.abilities.flying = false;
            if (killAura && client.player.age % 2 == 0) {
                for (net.minecraft.entity.Entity e : client.world.getEntities()) {
                    if (e instanceof net.minecraft.entity.mob.HostileEntity && e.isAlive() && client.player.distanceTo(e) < 4.5) {
                        client.interactionManager.attackEntity(client.player, e);
                        client.player.swingHand(net.minecraft.util.Hand.MAIN_HAND);
                        break;
                    }
                }
            }
            if (fullBright || xray) client.options.gamma = 100.0;
            else if (client.options.gamma > 1.0) client.options.gamma = 1.0;
            if (speed && client.player.isOnGround()) client.player.updateVelocity(0.1f, new net.minecraft.util.math.Vec3d(0, 0, 1));
        });
    }
}
