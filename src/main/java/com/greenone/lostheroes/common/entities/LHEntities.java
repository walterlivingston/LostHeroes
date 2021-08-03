package com.greenone.lostheroes.common.entities;

import com.greenone.lostheroes.LostHeroes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class LHEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, LostHeroes.MOD_ID);

    public static final EntityType<SpearEntity> SPEAR = EntityType.Builder.<SpearEntity>of(SpearEntity::new, MobCategory.MISC).build(LostHeroes.MOD_ID+":spear");
    public static final EntityType<GreekFireEntity> GREEK_FIRE = EntityType.Builder.<GreekFireEntity>of(GreekFireEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build("greek_fire");

    public static void register(IEventBus eventBus){
        ENTITIES.register("spear", () -> SPEAR);
        ENTITIES.register("greek_fire", () -> GREEK_FIRE);

        ENTITIES.register(eventBus);
    }
}
