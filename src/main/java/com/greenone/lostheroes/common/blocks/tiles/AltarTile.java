package com.greenone.lostheroes.common.blocks.tiles;

import com.greenone.lostheroes.common.capabilities.CapabilityRegistry;
import com.greenone.lostheroes.common.capabilities.IPlayerCap;
import com.greenone.lostheroes.common.init.Deities;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class AltarTile extends TileEntity implements ITickableTileEntity {
    private int cooldown = 0;

    public AltarTile() {
        super(LHTileEntities.ALTAR);
    }

    @Override
    public void tick() {
        if(cooldown > 0) cooldown--;
    }

    private void addEffectsToPlayers(Effect primaryEffect, PlayerEntity player){
        player.addEffect(new EffectInstance(primaryEffect, 1800));
    }

    public void addItem(ItemStack item, PlayerEntity player){
        boolean flag1 = Deities.getDeity(item.getItem())==Deities.APOLLO && player.getCommandSenderWorld().isDay();
        boolean flag2 = Deities.getDeity(item.getItem())==Deities.ARTEMIS && player.getCommandSenderWorld().isNight();
        boolean flag3 = !(Deities.getDeity(item.getItem())==Deities.APOLLO) && !(Deities.getDeity(item.getItem())==Deities.ARTEMIS);
        if(flag1 || flag2 || flag3){
            addEffectsToPlayers(Deities.getDeity(item.getItem()).getBlessing(), player);
            player.getCommandSenderWorld().setBlock(this.getBlockPos().above(), Blocks.FIRE.defaultBlockState(), 2);
            if(!player.isCreative()) item.shrink(1);
            resetCooldown();
            IPlayerCap playerCap = player.getCapability(CapabilityRegistry.PLAYERCAP, null).orElse(null);
            if(playerCap!=null) playerCap.addExperience(player, 50);
        }
    }

    public boolean checkCooldown(){
        return cooldown==0;
    }

    private void resetCooldown() {
        cooldown = 36000;
    }
}
