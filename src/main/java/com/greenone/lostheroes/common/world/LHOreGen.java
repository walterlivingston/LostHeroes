package com.greenone.lostheroes.common.world;

//TODO FIX OREGEN
public class LHOreGen {
    public static void initOres() {
        /*Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, LHBlocks.ores.get(Metal.COPPER).getRegistryName(),
                Feature.ORE
                        .configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                                LHBlocks.ores.get(Metal.COPPER).defaultBlockState(), 9))
                        .decorated(Placement.RANGE
                                .configured(new TopSolidRangeConfig(8,
                                        8, 64)))
                        .squared().count(20));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, LHBlocks.ores.get(Metal.TIN).getRegistryName(),
                Feature.ORE
                        .configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                                LHBlocks.ores.get(Metal.TIN).defaultBlockState(), 9))
                        .decorated(Placement.RANGE
                                .configured(new TopSolidRangeConfig(8,
                                        8, 64)))
                        .squared().count(20));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, LHBlocks.ores.get(Metal.LEAD).getRegistryName(),
                Feature.ORE
                        .configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                                LHBlocks.ores.get(Metal.LEAD).defaultBlockState(), 9))
                        .decorated(Placement.RANGE
                                .configured(new TopSolidRangeConfig(0,
                                        0, 32)))
                        .squared().count(15));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, LHBlocks.ores.get(Metal.SILVER).getRegistryName(),
                Feature.ORE
                        .configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                                LHBlocks.ores.get(Metal.SILVER).defaultBlockState(), 9))
                        .decorated(Placement.RANGE
                                .configured(new TopSolidRangeConfig(0,
                                        0, 32)))
                        .squared().count(15));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, LHBlocks.stoneBlocks.get(Stone.MARBLE).getRegistryName(),
                Feature.ORE
                        .configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                                LHBlocks.stoneBlocks.get(Stone.MARBLE).defaultBlockState(), 33))
                        .decorated(Placement.RANGE
                                .configured(new TopSolidRangeConfig(0,
                                        0, 80)))
                        .squared().count(10));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, LHBlocks.storageBlocks.get(Metal.METEORIC_IRON).getRegistryName(),
                Feature.LAKE
                        .configured(new BlockStateFeatureConfig(LHBlocks.storageBlocks.get(Metal.METEORIC_IRON).defaultBlockState())).decorated(Placement.LAVA_LAKE.configured(new ChanceConfig(99))));
    */
    }

    //OreFeatureConfig(fillerBlock, blockState, veinSize)).decorated(Placement.RANGE.configure(new TopSolidRangeConfig(minY,minY, maxY))).squared().count(chunkOccurrence));

    /*public static void setupOres() {
        for (Map.Entry<RegistryKey<Biome>, Biome> biome : BuiltinRegistries.BIOME.entrySet()) {
            if(biome.getValue().getBiomeCategory().equals(Biome.Category.NETHER)){

            }
            else if(biome.getValue().getBiomeCategory().equals(Biome.Category.THEEND)){

            }else{
                addFeatureToBiome(biome.getValue(), BuiltinRegistries.CONFIGURED_FEATURE.get(LHBlocks.ores.get(Metal.COPPER).getRegistryName()));
                addFeatureToBiome(biome.getValue(), BuiltinRegistries.CONFIGURED_FEATURE.get(LHBlocks.ores.get(Metal.TIN).getRegistryName()));
                addFeatureToBiome(biome.getValue(), BuiltinRegistries.CONFIGURED_FEATURE.get(LHBlocks.ores.get(Metal.LEAD).getRegistryName()));
                addFeatureToBiome(biome.getValue(), BuiltinRegistries.CONFIGURED_FEATURE.get(LHBlocks.ores.get(Metal.SILVER).getRegistryName()));
                addFeatureToBiome(biome.getValue(), BuiltinRegistries.CONFIGURED_FEATURE.get(LHBlocks.stoneBlocks.get(Stone.MARBLE).getRegistryName()));
                addFeatureToBiome(biome.getValue(), BuiltinRegistries.CONFIGURED_FEATURE.get(LHBlocks.storageBlocks.get(Metal.METEORIC_IRON).getRegistryName()));
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
    }*/
}
