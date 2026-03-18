package com.trackermod.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class TrackerScreen extends Screen {

    private static final int PANEL_W = 260;
    private static final int PANEL_H = 320;

    public TrackerScreen() {
        super(Text.literal("Tracker"));
    }

    @Override
    protected void init() {
        int x = (this.width - PANEL_W) / 2;
        int y = (this.height - PANEL_H) / 2;

        int btnX = x + 20;
        int btnW = PANEL_W - 40;
        int startY = y + 60;
        int spacing = 34;

        // One toggle button per type
        for (int i = 0; i < TrackerConfig.LABELS.length; i++) {
            final int idx = i;
            this.addDrawableChild(new ToggleButton(
                    btnX, startY + (spacing * i), btnW, 26,
                    idx,
                    btn -> TrackerConfig.enabled[idx] = !TrackerConfig.enabled[idx]
            ));
        }

        // Close button
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Close"), btn -> this.close())
                .dimensions(x + (PANEL_W / 2) - 50, y + PANEL_H - 36, 100, 22)
                .build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Dim background
        this.renderBackground(context, mouseX, mouseY, delta);

        int x = (this.width - PANEL_W) / 2;
        int y = (this.height - PANEL_H) / 2;

        // Panel background
        context.fill(x, y, x + PANEL_W, y + PANEL_H, 0xE8101010);
        // Border
        context.fill(x, y, x + PANEL_W, y + 1, 0xFF444444);
        context.fill(x, y + PANEL_H - 1, x + PANEL_W, y + PANEL_H, 0xFF444444);
        context.fill(x, y, x + 1, y + PANEL_H, 0xFF444444);
        context.fill(x + PANEL_W - 1, y, x + PANEL_W, y + PANEL_H, 0xFF444444);

        // Title bar
        context.fill(x, y, x + PANEL_W, y + 42, 0xFF1A1A1A);
        context.fill(x, y + 42, x + PANEL_W, y + 43, 0xFF333333);

        // Title text
        context.drawCenteredTextWithShadow(this.textRenderer,
                Text.literal("§f§lTRACKER §8§lMOD"), x + PANEL_W / 2, y + 10, 0xFFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer,
                Text.literal("§7Toggle what to track"), x + PANEL_W / 2, y + 26, 0xAAAAAA);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    // ---- Inner toggle button ----
    static class ToggleButton extends ButtonWidget {
        private final int idx;

        ToggleButton(int x, int y, int width, int height, int idx, PressAction onPress) {
            super(x, y, width, height, Text.empty(), onPress, DEFAULT_NARRATION_SUPPLIER);
            this.idx = idx;
        }

        @Override
        public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
            boolean on = TrackerConfig.enabled[idx];
            int color = TrackerConfig.COLORS[idx];
            String label = TrackerConfig.LABELS[idx];

            // Button bg
            int bg = this.isHovered() ? 0xFF2A2A2A : 0xFF1E1E1E;
            context.fill(this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.height, bg);

            // Color swatch
            context.fill(this.getX() + 8, this.getY() + 7, this.getX() + 20, this.getY() + this.height - 7, on ? color : 0xFF444444);

            // Label
            context.drawTextWithShadow(
                    net.minecraft.client.MinecraftClient.getInstance().textRenderer,
                    Text.literal(on ? "§f" + label : "§8" + label),
                    this.getX() + 28, this.getY() + 9, 0xFFFFFF
            );

            // ON/OFF badge
            String badge = on ? "§aON" : "§cOFF";
            int badgeX = this.getX() + this.width - 32;
            context.drawTextWithShadow(
                    net.minecraft.client.MinecraftClient.getInstance().textRenderer,
                    Text.literal(badge),
                    badgeX, this.getY() + 9, 0xFFFFFF
            );

            // Bottom divider
            context.fill(this.getX(), this.getY() + this.height - 1, this.getX() + this.width, this.getY() + this.height, 0xFF2A2A2A);
        }
    }
}
