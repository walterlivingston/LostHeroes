package com.greenone.lostheroes.common;

import com.greenone.lostheroes.common.init.Blessings;
import com.greenone.lostheroes.common.potions.LHEffect;
import com.greenone.lostheroes.common.init.LHEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.Random;

public class Blessing extends LHEffect {
    private int d=0;

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
        if(entityLivingBaseIn instanceof Player){
            Player player = (Player) entityLivingBaseIn;
            if(!(player.isCreative() || entityLivingBaseIn.isSpectator())){
                if(this == Blessings.ZEUS){
                    player.getAbilities().mayfly = true;
                    player.onUpdateAbilities();
                }
                if(this == Blessings.POSEIDON){
                    player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 50, 1, false, false, false, new MobEffectInstance(MobEffects.WATER_BREATHING)));
                }
                if(this == Blessings.APHRODITE){
                    AABB aabb = (new AABB(player.blockPosition())).inflate(10).expandTowards(0.0D, (double)player.level.getHeight(), 0.0D);
                    List<AgeableMob> mobs = player.level.getEntitiesOfClass(AgeableMob.class, aabb);
                    if(!mobs.isEmpty()){
                        mobs.forEach(mob -> {
                            mob.getLookControl().setLookAt(player, mob.getHeadRotSpeed()+20, mob.getHeadRotSpeed()+20);
                            mob.getNavigation().moveTo(player, 1.25D);
                        });
                    }
                }
                if(this == Blessings.DIONYSUS){
                    if(new Random().nextFloat() <= 0.05) player.getFoodData().eat(amplifier + 1, 1.0F);
                }
                if(this == Blessings.DEMETER){
                    if(d==100){
                        for(int y = (int)(player.getY()-2); y < (player.getY()+2); y++){
                            for(int x = (int)(player.getX()-4); x < (player.getX()+4); x++){
                                for(int z = (int)(player.getZ()-4); z < (player.getZ()+4); z++){
                                    BlockPos pos = new BlockPos(x,y,z);
                                    Block block = player.level.getBlockState(pos).getBlock();
                                    if(block instanceof CropBlock){
                                        CropBlock crop = (CropBlock) block;
                                        crop.growCrops(player.level, pos, player.level.getBlockState(pos));
                                        d=0;
                                    }
                                }
                            }
                        }
                    }
                    d++;
                }
                if(this == Blessings.HEPHAESTUS){
                    player.addEffect(new MobEffectInstance(LHEffects.FIRE_RESISTANCE, 30, 1, false, false, false, null));
                }
            }
        }
    }
}
