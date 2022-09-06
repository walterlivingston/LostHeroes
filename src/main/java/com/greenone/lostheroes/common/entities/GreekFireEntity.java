package com.greenone.lostheroes.common.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.Random;

public class GreekFireEntity extends PotionEntity {
    private int modifier=3;
    private boolean isExplosive=false;

    public GreekFireEntity(EntityType<? extends PotionEntity> type, World world) {
        super(type, world);
    }

    public GreekFireEntity(World world, LivingEntity thrower, int modifierIn, boolean isExplosiveIn) {
        super(world, thrower);
        this.modifier = modifierIn;
        this.isExplosive = isExplosiveIn;
    }

    public GreekFireEntity(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    /*@Override
    protected void onHit(RayTraceResult trace) {
        if(!this.getCommandSenderWorld().isClientSide()){
            Entity entity = this.getOwner();
            if(entity == null || !(entity instanceof MobEntity) || this.getCommandSenderWorld().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) || ForgeEventFactory.getMobGriefingEvent(this.getCommandSenderWorld(), this.getEntity())){
                if(isExplosive && this.getCommandSenderWorld().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)){
                    this.getCommandSenderWorld().explode(this, this.getX(), this.getY(), this.getZ(), modifier, false, Explosion.Mode.BREAK);
                    this.remove();
                }
                Random rand = new Random();
                for(int y = -modifier; y <= modifier; y++){
                    for(int x = -modifier; x <= modifier; x++){
                        for(int z = -modifier; z <= modifier; z++){
                            if(rand.nextBoolean() && rand.nextBoolean()) {
                                BlockPos blockPos = new BlockPos(trace.getLocation().x + x, trace.getLocation().y + y, trace.getLocation().z + z);
                                if (this.getCommandSenderWorld().getBlockState(blockPos).isAir()) {
                                    this.getCommandSenderWorld().setBlock(blockPos, LHBlocks.greek_fire.defaultBlockState(), 1);
                                }
                            }
                        }
                    }
                }
            }
        }
    }*/

    @Override
    protected void onHitBlock(BlockRayTraceResult trace) {
        if(!this.getCommandSenderWorld().isClientSide()){
            Entity entity = this.getOwner();
            if(entity == null || !(entity instanceof MobEntity) || this.getCommandSenderWorld().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) || ForgeEventFactory.getMobGriefingEvent(this.getCommandSenderWorld(), this.getEntity())){
                if(isExplosive && this.getCommandSenderWorld().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)){
                    this.getCommandSenderWorld().explode(this, this.getX(), this.getY(), this.getZ(), modifier, false, Explosion.Mode.BREAK);
                    this.remove();
                }
                Random rand = new Random();
                for(int y = -modifier; y <= modifier; y++){
                    for(int x = -modifier; x <= modifier; x++){
                        for(int z = -modifier; z <= modifier; z++){
                            //if(rand.nextBoolean() && rand.nextBoolean()) {
                                BlockPos blockPos = new BlockPos(trace.getLocation().x + x, trace.getLocation().y + y, trace.getLocation().z + z);
                                if (this.getCommandSenderWorld().getBlockState(blockPos).isAir()) {
                                    //this.getCommandSenderWorld().setBlock(blockPos, LHBlocks.greek_fire.defaultBlockState(), 2);
                                }
                            //}
                        }
                    }
                }
            }
        }
    }
}
