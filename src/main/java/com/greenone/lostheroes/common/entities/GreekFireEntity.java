package com.greenone.lostheroes.common.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.Random;

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

    /*@Override
    protected void onHit(RayTraceResult trace) {
        if(!this.getCommandSenderLevel().isClientSide()){
            Entity entity = this.getOwner();
            if(entity == null || !(entity instanceof MobEntity) || this.getCommandSenderLevel().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) || ForgeEventFactory.getMobGriefingEvent(this.getCommandSenderLevel(), this.getEntity())){
                if(isExplosive && this.getCommandSenderLevel().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)){
                    this.getCommandSenderLevel().explode(this, this.getX(), this.getY(), this.getZ(), modifier, false, Explosion.Mode.BREAK);
                    this.remove();
                }
                Random rand = new Random();
                for(int y = -modifier; y <= modifier; y++){
                    for(int x = -modifier; x <= modifier; x++){
                        for(int z = -modifier; z <= modifier; z++){
                            if(rand.nextBoolean() && rand.nextBoolean()) {
                                BlockPos blockPos = new BlockPos(trace.getLocation().x + x, trace.getLocation().y + y, trace.getLocation().z + z);
                                if (this.getCommandSenderLevel().getBlockState(blockPos).isAir()) {
                                    this.getCommandSenderLevel().setBlock(blockPos, LHBlocks.greek_fire.defaultBlockState(), 1);
                                }
                            }
                        }
                    }
                }
            }
        }
    }*/

    @Override
    protected void onHitBlock(BlockHitResult trace) {
        if(!this.level.isClientSide()){
            Entity entity = this.getOwner();
            if(entity == null || !(entity instanceof Mob) || this.level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) || ForgeEventFactory.getMobGriefingEvent(this.level, this.getOwner())){
                if(isExplosive && this.level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)){
                    this.level.explode(this, this.getX(), this.getY(), this.getZ(), modifier, false, Explosion.BlockInteraction.BREAK);
                    this.remove(false);
                }
                Random rand = new Random();
                for(int y = -modifier; y <= modifier; y++){
                    for(int x = -modifier; x <= modifier; x++){
                        for(int z = -modifier; z <= modifier; z++){
                            //if(rand.nextBoolean() && rand.nextBoolean()) {
                                BlockPos blockPos = new BlockPos(trace.getLocation().x + x, trace.getLocation().y + y, trace.getLocation().z + z);
                                if (this.level.getBlockState(blockPos).isAir()) {
                                    //this.getCommandSenderLevel().setBlock(blockPos, LHBlocks.greek_fire.defaultBlockState(), 2);
                                }
                            //}
                        }
                    }
                }
            }
        }
    }
}
