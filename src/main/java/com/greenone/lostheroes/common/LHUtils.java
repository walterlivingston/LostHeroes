package com.greenone.lostheroes.common;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class LHUtils {
    public static Vector3d getLookingAt(PlayerEntity player, int distance) {
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
        RayTraceResult trace = world.clip(new RayTraceContext(vec3d, vec3d1, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, player));
        output = trace.getLocation();
        return output;
    }
}
