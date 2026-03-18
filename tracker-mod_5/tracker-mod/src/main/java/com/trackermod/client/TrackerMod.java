package com.trackermod.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class TrackerMod implements ClientModInitializer {

    public static KeyBinding toggleKey;
    public static boolean trackerEnabled = false;

    @Override
    public void onInitializeClient() {
        toggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.trackermod.toggle",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_G,
                "category.trackermod"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleKey.wasPressed()) {
                // Open the screen if not already open, otherwise close
                if (client.currentScreen instanceof TrackerScreen) {
                    client.setScreen(null);
                    trackerEnabled = false;
                } else {
                    client.setScreen(new TrackerScreen());
                    trackerEnabled = true;
                }
            }
        });

        WorldRenderEvents.AFTER_TRANSLUCENT.register(TrackerRenderer::render);
    }
}
