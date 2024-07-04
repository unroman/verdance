package com.teamabode.verdance.common.block;

import com.teamabode.verdance.common.entity.CushionEntity;
import com.teamabode.verdance.core.registry.VerdanceEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CushionBlock extends Block {

    private static final VoxelShape CUSHION_SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 10.0, 16.0);

    public CushionBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean hasAnalogOutputSignal(BlockState blockState) {
        return true;
    }

    //When you sit on Cushion, it gives a redstone signal of 15
    @Override
    protected int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos blockPos) {
        List<CushionEntity> aBunchOfCushions = level.getEntitiesOfClass(CushionEntity.class, new AABB(blockPos));
       // System.out.println(aBunchOfCushions);
        if (!(aBunchOfCushions.isEmpty())) {
            return 15;
        } else {
            return 8;
        }
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
        if (!level.isClientSide()) {
            List<CushionEntity> entities = level.getEntitiesOfClass(CushionEntity.class, new AABB(blockPos));
            if (!entities.isEmpty()) {
                return InteractionResult.FAIL;
            } else {
                CushionEntity cushionEntity = VerdanceEntityTypes.CUSHION.create(level);
                cushionEntity.setPos(blockPos.getX() + 0.5D, blockPos.getY() + 0.4D, blockPos.getZ() + 0.5D);
                //cushionEntity.setPos(blockPos.getX(), blockPos.getY(), blockPos.getZ());
                level.addFreshEntity(cushionEntity);
                player.startRiding(cushionEntity);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }

    public void fallOn(Level level, BlockState blockState, BlockPos blockPos, Entity entity, float f) {
        super.fallOn(level, blockState, blockPos, entity, f * 0.5F);
    }

    public void updateEntityAfterFallOn(BlockGetter blockGetter, Entity entity) {
        if (entity.isSuppressingBounce()) {
            super.updateEntityAfterFallOn(blockGetter, entity);
        } else {
            this.bounceUp(entity);
        }

    }

    private void bounceUp(Entity entity) {
        Vec3 vec3 = entity.getDeltaMovement();
        if (vec3.y < 0.0) {
            double d = entity instanceof LivingEntity ? 1.0 : 0.8;
            entity.setDeltaMovement(vec3.x, -vec3.y * 0.7 * d, vec3.z);
        }

    }

    @Override
    protected boolean isPathfindable(BlockState blockState, PathComputationType pathComputationType) {
        return false;
    }

    @Override
    public VoxelShape getCollisionShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return CUSHION_SHAPE;
    }

    @Override
    public VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return CUSHION_SHAPE;
    }
}