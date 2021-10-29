package com.greenone.lostheroes.client.color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;

public class LHColors {
    private static final ItemColors itemcolors = Minecraft.getInstance().getItemColors();
    private static final BlockColors blockColors = Minecraft.getInstance().getBlockColors();

    public static void registerItemColors(){
        /*itemcolors.register((p_92687_, p_92688_) -> {
            BlockState blockstate = ((BlockItem)p_92687_.getItem()).getBlock().defaultBlockState();
            return blockColors.getColor(blockstate, null, null, p_92688_);
        }, LHBlocks.grape_vine);*/
    }

    public static void registerBlockColors(){
        /*blockColors.register((p_92626_, p_92627_, p_92628_, p_92629_) ->
            p_92627_ != null && p_92628_ != null ? BiomeColors.getAverageFoliageColor(p_92627_, p_92628_) : FoliageColor.getDefaultColor(),
            LHBlocks.grape_vine);*/
    }
}
