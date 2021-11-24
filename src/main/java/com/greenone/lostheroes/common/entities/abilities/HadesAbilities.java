package com.greenone.lostheroes.common.entities.abilities;

import com.greenone.lostheroes.common.config.LHConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.OreBlock;

import java.util.ArrayList;
import java.util.List;

public class HadesAbilities extends AbstractAbility{

    @Override
    public void mainAbility(Player playerIn) {
        player = playerIn;
        if(player.isCreative() || playerCap().consumeMana(getMainManaReq())){
            callMinerals(player);
            success();
        }
    }

    @Override
    public void minorAbility(Player playerIn) {
        player = playerIn;
    }

    @Override
    public float getMainManaReq() {
        return LHConfig.getBaseMaxMana();
    }

    @Override
    public float getMinorManaReq() {
        return 0;
    }

    private void callMinerals(Player player) {
        Level world = player.level;
        List<OreBlock> oreList = new ArrayList<>();
        double maxY = player.getY();
        double minX = player.getX()-1;
        double maxX = player.getX()+1;
        double minZ = player.getZ()-1;
        double maxZ = player.getZ()+1;

        for(int y=0; y<=maxY; y++){
            for(int x=(int)minX; x<=maxX; x++){
                for(int z=(int)minZ; z<=maxZ; z++){
                    BlockPos pos = new BlockPos(x, y, z);
                    Block block = player.level.getBlockState(pos).getBlock();
                    if(block instanceof OreBlock){
                        oreList.add((OreBlock) block);
                        world.removeBlock(pos, false);
                    }
                }
            }
        }

        for(OreBlock block : oreList){
            ItemEntity entity = new ItemEntity(player.level, player.getX(), player.getY(), player.getZ(), new ItemStack(block.asItem()));
            player.level.addFreshEntity(entity);
        }
    }
}
