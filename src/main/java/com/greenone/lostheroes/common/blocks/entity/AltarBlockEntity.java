package com.greenone.lostheroes.common.blocks.entity;

import com.greenone.lostheroes.common.blocks.PillarBlock;
import com.greenone.lostheroes.common.init.Deities;
import com.greenone.lostheroes.common.util.pattern.Altar;
import com.greenone.lostheroes.common.util.pattern.BlackAltar;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AltarBlockEntity extends BlockEntity {
    private static int cooldown = 0;

    public AltarBlockEntity(BlockPos p_155088_, BlockState p_155089_) {
        super(LHBlockEntities.ALTAR, p_155088_, p_155089_);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, AltarBlockEntity tile) {
        Altar altar = new Altar();
        BlackAltar blackAltar = new BlackAltar();
        if((altar.checkPattern(level, pos)||blackAltar.checkPattern(level, pos)) && !state.getValue(PillarBlock.IS_ALTAR)){
            level.setBlock(pos, state.setValue(PillarBlock.IS_ALTAR, true), 2);
        }
        if(cooldown > 0) cooldown--;
    }

    private void addEffectsToPlayers(MobEffect primaryEffect, Player player){
        player.addEffect(new MobEffectInstance(primaryEffect, 1200));
    }

    public void addItem(ItemStack item, Player player){
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
