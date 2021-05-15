package com.greenone.lostheroes.common.util;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.capabilities.IPlayerCap;
import com.greenone.lostheroes.common.init.Blessings;
import com.greenone.lostheroes.common.potions.LHEffects;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.Block;
import net.minecraft.block.CropsBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.List;

public class LHUtils {
    public static void abilityCheck(PlayerEntity player, IPlayerCap playerCap){
        switch(playerCap.getParent().getName()){
            case "zeus":
                if(!(player.isCreative() || player.isSpectator())){
                    if(playerCap.getMana() > 0.5F) player.addEffect(new EffectInstance(Blessings.ZEUS, 30, 1, false, false, false, null));
                    if(player.abilities.flying) playerCap.consumeMana(0.006F);
                }
                break;
            case "poseidon":
                if(!(player.isCreative() || player.isSpectator()))
                    if(player.isUnderWater() && player.getAirSupply() < player.getMaxAirSupply())
                        player.setAirSupply(determineNextAir(player, player.getAirSupply()));
                break;
            case "hades":
                break;
            case "athena":
                break;
            case "ares":
                break;
            case "aphrodite":
                if(!player.isSpectator() && player.isSteppingCarefully() && playerCap.consumeMana(0.008F)){
                    List<CreatureEntity> mobs = player.level.getNearbyEntities(CreatureEntity.class, new EntityPredicate().range(10), player, new AxisAlignedBB(player.blockPosition()).inflate(10));
                    mobs.stream().filter(mob -> !(mob instanceof MonsterEntity)).forEach(mob -> {
                        mob.getLookControl().setLookAt(mob, (float) (mob.getHeadRotSpeed() + 20), (float) mob.getHeadRotSpeed());
                        mob.getNavigation().moveTo(player, 1.25D);
                    });
                }
                break;
            case "apollo":
                if(player.level.isDay())
                    playerCap.getParent().applyAttributesModifiersToEntity(player, player.getAttributes(), 0);
                else
                    playerCap.getParent().removeAttributeModifiers(player, player.getAttributes(), 0);
                break;
            case "artemis":
                if(player.level.isNight())
                    playerCap.getParent().applyAttributesModifiersToEntity(player, player.getAttributes(), 0);
                else
                    playerCap.getParent().removeAttributeModifiers(player, player.getAttributes(), 0);
                break;
            case "dionysus":
                player.addEffect(new EffectInstance(Blessings.DIONYSUS, 30, 1, false, false, false, null));
                break;
            case "demeter":
                if(player.isSteppingCarefully() && (player.isCreative() || playerCap.consumeMana(0.008F))){
                    player.addEffect(new EffectInstance(Blessings.DEMETER, 30, 1, false, false, false, null));
                }
                break;
            case "hermes":
                if(player.isSprinting() && (player.isCreative() || playerCap.getMana() > 0.5F)){
                    player.addEffect(new EffectInstance(Blessings.HERMES, 30, 1, false, false, false, null));
                    playerCap.consumeMana(0.008F);
                }
                break;
            case "hephaestus":
                player.addEffect(new EffectInstance(LHEffects.FIRE_RESISTANCE, 30, 1, false, false, false, null));
                break;
        }
    }

    private static int determineNextAir(PlayerEntity player, int currentAir) {
        return Math.min(currentAir + 4, player.getMaxAirSupply());
    }

    public static Vector3d getLookingAt(PlayerEntity player, int distance) {
        Vector3d output;
        World world = player.level;
        float f = player.xRot;
        float f1 = player.yRot;
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

    public static boolean isItemInInventory(PlayerEntity player, Item item) {
        ItemStack itemStack = null;
        for(ItemStack i : player.inventory.items){
            if(i != null && i.getItem()==item){
                return true;
            }
        }
        return false;
    }

    public static void renderRageOverlay() {
        int height = Minecraft.getInstance().getWindow().getGuiScaledHeight();
        int width = Minecraft.getInstance().getWindow().getGuiScaledWidth();

        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.defaultBlendFunc();
        RenderSystem.color4f(255, 96, 96, 1.0F);
        RenderSystem.disableAlphaTest();
        Minecraft.getInstance().getTextureManager().bind(new ResourceLocation(LostHeroes.MOD_ID, "textures/misc/rage_overlay.png"));
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
}