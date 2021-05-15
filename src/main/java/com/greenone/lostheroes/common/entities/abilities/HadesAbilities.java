package com.greenone.lostheroes.common.entities.abilities;

import com.greenone.lostheroes.common.capabilities.CapabilityRegistry;
import com.greenone.lostheroes.common.capabilities.IPlayerCap;
import net.minecraft.block.Block;
import net.minecraft.block.OreBlock;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class HadesAbilities extends AbstractAbility{
    @Override
    public void mainAbility(PlayerEntity player) {
        System.out.println("HADES");
        IPlayerCap playerCap = player.getCapability(CapabilityRegistry.PLAYERCAP, null).orElse(null);
        if(player.isCreative() || playerCap.consumeMana(playerCap.getMaxMana())){
            callMinerals(player);
        }
    }

    @Override
    public void minorAbility(PlayerEntity player) {

    }

    private void callMinerals(PlayerEntity player) {
        World world = player.level;
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
