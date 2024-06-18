package com.teamabode.verdance.core.misc.worldgen;

import com.teamabode.verdance.Verdance;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class VerdanceBiomes {

    public static final ResourceKey<Biome> SHRUBLANDS = create("shrublands");
    public static final ResourceKey<Biome> MULBERRY_FOREST = create("mulberry_forest");

    private static ResourceKey<Biome> create(String name) {
        return ResourceKey.create(Registries.BIOME, Verdance.id(name));
    }
}
