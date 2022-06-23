package com.greenone.lostheroes.common.world;

import com.google.common.collect.Lists;
import com.greenone.lostheroes.common.init.LHBlocks;
import com.greenone.lostheroes.common.enums.Metal;
import com.greenone.lostheroes.common.enums.Stone;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class LHOreGen {
    public static void initOres() {
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, LHBlocks.ores.get(Metal.COPPER).getRegistryName(),
                Feature.ORE
                        .configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                                LHBlocks.ores.get(Metal.COPPER).defaultBlockState(), 9))
                        .decorated(Placement.RANGE
                                .configured(new TopSolidRangeConfig(8,
                                        8, 64)))
                        .squared().count(20));
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, LHBlocks.ores.get(Metal.TIN).getRegistryName(),
                Feature.ORE
                        .configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                                LHBlocks.ores.get(Metal.TIN).defaultBlockState(), 9))
                        .decorated(Placement.RANGE
                                .configured(new TopSolidRangeConfig(8,
                                        8, 64)))
                        .squared().count(20));
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, LHBlocks.ores.get(Metal.LEAD).getRegistryName(),
                Feature.ORE
                        .configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                                LHBlocks.ores.get(Metal.LEAD).defaultBlockState(), 9))
                        .decorated(Placement.RANGE
                                .configured(new TopSolidRangeConfig(0,
                                        0, 32)))
                        .squared().count(15));
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, LHBlocks.ores.get(Metal.SILVER).getRegistryName(),
                Feature.ORE
                        .configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                                LHBlocks.ores.get(Metal.SILVER).defaultBlockState(), 9))
                        .decorated(Placement.RANGE
                                .configured(new TopSolidRangeConfig(0,
                                        0, 32)))
                        .squared().count(15));
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, LHBlocks.stoneBlocks.get(Stone.MARBLE).getRegistryName(),
                Feature.ORE
                        .configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                                LHBlocks.stoneBlocks.get(Stone.MARBLE).defaultBlockState(), 33))
                        .decorated(Placement.RANGE
                                .configured(new TopSolidRangeConfig(0,
                                        0, 80)))
                        .squared().count(10));
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, LHBlocks.storageBlocks.get(Metal.METEORIC_IRON).getRegistryName(),
                Feature.LAKE
                        .configured(new BlockStateFeatureConfig(LHBlocks.storageBlocks.get(Metal.METEORIC_IRON).defaultBlockState())).decorated(Placement.LAVA_LAKE.configured(new ChanceConfig(99))));
    }

    //OreFeatureConfig(fillerBlock, blockState, veinSize)).decorated(Placement.RANGE.configure(new TopSolidRangeConfig(minY,minY, maxY))).squared().count(chunkOccurrence));

    public static void setupOres() {
        for (Map.Entry<RegistryKey<Biome>, Biome> biome : WorldGenRegistries.BIOME.entrySet()) {
            if(biome.getValue().getBiomeCategory().equals(Biome.Category.NETHER)){

            }
            else if(biome.getValue().getBiomeCategory().equals(Biome.Category.THEEND)){

            }else{
                addFeatureToBiome(biome.getValue(), WorldGenRegistries.CONFIGURED_FEATURE.get(LHBlocks.ores.get(Metal.COPPER).getRegistryName()));
                addFeatureToBiome(biome.getValue(), WorldGenRegistries.CONFIGURED_FEATURE.get(LHBlocks.ores.get(Metal.TIN).getRegistryName()));
                addFeatureToBiome(biome.getValue(), WorldGenRegistries.CONFIGURED_FEATURE.get(LHBlocks.ores.get(Metal.LEAD).getRegistryName()));
                addFeatureToBiome(biome.getValue(), WorldGenRegistries.CONFIGURED_FEATURE.get(LHBlocks.ores.get(Metal.SILVER).getRegistryName()));
                addFeatureToBiome(biome.getValue(), WorldGenRegistries.CONFIGURED_FEATURE.get(LHBlocks.stoneBlocks.get(Stone.MARBLE).getRegistryName()));
                addFeatureToBiome(biome.getValue(), WorldGenRegistries.CONFIGURED_FEATURE.get(LHBlocks.storageBlocks.get(Metal.METEORIC_IRON).getRegistryName()));
            }
        }
    }

    public static void addFeatureToBiome(Biome biome, ConfiguredFeature<?, ?> configuredFeature) {
        GenerationStage.Decoration decoration = GenerationStage.Decoration.UNDERGROUND_ORES;
        List<List<Supplier<ConfiguredFeature<?, ?>>>> biomeFeatures = new ArrayList<>(biome.getGenerationSettings().features());
        while (biomeFeatures.size() <= decoration.ordinal()) {
            biomeFeatures.add(Lists.newArrayList());
        }
        List<Supplier<ConfiguredFeature<?, ?>>> features = new ArrayList<>(biomeFeatures.get(decoration.ordinal()));
        features.add(() -> configuredFeature);
        biomeFeatures.set(decoration.ordinal(), features);

        ObfuscationReflectionHelper.setPrivateValue(BiomeGenerationSettings.class, biome.getGenerationSettings(), biomeFeatures, "field_242484_f");
    }
}
