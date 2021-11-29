package com.greenone.lostheroes.common.world.level.biome;

import com.greenone.lostheroes.LostHeroes;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class LHBiomes {
    public static final ResourceKey<Biome> ASPHODEL_FIELDS = register("asphodel_fields");

    public static void registerSources(){
        Registry.register(Registry.BIOME_SOURCE, "underworld", UnderworldBiomeSource.CODEC);
    }

    private static ResourceKey<Biome> register(String name){
        return ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(LostHeroes.MOD_ID, name));
    }
}
