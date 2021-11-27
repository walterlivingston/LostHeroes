package com.greenone.lostheroes.common.world;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.enums.Metal;
import com.greenone.lostheroes.common.enums.Stone;
import com.greenone.lostheroes.common.enums.Wood;
import com.greenone.lostheroes.common.init.LHBlocks;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.Features;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.BendingTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;
import net.minecraft.world.level.levelgen.placement.FrequencyWithExtraChanceDecoratorConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class LHFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, LostHeroes.MOD_ID);

    public static final ImmutableList<OreConfiguration.TargetBlockState> ORE_TIN_TARGET_LIST = ImmutableList.of(OreConfiguration.target(OreConfiguration.Predicates.STONE_ORE_REPLACEABLES, States.TIN_ORE), OreConfiguration.target(OreConfiguration.Predicates.DEEPSLATE_ORE_REPLACEABLES, States.TIN_ORE));
    public static final ImmutableList<OreConfiguration.TargetBlockState> ORE_LEAD_TARGET_LIST = ImmutableList.of(OreConfiguration.target(OreConfiguration.Predicates.STONE_ORE_REPLACEABLES, States.LEAD_ORE), OreConfiguration.target(OreConfiguration.Predicates.DEEPSLATE_ORE_REPLACEABLES, States.LEAD_ORE));
    public static final ImmutableList<OreConfiguration.TargetBlockState> ORE_SILVER_TARGET_LIST = ImmutableList.of(OreConfiguration.target(OreConfiguration.Predicates.STONE_ORE_REPLACEABLES, States.SILVER_ORE), OreConfiguration.target(OreConfiguration.Predicates.DEEPSLATE_ORE_REPLACEABLES, States.SILVER_ORE));

    public static final ConfiguredFeature<?, ?> ORE_TIN = Feature.ORE.configured(new OreConfiguration(ORE_TIN_TARGET_LIST, 10)).rangeUniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(63)).squared().count(8);
    public static final ConfiguredFeature<?, ?> ORE_LEAD = Feature.ORE.configured(new OreConfiguration(ORE_LEAD_TARGET_LIST, 9)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(31)).squared().count(2);
    public static final ConfiguredFeature<?, ?> ORE_SILVER = Feature.ORE.configured(new OreConfiguration(ORE_SILVER_TARGET_LIST, 9)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(31)).squared().count(2);
    public static final ConfiguredFeature<?, ?> METEORIC_IRON = Feature.LAKE.configured(new BlockStateConfiguration(States.METEORIC_IRON)).range(Features.Decorators.FULL_RANGE).squared().rarity(100);
    public static final ConfiguredFeature<?, ?> ORE_MARBLE = Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, States.MARBLE, 33)).rangeUniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(79)).squared().count(10);
    public static final ConfiguredFeature<?, ?> ORE_BLACK_MARBLE = Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, States.BLACK_MARBLE, 33)).rangeUniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(79)).squared().count(10);

    //public static final ConfiguredFeature<TreeConfiguration, ?> OLIVE = Feature.TREE.configured((new TreeConfiguration.TreeConfigurationBuilder(new SimpleStateProvider(States.OLIVE_LOG), new StraightTrunkPlacer(4, 2, 0), new SimpleStateProvider(States.OLIVE_LEAVES), new SimpleStateProvider(States.OLIVE_SAPLING), new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3), new TwoLayersFeatureSize(1, 0, 1))).ignoreVines().build());
    public static final ConfiguredFeature<TreeConfiguration, ?> POMEGRANATE = Feature.TREE.configured((new TreeConfiguration.TreeConfigurationBuilder(new SimpleStateProvider(States.POMEGRANATE_LOG), new BendingTrunkPlacer(4, 2, 0, 3, UniformInt.of(1, 2)), new SimpleStateProvider(States.POMEGRANATE_LEAVES), new SimpleStateProvider(States.POMEGRANATE_SAPLING), new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3), new TwoLayersFeatureSize(1, 0, 1))).ignoreVines().build());

    //public static final ConfiguredFeature<?, ?> OLIVE_TREE_FEATURE = Feature.RANDOM_SELECTOR.configured(new RandomFeatureConfiguration(ImmutableList.of(OLIVE.weighted(0.33333334F)), OLIVE)).decorated(Features.Decorators.HEIGHTMAP_WITH_TREE_THRESHOLD_SQUARED).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(0, 0.05F, 1)));
    public static final ConfiguredFeature<?, ?> POMEGRANATE_TREE_FEATURE = Feature.RANDOM_SELECTOR.configured(new RandomFeatureConfiguration(ImmutableList.of(POMEGRANATE.weighted(0.33333334F)), POMEGRANATE)).decorated(Features.Decorators.HEIGHTMAP_WITH_TREE_THRESHOLD_SQUARED).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(0, 0.05F, 1)));

    //public static final Feature<?> LOTUS_FLOWER_CLUSTER = Feature.FLOWER.configured(Features.Configs.DEFAULT_FLOWER_CONFIG).decorated(Features.Decorators.ADD_32).decorated(Features.Decorators.HEIGHTMAP_SQUARE).count(4).feature;

    public static void register(IEventBus eventBus){
        //FEATURES.register("lotus_flower_cluster", () -> LOTUS_FLOWER_CLUSTER);

        FEATURES.register(eventBus);
    }

    public static void initFeatures(){
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, LHBlocks.ores.get(Metal.TIN).getRegistryName(), ORE_TIN);
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, LHBlocks.ores.get(Metal.LEAD).getRegistryName(), ORE_LEAD);
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, LHBlocks.ores.get(Metal.SILVER).getRegistryName(), ORE_SILVER);
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, LHBlocks.storageBlocks.get(Metal.METEORIC_IRON).getRegistryName(), METEORIC_IRON);
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, LHBlocks.stoneBlocks.get(Stone.MARBLE).getRegistryName(), ORE_MARBLE);
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, LHBlocks.stoneBlocks.get(Stone.BLACK_MARBLE).getRegistryName(), ORE_BLACK_MARBLE);
    }

    public static void setupFeatures() {
        for (Map.Entry<ResourceKey<Biome>, Biome> biome : BuiltinRegistries.BIOME.entrySet()) {
            if(biome.getValue().getBiomeCategory().equals(Biome.BiomeCategory.NETHER)){

            }
            else if(biome.getValue().getBiomeCategory().equals(Biome.BiomeCategory.THEEND)){

            }else{
                addFeatureToBiome(biome.getValue(), BuiltinRegistries.CONFIGURED_FEATURE.get(LHBlocks.ores.get(Metal.TIN).getRegistryName()), GenerationStep.Decoration.UNDERGROUND_ORES);
                addFeatureToBiome(biome.getValue(), BuiltinRegistries.CONFIGURED_FEATURE.get(LHBlocks.ores.get(Metal.LEAD).getRegistryName()), GenerationStep.Decoration.UNDERGROUND_ORES);
                addFeatureToBiome(biome.getValue(), BuiltinRegistries.CONFIGURED_FEATURE.get(LHBlocks.ores.get(Metal.SILVER).getRegistryName()), GenerationStep.Decoration.UNDERGROUND_ORES);
                addFeatureToBiome(biome.getValue(), BuiltinRegistries.CONFIGURED_FEATURE.get(LHBlocks.stoneBlocks.get(Stone.MARBLE).getRegistryName()), GenerationStep.Decoration.UNDERGROUND_ORES);
                addFeatureToBiome(biome.getValue(), BuiltinRegistries.CONFIGURED_FEATURE.get(LHBlocks.storageBlocks.get(Metal.METEORIC_IRON).getRegistryName()), GenerationStep.Decoration.UNDERGROUND_ORES);

            }
            if(biome.getValue().getBiomeCategory().equals(Biome.BiomeCategory.PLAINS)){
                //addFeatureToBiome(biome.getValue(), OLIVE_TREE_FEATURE, GenerationStep.Decoration.SURFACE_STRUCTURES);
            }
        }
    }

    public static void addFeatureToBiome(Biome biome, ConfiguredFeature<?, ?> configuredFeature, GenerationStep.Decoration decoration) {
        List<List<Supplier<ConfiguredFeature<?, ?>>>> biomeFeatures = new ArrayList<>(biome.getGenerationSettings().features());
        while (biomeFeatures.size() <= decoration.ordinal()) {
            biomeFeatures.add(Lists.newArrayList());
        }
        List<Supplier<ConfiguredFeature<?, ?>>> features = new ArrayList<>(biomeFeatures.get(decoration.ordinal()));
        features.add(() -> configuredFeature);
        biomeFeatures.set(decoration.ordinal(), features);

        ObfuscationReflectionHelper.setPrivateValue(BiomeGenerationSettings.class, biome.getGenerationSettings(), biomeFeatures, "f_47781_");
    }

    public class States{
        public static final BlockState TIN_ORE = LHBlocks.ores.get(Metal.TIN).defaultBlockState();
        public static final BlockState LEAD_ORE = LHBlocks.ores.get(Metal.LEAD).defaultBlockState();
        public static final BlockState SILVER_ORE = LHBlocks.ores.get(Metal.SILVER).defaultBlockState();
        public static final BlockState METEORIC_IRON = LHBlocks.storageBlocks.get(Metal.METEORIC_IRON).defaultBlockState();

        public static final BlockState MARBLE = LHBlocks.stoneBlocks.get(Stone.MARBLE).defaultBlockState();
        public static final BlockState BLACK_MARBLE = LHBlocks.stoneBlocks.get(Stone.BLACK_MARBLE).defaultBlockState();

        //public static final BlockState OLIVE_LOG = LHBlocks.logs.get(Wood.OLIVE).defaultBlockState();
        public static final BlockState POMEGRANATE_LOG = LHBlocks.logs.get(Wood.POMEGRANATE).defaultBlockState();

        //public static final BlockState OLIVE_LEAVES = LHBlocks.leaves.get(Wood.OLIVE).defaultBlockState();
        public static final BlockState POMEGRANATE_LEAVES = LHBlocks.leaves.get(Wood.POMEGRANATE).defaultBlockState();

        //public static final BlockState OLIVE_SAPLING = LHBlocks.saplings.get(Wood.OLIVE).defaultBlockState();
        public static final BlockState POMEGRANATE_SAPLING = LHBlocks.saplings.get(Wood.POMEGRANATE).defaultBlockState();
    }
}
