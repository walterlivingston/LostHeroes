package com.greenone.lostheroes.common.deity;

import com.greenone.lostheroes.common.player.capability.IMana;
import com.greenone.lostheroes.common.player.capability.IParent;
import com.greenone.lostheroes.common.player.capability.PlayerCapabilities;
import com.greenone.lostheroes.common.potion.LHEffect;
import net.minecraft.block.Block;
import net.minecraft.block.CropsBlock;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import java.util.List;
import java.util.Random;

public class Blessing extends LHEffect {
    private Deity deity = null;

    protected Blessing() {
        super(EffectType.BENEFICIAL, 0);
    }

    public void setDeity(Deity deity_) { this.deity = deity_; }

    public Deity getDeity() {
        return deity;
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int p_76394_2_) {
        PlayerEntity player = (PlayerEntity) entity;
        IParent parentCap = player.getCapability(PlayerCapabilities.PARENT_CAPABILITY).orElse(null);
        IMana manaCap = player.getCapability(PlayerCapabilities.MANA_CAPABILITY).orElse(null);
        if(getDeity() != null && parentCap != null){
            if (getDeity() == parentCap.getParent()) this.render = false;

            if (this == Blessings.ZEUS){
                player.abilities.mayfly = true;
            }
            if (this == Blessings.POSEIDON) {
                if (player.isInWater()) {
                    if (player.getHealth() < player.getMaxHealth())
                        player.heal(0.05f);
                    if (player.isUnderWater() && player.getAirSupply() < player.getMaxAirSupply())
                        player.setAirSupply(Math.min(player.getAirSupply() + 4, player.getMaxAirSupply()));
                }
            }
            if (this == Blessings.APHRODITE){
                if(!player.isSpectator() && player.isSteppingCarefully()){
                    if((this.deity == parentCap.getParent() && (player.isCreative() || manaCap.consumeMana(0.008f))) || this.deity != parentCap.getParent()) {
                        List<CreatureEntity> mobs = player.level.getNearbyEntities(CreatureEntity.class, new EntityPredicate().range(10), player, new AxisAlignedBB(player.blockPosition()).inflate(10));
                        mobs.stream().filter(mob -> !(mob instanceof MonsterEntity)).forEach(mob -> {
                            mob.getLookControl().setLookAt(mob, (float) (mob.getHeadRotSpeed() + 20), (float) mob.getHeadRotSpeed());
                            mob.getNavigation().moveTo(player, 1.25D);
                        });
                    }
                }
            }
            if (this == Blessings.DEMETER){
                if (!player.level.isClientSide) {
                    player.getFoodData().eat(p_76394_2_ + 1, 1.0F);
                }
                for(int y = (int)(player.getY()-2); y < (player.getY()+2); y++){
                    for(int x = (int)(player.getX()-4); x < (player.getX()+4); x++){
                        for(int z = (int)(player.getZ()-4); z < (player.getZ()+4); z++){
                            BlockPos pos = new BlockPos(x,y,z);
                            Block block = player.level.getBlockState(pos).getBlock();
                            if(block instanceof CropsBlock){
                                Random rand = new Random();
                                CropsBlock crop = (CropsBlock) block;
                                if(rand.nextInt(5 + 1) > 4) crop.growCrops(player.level, pos, player.level.getBlockState(pos));
                            }
                        }
                    }
                }
            }
        }
    }
}
