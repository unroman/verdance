package com.teamabode.verdance.common.entity.silk_moth.sensor;

import com.google.common.collect.ImmutableSet;
import com.teamabode.verdance.common.entity.silk_moth.SilkMoth;
import com.teamabode.verdance.core.registry.VerdanceMemoryModuleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;

import java.util.Optional;
import java.util.Set;

public class SilkMothSensor extends Sensor<SilkMoth> {

    protected void doTick(ServerLevel level, SilkMoth entity) {
        Brain<SilkMoth> brain = entity.getBrain();
        Optional<Unit> isFlying = Optional.empty();
        Optional<Unit> wantsToSleep = Optional.empty();

        if (entity.isFlying()) {
            isFlying = Optional.of(Unit.INSTANCE);
        }
        if (entity.level().isDay()) {
            wantsToSleep = Optional.of(Unit.INSTANCE);
        }
        brain.setMemory(VerdanceMemoryModuleTypes.IS_FLYING, isFlying);
        brain.setMemory(VerdanceMemoryModuleTypes.WANTS_TO_SLEEP, wantsToSleep);
    }

    public Set<MemoryModuleType<?>> requires() {
        return ImmutableSet.of(
                VerdanceMemoryModuleTypes.IS_FLYING,
                VerdanceMemoryModuleTypes.WANTS_TO_SLEEP
        );
    }
}
