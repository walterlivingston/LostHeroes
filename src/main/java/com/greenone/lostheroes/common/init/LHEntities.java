package com.greenone.lostheroes.common.init;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.entities.GreekFireEntity;
import com.greenone.lostheroes.common.entities.SpearEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class LHEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, LostHeroes.MOD_ID);

    public static final EntityType<SpearEntity> SPEAR = EntityType.Builder.<SpearEntity>of(SpearEntity::new, EntityClassification.MISC).build(LostHeroes.MOD_ID+":spear");
    public static final EntityType<GreekFireEntity> GREEK_FIRE = EntityType.Builder.<GreekFireEntity>of(GreekFireEntity::new, EntityClassification.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build("greek_fire");

    public static void register(IEventBus eventBus){
        ENTITIES.register("spear", () -> SPEAR);
        ENTITIES.register("greek_fire", () -> GREEK_FIRE);

        ENTITIES.register(eventBus);
    }
}
