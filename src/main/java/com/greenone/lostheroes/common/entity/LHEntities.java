package com.greenone.lostheroes.common.entity;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.entity.monster.WitherSkeletonWarrior;
import com.greenone.lostheroes.common.entity.projectile.LightRayProjectile;
import com.greenone.lostheroes.common.entity.projectile.WaterBallProjectile;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.WitherSkeletonEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class LHEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, LostHeroes.MODID);

    public static EntityType<WaterBallProjectile> WATER_BALL = EntityType.Builder.<WaterBallProjectile>of(WaterBallProjectile::new, EntityClassification.MISC).sized(0.25F, 0.25F).build(LostHeroes.MODID+":water_ball");
    public static EntityType<LightRayProjectile> LIGHT_RAY = EntityType.Builder.<LightRayProjectile>of(LightRayProjectile::new, EntityClassification.MISC).sized(0.25F, 0.25F).build(LostHeroes.MODID+":light_ray");
    public static EntityType<WitherSkeletonWarrior> WITHER_WARRIOR = EntityType.Builder.<WitherSkeletonWarrior>of(WitherSkeletonWarrior::new, EntityClassification.MONSTER).fireImmune().immuneTo(Blocks.WITHER_ROSE).sized(0.7F, 2.4F).clientTrackingRange(8).build(LostHeroes.MODID+":wither_warrior");

    public static void register(IEventBus eventBus) {
        ENTITIES.register("water_ball", () -> WATER_BALL);
        ENTITIES.register("light_ray", () -> LIGHT_RAY);
        ENTITIES.register("wither_warrior", () -> WITHER_WARRIOR);

        ENTITIES.register(eventBus);
    }

    @SubscribeEvent
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event){
        event.put(WITHER_WARRIOR, WitherSkeletonWarrior.createAttributes().build());
    }
}
