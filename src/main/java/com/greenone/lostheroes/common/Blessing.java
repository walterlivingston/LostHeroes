package com.greenone.lostheroes.common;

import com.greenone.lostheroes.common.init.Blessings;
import com.greenone.lostheroes.common.potions.LHEffect;
import com.greenone.lostheroes.common.potions.LHEffects;
import net.minecraft.block.Block;
import net.minecraft.block.CropsBlock;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class Blessing extends LHEffect {
    private int d=0;

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
        if(entityLivingBaseIn instanceof PlayerEntity){
            PlayerEntity player = (PlayerEntity) entityLivingBaseIn;
            if(!(player.isCreative() || entityLivingBaseIn.isSpectator())){
                if(this == Blessings.ZEUS){
                    player.abilities.mayfly = true;
                }
                if(this == Blessings.POSEIDON){
                    player.addEffect(new EffectInstance(Effects.WATER_BREATHING, 50, 1, false, false, false, new EffectInstance(Effects.WATER_BREATHING)));
                }
                if(this == Blessings.APHRODITE){
                    List<AgeableEntity> mobs = player.getCommandSenderWorld().getNearbyEntities(AgeableEntity.class, new EntityPredicate().range(10.0D), player, new AxisAlignedBB(player.blockPosition()).inflate(10));
                    if(!mobs.isEmpty()){
                        mobs.forEach(mob -> {
                            mob.getLookControl().setLookAt(player, mob.getHeadRotSpeed()+20, mob.getHeadRotSpeed()+20);
                            mob.getNavigation().moveTo(player, 1.25D);
                        });
                    }
                }
                if(this == Blessings.DIONYSUS){
                    if(player.getFoodData().needsFood()) player.getFoodData().setFoodLevel(player.getFoodData().getFoodLevel()+1);
                    player.getFoodData().setSaturation(1.0F);
                }
                if(this == Blessings.DEMETER){
                    if(d==100){
                        for(int y = (int)(player.getY()-2); y < (player.getY()+2); y++){
                            for(int x = (int)(player.getX()-4); x < (player.getX()+4); x++){
                                for(int z = (int)(player.getZ()-4); z < (player.getZ()+4); z++){
                                    BlockPos pos = new BlockPos(x,y,z);
                                    Block block = player.getCommandSenderWorld().getBlockState(pos).getBlock();
                                    if(block instanceof CropsBlock){
                                        CropsBlock crop = (CropsBlock) block;
                                        crop.growCrops(player.getCommandSenderWorld(), pos, player.getCommandSenderWorld().getBlockState(pos));
                                        d=0;
                                    }
                                }
                            }
                        }
                    }
                    d++;
                }
                if(this == Blessings.HEPHAESTUS){
                    player.addEffect(new EffectInstance(LHEffects.FIRE_RESISTANCE, 30, 1, false, false, false, null));
                }
            }
        }
    }
}
