package archives.tater.anglingaquariumport.mixin;

import archives.tater.anglingaquariumport.AnglingAquariumPort;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import me.jellysquid.mods.sodium.client.render.chunk.compile.ChunkBuildBuffers;
import me.jellysquid.mods.sodium.client.render.chunk.compile.pipeline.FluidRenderer;
import me.jellysquid.mods.sodium.client.world.WorldSlice;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockRenderView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FluidRenderer.class)
public class SodiumFluidRendererMixin {
    @Unique
    private boolean aap$isFullFluid = false;

    @Inject(
            method = "render",
            at = @At("HEAD")
    )
    private void measureFluidHeight(WorldSlice world, FluidState fluidState, BlockPos blockPos, BlockPos offset, ChunkBuildBuffers buffers, CallbackInfo ci) {
        aap$isFullFluid = fluidState.getLevel() == 8;
    }

    @ModifyExpressionValue(
            method = "isFluidOccluded",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/fluid/Fluid;matchesType(Lnet/minecraft/fluid/Fluid;)Z")
    )
    private boolean checkGlass(boolean original, @Local(argsOnly = true) BlockRenderView world, @Local(argsOnly = true) Fluid fluid, @Local(argsOnly = true) Direction direction, @Local BlockPos.Mutable pos, @Local BlockState blockState) {
        return original || (direction != Direction.UP || aap$isFullFluid) && AnglingAquariumPort.shouldCull(fluid) && AnglingAquariumPort.isCullingGlass(world, pos, blockState, direction);
    }
}
