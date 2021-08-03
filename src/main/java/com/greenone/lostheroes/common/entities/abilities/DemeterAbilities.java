package com.greenone.lostheroes.common.entities.abilities;

import com.greenone.lostheroes.common.capabilities.CapabilityRegistry;
import com.greenone.lostheroes.common.capabilities.IPlayerCap;
import com.greenone.lostheroes.common.util.LHUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;

public class DemeterAbilities extends AbstractAbility{
    @Override
    public void mainAbility(Player player) {
        IPlayerCap playerCap = player.getCapability(CapabilityRegistry.PLAYERCAP, null).orElse(null);
        if(playerCap.consumeMana(getMainManaReq())) harvestAndReplant(player);
    }

    @Override
    public void minorAbility(Player player) {

    }

    @Override
    public float getMainManaReq() {
        return 2.5f;
    }

    @Override
    public float getMinorManaReq() {
        return 0;
    }

    private void harvestAndReplant(Player player) {
        for(int y = (int)(player.getY()-2); y < (player.getY()+2); y++){
            for(int x = (int)(player.getX()-4); x < (player.getX()+4); x++){
                for(int z = (int)(player.getZ()-4); z < (player.getZ()+4); z++){
                    BlockPos pos = new BlockPos(x,y,z);
                    Block block = player.level.getBlockState(pos).getBlock();
                    if(block instanceof CropBlock){
                        CropBlock crop = (CropBlock) block;
                        if(crop.isMaxAge(player.level.getBlockState(pos))){
                            player.level.destroyBlock(pos, true);
                            if(LHUtils.isItemInInventory(player, crop.getPlant(player.level, pos).getBlock().asItem())){
                                player.level.setBlock(pos, crop.defaultBlockState(), 0);
                            }
                        }
                    }
                }
            }
        }
    }
}
