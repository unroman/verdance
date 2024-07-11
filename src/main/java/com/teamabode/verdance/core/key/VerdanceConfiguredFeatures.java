package com.teamabode.verdance.core.key;

import com.teamabode.verdance.Verdance;
import com.teamabode.verdance.common.worldgen.feature.MulberryTrunkPlacer;
import com.teamabode.verdance.core.registry.VerdanceBlocks;
import com.teamabode.verdance.core.tag.VerdanceBlockTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.CherryFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;

public class VerdanceConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_MULBERRY_FOREST = createKey("flower_mulberry_forest");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MULBERRY = createKey("mulberry");

    public static final ResourceKey<ConfiguredFeature<?, ?>> SHRUB_PATCH_BONEMEAL = createKey("shrub_patch_bonemeal");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_CANTALOUPE = createKey("patch_cantaloupe");

    public static void register(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        FeatureUtils.register(context, FLOWER_MULBERRY_FOREST, Feature.FLOWER, new RandomPatchConfiguration(
                64,
                7,
                3,
                PlacementUtils.inlinePlaced(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                        new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                                .add(Blocks.LILY_OF_THE_VALLEY.defaultBlockState(), 1)
                                .add(Blocks.OXEYE_DAISY.defaultBlockState(), 2).build())
                ))
        ));
        FeatureUtils.register(context, MULBERRY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                SimpleStateProvider.simple(VerdanceBlocks.MULBERRY_LOG),
                new MulberryTrunkPlacer(7, 2, 0),
                new WeightedStateProvider(
                        SimpleWeightedRandomList.<BlockState>builder()
                                .add(VerdanceBlocks.MULBERRY_LEAVES.defaultBlockState(), 8)
                                .add(VerdanceBlocks.FLOWERING_MULBERRY_LEAVES.defaultBlockState(), 1)
                                .build()
                ),
                new CherryFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), ConstantInt.of(4), 0.33333333F, 0.25f, 0.16666667f, 0.33333334f),
                new TwoLayersFeatureSize(1, 0, 2)
        ).build());
        FeatureUtils.register(context, PATCH_CANTALOUPE, Feature.RANDOM_PATCH, new RandomPatchConfiguration(
                96,
                8,
                3,
                PlacementUtils.inlinePlaced(
                        Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(VerdanceBlocks.CANTALOUPE)),
                        BlockPredicateFilter.forPredicate(BlockPredicate.allOf(
                                BlockPredicate.matchesBlocks(Blocks.AIR),
                                BlockPredicate.matchesTag(VerdanceBlockTags.SHRUB_MAY_PLACE_ON)
                )))
        ));
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, Verdance.id(name));
    }

}