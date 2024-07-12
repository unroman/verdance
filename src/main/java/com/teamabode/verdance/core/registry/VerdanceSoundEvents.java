package com.teamabode.verdance.core.registry;

import com.teamabode.verdance.Verdance;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;

public class VerdanceSoundEvents {
    public static final SoundEvent BLOCK_STUCCO_BREAK = register("block.stucco.break");
    public static final SoundEvent BLOCK_STUCCO_FALL = register("block.stucco.fall");
    public static final SoundEvent BLOCK_STUCCO_HIT = register("block.stucco.hit");
    public static final SoundEvent BLOCK_STUCCO_STEP = register("block.stucco.step");
    public static final SoundEvent BLOCK_STUCCO_PLACE = register("block.stucco.place");

    public static final SoundEvent ENTITY_SILK_MOTH_IDLE = register("entity.silk_moth.idle");
    public static final SoundEvent ENTITY_SILK_MOTH_HURT = register("entity.silk_moth.hurt");
    public static final SoundEvent ENTITY_SILK_MOTH_DEATH = register("entity.silk_moth.death");

    public static final SoundEvent ENTITY_SILKWORM_HURT = register("entity.silkworm.hurt");
    public static final SoundEvent ENTITY_SILKWORM_DEATH = register("entity.silkworm.death");

    /*
        TODO:
        - BLOCK_SILK_COCOON_PLACE "block.silk_cocoon.place"
        - BLOCK_SILK_COCOON_BREAK "block.silk_cocoon.break"
        - ENTITY_SILK_MOTH_EAT "entity.silk_moth.eat"
     */

    public static void register() {

    }

    private static SoundEvent register(String name) {
        return Registry.register(BuiltInRegistries.SOUND_EVENT, Verdance.id(name), SoundEvent.createVariableRangeEvent(Verdance.id(name)));
    }

    private static Holder.Reference<SoundEvent> registerHolder(String name) {
        return Registry.registerForHolder(BuiltInRegistries.SOUND_EVENT, Verdance.id(name), SoundEvent.createVariableRangeEvent(Verdance.id(name)));
    }
}
