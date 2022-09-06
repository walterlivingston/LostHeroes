package com.greenone.lostheroes.common.entities.abilities;

import com.greenone.lostheroes.common.capabilities.CapabilityRegistry;
import com.greenone.lostheroes.common.capabilities.IPlayerCap;
import com.greenone.lostheroes.common.util.LHUtils;
import net.minecraft.block.Block;
import net.minecraft.block.CropsBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

public class DemeterAbilities extends AbstractAbility{
    @Override
    public void mainAbility(PlayerEntity playerIn) {
        player = playerIn;
        playerCap = player.getCapability(CapabilityRegistry.PLAYERCAP, null).orElse(null);
        if(player.isCreative() | playerCap.getMana() >= getMainManaReq()) {
            harvestAndReplant(player);
            if(!player.isCreative()) success();
        }
    }

    @Override
    public void minorAbility(PlayerEntity playerIn) {
        player = playerIn;
        playerCap = player.getCapability(CapabilityRegistry.PLAYERCAP, null).orElse(null);
    }

    @Override
    public float getMainManaReq() {
        return 2.5f;
    }

    @Override
    public float getMinorManaReq() {
        return 0;
    }

    private void harvestAndReplant(PlayerEntity player) {
        boolean harvested = false;
        for(int y = (int)(player.getY()-2); y < (player.getY()+2); y++){
            for(int x = (int)(player.getX()-4); x < (player.getX()+4); x++){
                for(int z = (int)(player.getZ()-4); z < (player.getZ()+4); z++){
                    BlockPos pos = new BlockPos(x,y,z);
                    Block block = player.level.getBlockState(pos).getBlock();
                    if(block instanceof CropsBlock){
                        CropsBlock crop = (CropsBlock) block;
                        harvested = true;
                        if(crop.isMaxAge(player.level.getBlockState(pos))){
                            player.level.destroyBlock(pos, true);
                            if(LHUtils.isItemInInventory(player, crop.getPlant(player.level, pos).getBlock().asItem())){
                                player.level.setBlock(pos, crop.defaultBlockState(), 0);
                            }
                        }
                    }
                }
            }
            if(harvested && !player.isCreative()) playerCap.consumeMana(getMainManaReq());
        }
    }
}
