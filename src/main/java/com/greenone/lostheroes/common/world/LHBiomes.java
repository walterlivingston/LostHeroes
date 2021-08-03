package com.greenone.lostheroes.common.world;

import com.greenone.lostheroes.LostHeroes;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class LHBiomes {
    public static final ResourceKey<Biome> LOTUS = ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(LostHeroes.MOD_ID, "lotus"));
}
