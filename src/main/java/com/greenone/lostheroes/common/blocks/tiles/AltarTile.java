package com.greenone.lostheroes.common.blocks.tiles;

import com.greenone.lostheroes.common.blocks.LHBlocks;
import com.greenone.lostheroes.common.blocks.PillarBlock;
import com.greenone.lostheroes.common.init.Deities;
import com.greenone.lostheroes.common.items.LHItems;
import com.greenone.lostheroes.common.util.pattern.Altar;
import com.greenone.lostheroes.common.util.pattern.BlackAltar;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;

public class AltarTile extends TileEntity implements ITickableTileEntity {
    private int cooldown = 0;

    public AltarTile() {
        super(LHTileEntities.ALTAR);
    }

    @Override
    public void tick() {
        Altar altar = new Altar();
        BlackAltar blackAltar = new BlackAltar();
        if((altar.checkPattern(level, getBlockPos())||blackAltar.checkPattern(level, getBlockPos())) && !getBlockState().getValue(PillarBlock.IS_ALTAR)){
            level.setBlock(getBlockPos(), getBlockState().setValue(PillarBlock.IS_ALTAR, true), 2);
        }
        if(cooldown > 0) cooldown--;
    }

    private void addEffectsToPlayers(Effect primaryEffect, PlayerEntity player){
        player.addEffect(new EffectInstance(primaryEffect, 1200));
    }

    public void addItem(ItemStack item, PlayerEntity player){
        boolean flag1 = Deities.getDeity(item.getItem())==Deities.APOLLO && player.getCommandSenderWorld().isDay();
        boolean flag2 = Deities.getDeity(item.getItem())==Deities.ARTEMIS && player.getCommandSenderWorld().isNight();
        boolean flag3 = !(Deities.getDeity(item.getItem())==Deities.APOLLO) && !(Deities.getDeity(item.getItem())==Deities.ARTEMIS);
        if(flag1 || flag2 || flag3){
            player.getCommandSenderWorld().setBlock(this.getBlockPos().above(), Blocks.FIRE.defaultBlockState(), 2);
            addEffectsToPlayers(Deities.getDeity(item.getItem()).getBlessing(), player);
            if(!player.isCreative()) item.shrink(1);
            resetCooldown();
        }
    }

    public boolean checkCooldown(){
        return cooldown==0;
    }

    private void resetCooldown() {
        cooldown = 36000;
    }
}
