package com.greenone.lostheroes.common.deity.abilities;

import com.greenone.lostheroes.common.player.capability.IMana;
import net.minecraft.block.Block;
import net.minecraft.block.OreBlock;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class HadesAbilities extends AbstractAbility{
    // Resource Call
    @Override
    public void majorAbility(PlayerEntity player, IMana manaCap) {
        if(player.isCreative() || manaCap.consumeMana(majorManaReq(manaCap.getMaxMana()))){
            for(int y = 0; y <= player.getY(); y++){
                for(int x = (int) (player.getX()-1); x <= (player.getX()+1); x++){
                    for(int z= (int) (player.getZ()-1); z <= (player.getZ()+1); z++){
                        BlockPos pos = new BlockPos(x, y, z);
                        Block block = player.level.getBlockState(pos).getBlock();

                        if(block instanceof OreBlock){
                            player.level.removeBlock(pos, false); // Remove ore from world

                            // Give ore to player
                            ItemEntity entity = new ItemEntity(player.level, player.getX(), player.getY(), player.getZ(), new ItemStack(block.asItem()));
                            player.level.addFreshEntity(entity);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void minorAbility(PlayerEntity player, IMana manaCap) {

    }

    @Override
    public float majorManaReq(float maxMana) {
        return maxMana;
    }

    @Override
    public float minorManaReq(float maxMana) {
        return 0;
    }
}
