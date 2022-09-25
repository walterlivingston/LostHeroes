package com.greenone.lostheroes.common.deity.abilities;

import com.greenone.lostheroes.common.LHUtils;
import com.greenone.lostheroes.common.entity.LHEntities;
import com.greenone.lostheroes.common.entity.monster.WitherSkeletonWarrior;
import com.greenone.lostheroes.common.player.capability.IMana;
import net.minecraft.block.Block;
import net.minecraft.block.OreBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public class HadesAbilities extends AbstractAbility{
    // Resource Call
    @Override
    public void majorAbility(PlayerEntity player, IMana manaCap) {
        if(player.isCreative() || manaCap.consumeMana(majorManaReq(manaCap.getMaxMana()))) {
            Vector3d spawnVec = LHUtils.getLookingAt(player, 5).getLocation();
            WitherSkeletonWarrior warrior = new WitherSkeletonWarrior(LHEntities.WITHER_WARRIOR, player.level);
            warrior.tame(player);
            player.level.addFreshEntity(warrior);
            warrior.setPos(spawnVec.x, spawnVec.y, spawnVec.z);
            warrior.finalizeSpawn(player.getServer().getLevel(player.level.dimension()), warrior.level.getCurrentDifficultyAt(warrior.blockPosition()), SpawnReason.COMMAND, (ILivingEntityData) null, (CompoundNBT) null);
        }
    }

    // Summon Warrior
    @Override
    public void minorAbility(PlayerEntity player, IMana manaCap) {
        if(player.isCreative() || manaCap.consumeMana(minorManaReq(manaCap.getMaxMana()))){
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
    public float majorManaReq(float maxMana) {
        return maxMana;
    }

    @Override
    public float minorManaReq(float maxMana) {
        return maxMana / 2;
    }
}
