package archives.tater.anglingaquariumport.mixin;

import archives.tater.anglingaquariumport.AnglingAquariumPort;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

import net.caffeinemc.mods.sodium.client.render.chunk.compile.pipeline.BlockOcclusionCache;
import net.caffeinemc.mods.sodium.client.services.PlatformBlockAccess;

@Mixin(BlockOcclusionCache.class)
public class SodiumBlockOcclusionCacheMixin {
    @WrapOperation(
            method = "shouldDrawFullBlockFluidSide",
            at = @At(value = "INVOKE", target = "Lnet/caffeinemc/mods/sodium/client/services/PlatformBlockAccess;shouldOccludeFluid(Lnet/minecraft/core/Direction;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/material/FluidState;)Z")
    )
    private boolean checkGlass(PlatformBlockAccess instance, Direction direction, BlockState blockState, FluidState fluidState, Operation<Boolean> original, @Local(argsOnly = true) BlockGetter view, @Local BlockPos.MutableBlockPos otherPos) {
        return original.call(instance, direction, blockState, fluidState) || (direction != Direction.DOWN || fluidState.getAmount() == 8) && AnglingAquariumPort.shouldCull(fluidState) && AnglingAquariumPort.isCullingGlass(view, otherPos, blockState, direction);
    }
}
