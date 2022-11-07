package com.greenone.lostheroes.common.deity.abilities;

import com.greenone.lostheroes.common.LHUtils;
import com.greenone.lostheroes.common.player.capability.IMana;
import net.minecraft.block.Block;
import net.minecraft.block.CropsBlock;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class DemeterAbilities extends AbstractAbility{
    @Override
    public void majorAbility(PlayerEntity player, IMana manaCap) {
        if(player.isCreative() || manaCap.consumeMana(majorManaReq(manaCap.getMaxMana()))){
            List<LivingEntity> list = player.level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(10.0d, 2.0D, 10.0d));
            AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(player.level, player.getX(), player.getY(), player.getZ());
            areaeffectcloudentity.setParticle(ParticleTypes.SNEEZE);
            areaeffectcloudentity.setRadiusOnUse(0f);
            areaeffectcloudentity.setWaitTime(0);
            areaeffectcloudentity.setDuration(5);
            areaeffectcloudentity.setRadiusPerTick(1.0f);

            if(!list.isEmpty()){
                for(LivingEntity entity : list){
                    if(entity != player){
                        entity.addEffect(new EffectInstance(Effects.HUNGER, 600));
                        entity.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 600));
                        //entity.addEffect(new EffectInstance(Effects.HARM, 600));
                    }
                }
            }
            player.level.addFreshEntity(areaeffectcloudentity);
        }
    }

    @Override
    public void minorAbility(PlayerEntity player, IMana manaCap) {
        if(player.isCreative() || manaCap.consumeMana(minorManaReq(manaCap.getMaxMana()))) {
            for (int y = (int) (player.getY() - 5); y < (player.getY() + 5); y++) {
                for (int x = (int) (player.getX() - 5); x < (player.getX() + 5); x++) {
                    for (int z = (int) (player.getZ() - 5); z < (player.getZ() + 5); z++) {
                        BlockPos pos = new BlockPos(x, y, z);
                        Block block = player.level.getBlockState(pos).getBlock();
                        if (block instanceof CropsBlock) {
                            CropsBlock crop = (CropsBlock) block;
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

    @Override
    public float majorManaReq(float maxMana) {
        return maxMana / 2;
    }

    @Override
    public float minorManaReq(float maxMana) {
        return maxMana / 4;
    }
}
