package com.greenone.lostheroes.common.util;

import com.greenone.lostheroes.common.capabilities.IPlayerCap;
import com.greenone.lostheroes.common.init.Blessings;
import com.greenone.lostheroes.common.init.LHEffects;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Random;

public class LHUtils {
    public static void abilityCheck(Player player, IPlayerCap playerCap){
        switch(playerCap.getParent().getName()){
            case "zeus":
                if(!(player.isCreative() || player.isSpectator())){
                    if(playerCap.getMana() > 0.5F) player.addEffect(new MobEffectInstance(Blessings.ZEUS, 30, 1, false, false, false, null));
                    if(player.getAbilities().flying) playerCap.consumeMana(0.012F);
                }
                break;
            case "poseidon":
                if(!(player.isCreative() || player.isSpectator()))
                    if(player.isUnderWater() && player.getAirSupply() < player.getMaxAirSupply()){
                        player.setAirSupply(determineNextAir(player, player.getAirSupply()));
                        if(new Random().nextFloat() <= 0.10) player.heal(0.5f);
                    }
                break;
            case "hades":
                break;
            case "athena":
                break;
            case "ares":
                break;
            case "aphrodite":
                if(!player.isSpectator() && player.isSteppingCarefully() && ( player.isCreative() || playerCap.consumeMana(0.008F))){
                    AABB aabb = (new AABB(player.blockPosition())).inflate(10).expandTowards(0.0D, (double)player.level.getHeight(), 0.0D);
                    List<AgeableMob> mobs = player.level.getEntitiesOfClass(AgeableMob.class, aabb);
                    mobs.stream().forEach(mob -> {
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

                System.out.println(player.level.getSkyDarken());
                break;
            case "demeter":
                if(player.isSteppingCarefully() && (player.isCreative() || playerCap.consumeMana(0.008F))){
                    player.addEffect(new MobEffectInstance(Blessings.DEMETER, 30, 1, false, false, false, null));
                }
                break;
            case "dionysus":
                player.addEffect(new MobEffectInstance(Blessings.DIONYSUS, 30, 1, false, false, false, null));
                break;

            case "hermes":
                if(player.isSprinting() && (player.isCreative() || playerCap.getMana() > 0.5F)){
                    player.addEffect(new MobEffectInstance(Blessings.HERMES, 30, 1, false, false, false, null));
                    playerCap.consumeMana(0.008F);
                }
                break;
            case "hephaestus":
                player.addEffect(new MobEffectInstance(LHEffects.FIRE_RESISTANCE, 30, 1, false, false, false, null));
                break;
        }
    }

    private static int determineNextAir(Player player, int currentAir) {
        return Math.min(currentAir + 4, player.getMaxAirSupply());
    }

    public static Vec3 getLookingAt(Player player, int distance) {
        Vec3 output;
        Level world = player.level;
        float f = player.xRotO; // Pitch
        float f1 = player.yRotO; // Yaw
        Vec3 vec3d = player.getEyePosition(1.0F);
        float f2 = Mth.cos(-f1 *((float) Math.PI / 180F) - (float) Math.PI);
        float f3 = Mth.sin(-f1 *((float) Math.PI / 180F) - (float) Math.PI);
        float f4 = -Mth.cos(-f *((float) Math.PI / 180F));
        float f5 = Mth.sin(-f *((float) Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        Vec3 vec3d1 = vec3d.add((double) f6*distance, (double) f5 * distance, (double) f7 * distance);
        BlockHitResult trace = world.clip(new ClipContext(vec3d, vec3d1, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
        output = trace.getLocation();
        return output;
    }

    public static boolean isItemInInventory(Player player, Item item) {
        ItemStack itemStack = null;
        for(ItemStack i : player.inventoryMenu.getItems()){
            if(i != null && i.getItem()==item){
                return true;
            }
        }
        return false;
    }

    public static void renderTextureOverlay(ResourceLocation p_168709_, float p_168710_) {
        int height = Minecraft.getInstance().getWindow().getGuiScaledHeight();
        int width = Minecraft.getInstance().getWindow().getGuiScaledWidth();

        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, p_168710_);
        RenderSystem.setShaderTexture(0, p_168709_);
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.vertex(0.0D, (double)height, -90.0D).uv(0.0F, 1.0F).endVertex();
        bufferbuilder.vertex((double)width, (double)height, -90.0D).uv(1.0F, 1.0F).endVertex();
        bufferbuilder.vertex((double)width, 0.0D, -90.0D).uv(1.0F, 0.0F).endVertex();
        bufferbuilder.vertex(0.0D, 0.0D, -90.0D).uv(0.0F, 0.0F).endVertex();
        tesselator.end();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static void pearlTP(Level world, Player player, InteractionHand hand) {
        if(!world.isClientSide()){
            if(player instanceof ServerPlayer){
                if(((ServerPlayer) player).getLevel().dimension() == Level.NETHER){
                    ServerPlayer sPlayer = (ServerPlayer) player;
                    BlockPos spawnPos = sPlayer.getRespawnPosition();
                    ServerLevel sLevel = sPlayer.server.overworld();
                    sPlayer.changeDimension(sLevel, sLevel.getPortalForcer());
                    sPlayer.teleportTo(sLevel, spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), sPlayer.yRotO, sPlayer.xRotO);
                    if(!player.isCreative()) player.getItemInHand(hand).shrink(1);
                }
            }
        }
    }
}