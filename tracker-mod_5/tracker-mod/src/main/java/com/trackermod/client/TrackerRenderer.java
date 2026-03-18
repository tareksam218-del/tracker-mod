package com.trackermod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

public class TrackerRenderer {

    private static final int MAX_DISTANCE = 128;

    // Convert ARGB int to float[4] rgba
    private static float[] toFloat(int argb) {
        return new float[]{
            ((argb >> 16) & 0xFF) / 255f,
            ((argb >> 8)  & 0xFF) / 255f,
            ( argb        & 0xFF) / 255f,
            1.0f
        };
    }

    public static void render(WorldRenderContext context) {
        if (!TrackerMod.trackerEnabled) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.world == null) return;

        World world = client.world;
        Vec3d camPos = context.camera().getPos();

        List<TrackerEntry> entries = new ArrayList<>();
        BlockPos playerPos = client.player.getBlockPos();
        int range = MAX_DISTANCE;

        for (BlockPos pos : BlockPos.iterate(
                playerPos.add(-range, -range, -range),
                playerPos.add(range, range, range))) {

            Block block = world.getBlockState(pos).getBlock();
            int typeIdx = -1;

            if (block instanceof ShulkerBoxBlock)                                                   typeIdx = 0;
            else if (block instanceof ChestBlock || block instanceof TrappedChestBlock || block instanceof BarrelBlock) typeIdx = 1;
            else if (block instanceof HopperBlock)                                                  typeIdx = 2;
            else if (block instanceof AbstractRailBlock)                                            typeIdx = 3;
            else if (block instanceof MobSpawnerBlock)                                              typeIdx = 4;
            else if (block instanceof PistonBlock || block instanceof StickyPistonBlock || block instanceof PistonHeadBlock) typeIdx = 5;

            if (typeIdx >= 0 && TrackerConfig.enabled[typeIdx]) {
                entries.add(new TrackerEntry(Vec3d.ofCenter(pos), toFloat(TrackerConfig.COLORS[typeIdx])));
            }
        }

        if (entries.isEmpty()) return;

        MatrixStack matrices = context.matrixStack();
        matrices.push();
        matrices.translate(-camPos.x, -camPos.y, -camPos.z);

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableDepthTest();
        RenderSystem.lineWidth(1.5f);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.LINES);

        Matrix4f matrix = matrices.peek().getPositionMatrix();
        org.joml.Matrix3f normalMatrix = matrices.peek().getNormalMatrix();

        for (TrackerEntry entry : entries) {
            float r = entry.color[0], g = entry.color[1], b = entry.color[2];
            float a = entry.color[3] * (float) Math.max(0.2, 1.0 - (camPos.distanceTo(entry.pos) / MAX_DISTANCE));

            buffer.vertex(matrix, (float) camPos.x, (float) camPos.y, (float) camPos.z)
                    .color(r, g, b, a).normal(normalMatrix, 0, 1, 0);
            buffer.vertex(matrix, (float) entry.pos.x, (float) entry.pos.y, (float) entry.pos.z)
                    .color(r, g, b, a).normal(normalMatrix, 0, 1, 0);
        }

        BufferRenderer.drawWithGlobalProgram(buffer.end());

        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
        matrices.pop();
    }

    private record TrackerEntry(Vec3d pos, float[] color) {}
}
