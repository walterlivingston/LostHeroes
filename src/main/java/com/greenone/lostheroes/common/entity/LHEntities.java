package com.greenone.lostheroes.common.entity;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.entity.projectile.LightRayProjectile;
import com.greenone.lostheroes.common.entity.projectile.WaterBallProjectile;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class LHEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, LostHeroes.MODID);

    public static EntityType<WaterBallProjectile> WATER_BALL = EntityType.Builder.<WaterBallProjectile>of(WaterBallProjectile::new, EntityClassification.MISC).sized(0.25F, 0.25F).build(LostHeroes.MODID+":water_ball");
    public static EntityType<LightRayProjectile> LIGHT_RAY = EntityType.Builder.<LightRayProjectile>of(LightRayProjectile::new, EntityClassification.MISC).sized(0.25F, 0.25F).build(LostHeroes.MODID+":light_ray");


    public static void register(IEventBus eventBus) {
        ENTITIES.register("water_ball", () -> WATER_BALL);
        ENTITIES.register("light_ray", () -> LIGHT_RAY);

        ENTITIES.register(eventBus);
    }
}
