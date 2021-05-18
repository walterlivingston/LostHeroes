package com.greenone.lostheroes.common.entities;

import com.greenone.lostheroes.LostHeroes;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class LHEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, LostHeroes.MOD_ID);

    public static final EntityType<SpearEntity> SPEAR = EntityType.Builder.<SpearEntity>of(SpearEntity::new, EntityClassification.MISC).build(LostHeroes.MOD_ID+":spear");

    public static void register(IEventBus eventBus){
        ENTITIES.register("spear", () -> SPEAR);

        ENTITIES.register(eventBus);
    }
}
