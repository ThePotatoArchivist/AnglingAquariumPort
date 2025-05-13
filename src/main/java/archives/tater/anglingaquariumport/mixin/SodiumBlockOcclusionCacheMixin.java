package archives.tater.anglingaquariumport.mixin;

import archives.tater.anglingaquariumport.AnglingAquariumPort;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.caffeinemc.mods.sodium.client.render.chunk.compile.pipeline.BlockOcclusionCache;
import net.caffeinemc.mods.sodium.client.services.PlatformBlockAccess;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockOcclusionCache.class)
public class SodiumBlockOcclusionCacheMixin {
    @WrapOperation(
            method = "shouldDrawFullBlockFluidSide",
            at = @At(value = "INVOKE", target = "Lnet/caffeinemc/mods/sodium/client/services/PlatformBlockAccess;shouldOccludeFluid(Lnet/minecraft/util/math/Direction;Lnet/minecraft/block/BlockState;Lnet/minecraft/fluid/FluidState;)Z")
    )
    private boolean checkGlass(PlatformBlockAccess instance, Direction direction, BlockState blockState, FluidState fluidState, Operation<Boolean> original, @Local(argsOnly = true) BlockView view, @Local BlockPos.Mutable otherPos) {
        return original.call(instance, direction, blockState, fluidState) || (direction != Direction.DOWN || fluidState.getLevel() == 8) && AnglingAquariumPort.shouldCull(fluidState) && AnglingAquariumPort.isCullingGlass(view, otherPos, blockState, direction);
    }
}
