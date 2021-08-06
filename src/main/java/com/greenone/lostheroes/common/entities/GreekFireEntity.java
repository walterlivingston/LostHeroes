package com.greenone.lostheroes.common.entities;

import com.google.common.collect.Sets;
import com.greenone.lostheroes.common.blocks.GreekFireBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.Set;

public class GreekFireEntity extends ThrownPotion {
    private int modifier=3;
    private boolean isExplosive=false;

    public GreekFireEntity(EntityType<? extends ThrownPotion> type, Level world) {
        super(type, world);
    }

    public GreekFireEntity(Level world, LivingEntity thrower, int modifierIn, boolean isExplosiveIn) {
        super(world, thrower);
        this.modifier = modifierIn;
        this.isExplosive = isExplosiveIn;
    }

    public GreekFireEntity(Level world, double x, double y, double z) {
        super(world, x, y, z);
    }

    @Override
    protected void onHit(HitResult trace) {
        if(!this.level.isClientSide()){
            Entity entity = this.getOwner();
            if(entity == null || !(entity instanceof Mob) || this.level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) || ForgeEventFactory.getMobGriefingEvent(this.level, this.getOwner())){
                if(isExplosive && this.level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)){
                    this.level.explode(this, this.getX(), this.getY(), this.getZ(), modifier, false, Explosion.BlockInteraction.BREAK);
                    this.discard();
                }
                for(BlockPos blockpos2 : getBurnSet(new BlockPos(trace.getLocation()), modifier)) {
                    if (this.random.nextInt(3) == 0 && (this.level.getBlockState(blockpos2).isAir() || this.level.getBlockState(blockpos2).is(Blocks.WATER)) && this.level.getBlockState(blockpos2.below()).isSolidRender(this.level, blockpos2.below())) {
                        this.level.setBlockAndUpdate(blockpos2, GreekFireBlock.getState(this.level, blockpos2));
                    }
                }
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult trace) {
        if(!this.level.isClientSide()){
            Entity entity = this.getOwner();
            if(entity == null || !(entity instanceof Mob) || this.level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) || ForgeEventFactory.getMobGriefingEvent(this.level, this.getOwner())){
                if(isExplosive && this.level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)){
                    this.level.explode(this, this.getX(), this.getY(), this.getZ(), modifier, false, Explosion.BlockInteraction.BREAK);
                    this.discard();
                }

                for(BlockPos blockpos2 : getBurnSet(trace.getBlockPos(), modifier)) {
                    if (this.random.nextInt(3) == 0 && (this.level.getBlockState(blockpos2).isAir() || this.level.getBlockState(blockpos2).is(Blocks.WATER)) && this.level.getBlockState(blockpos2.below()).isSolidRender(this.level, blockpos2.below())) {
                        this.level.setBlockAndUpdate(blockpos2, GreekFireBlock.getState(this.level, blockpos2));
                    }
                }
            }
        }
    }

    public Set<BlockPos> getBurnSet(BlockPos pos, int radius){
        Set<BlockPos> ret = Sets.newHashSet();
        for(int y = -radius; y <= radius; y++){
            for(int x = -radius; x <= radius; x++){
                for(int z = -radius; z <= radius; z++){
                    ret.add(new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z));
                }
            }
        }
        return ret;
    }
}
