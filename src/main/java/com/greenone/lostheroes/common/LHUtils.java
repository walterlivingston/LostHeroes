package com.greenone.lostheroes.common;

import com.greenone.lostheroes.LostHeroes;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class LHUtils {
    public static RayTraceResult getLookingAt(PlayerEntity player, int distance) {
        Vector3d output;
        World world = player.level;
        float f = player.xRot; // Pitch
        float f1 = player.yRot; // Yaw
        Vector3d vec3d = player.getEyePosition(1.0F);
        float f2 = MathHelper.cos(-f1 *((float) Math.PI / 180F) - (float) Math.PI);
        float f3 = MathHelper.sin(-f1 *((float) Math.PI / 180F) - (float) Math.PI);
        float f4 = -MathHelper.cos(-f *((float) Math.PI / 180F));
        float f5 = MathHelper.sin(-f *((float) Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        Vector3d vec3d1 = vec3d.add((double) f6*distance, (double) f5 * distance, (double) f7 * distance);
        return world.clip(new RayTraceContext(vec3d, vec3d1, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, player));
    }

    @OnlyIn(Dist.CLIENT)
    public static void renderRageOverlay() {
        int height = Minecraft.getInstance().getWindow().getGuiScaledHeight();
        int width = Minecraft.getInstance().getWindow().getGuiScaledWidth();

        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.defaultBlendFunc();
        RenderSystem.color4f(255, 96, 96, 1.0F);
        RenderSystem.disableAlphaTest();
        Minecraft.getInstance().getTextureManager().bind(new ResourceLocation(LostHeroes.MODID, "textures/gui/rage.png"));
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuilder();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.vertex(0.0D, (double)height, -90.0D).uv(0.0F, 1.0F).endVertex();
        bufferbuilder.vertex((double)width, (double)height, -90.0D).uv(1.0F, 1.0F).endVertex();
        bufferbuilder.vertex((double)width, 0.0D, -90.0D).uv(1.0F, 0.0F).endVertex();
        bufferbuilder.vertex(0.0D, 0.0D, -90.0D).uv(0.0F, 0.0F).endVertex();
        tessellator.end();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.enableAlphaTest();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static boolean isItemInInventory(PlayerEntity player, Item item) {
        for(ItemStack i : player.inventory.items){
            if(i != null && i.getItem()==item){
                return true;
            }
        }
        return false;
    }
}
