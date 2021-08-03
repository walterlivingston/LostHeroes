package com.greenone.lostheroes.common.world;

import com.greenone.lostheroes.LostHeroes;
import net.minecraft.data.worldgen.Features;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class LHFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, LostHeroes.MOD_ID);

    public static final Feature<?> LOTUS_FLOWER_CLUSTER = Feature.FLOWER.configured(Features.Configs.DEFAULT_FLOWER_CONFIG).decorated(Features.Decorators.ADD_32).decorated(Features.Decorators.HEIGHTMAP_SQUARE).count(4).feature;

    public static void register(IEventBus eventBus){
        FEATURES.register("lotus_flower_cluster", () -> LOTUS_FLOWER_CLUSTER);

        FEATURES.register(eventBus);
    }
}
