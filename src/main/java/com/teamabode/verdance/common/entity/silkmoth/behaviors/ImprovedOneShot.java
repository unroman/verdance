package com.teamabode.verdance.common.entity.silkmoth.behaviors;

import com.google.common.collect.Maps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

import java.util.Map;

public abstract class ImprovedOneShot<E extends LivingEntity> extends OneShot<E> {
    private final Map<MemoryModuleType<?>, MemoryStatus> requiredMemories = Maps.newHashMap();

    public abstract void requires(Map<MemoryModuleType<?>, MemoryStatus> map);

    public final boolean requiredMemories(Brain<?> brain) {
        this.requires(requiredMemories);

        boolean successful = false;

        for (var entry : this.requiredMemories.entrySet()) {
            MemoryModuleType<?> memoryModule = entry.getKey();
            MemoryStatus memoryStatus = this.requiredMemories.get(memoryModule);

            if (brain.checkMemory(memoryModule, memoryStatus)) {
                successful = true;
                continue;
            }
            successful = false;
        }
        return successful;
    }

    public boolean canRun(ServerLevel level, E entity, long gameTime) {
        return true;
    }

    public boolean trigger(ServerLevel level, E entity, long gameTime) {
        if (this.requiredMemories(entity.getBrain()) && this.canRun(level, entity, gameTime)) {
            this.run(level, entity, gameTime);
            return true;
        }
        return false;
    }

    public abstract void run(ServerLevel level, E entity, long gameTime);
}