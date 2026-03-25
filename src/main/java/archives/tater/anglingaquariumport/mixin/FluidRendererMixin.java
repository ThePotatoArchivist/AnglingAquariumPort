package archives.tater.anglingaquariumport.mixin;

import archives.tater.anglingaquariumport.AnglingAquariumPort;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.FluidRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

@Environment(EnvType.CLIENT)
@Mixin(FluidRenderer.class)
public class FluidRendererMixin {
	@WrapOperation(
			method = "tesselate",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/FluidRenderer;shouldRenderFace(Lnet/minecraft/world/level/material/FluidState;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;Lnet/minecraft/world/level/material/FluidState;)Z")
	)
	private boolean hideGlassAdjacent(FluidState fluidState, BlockState blockState, Direction side, FluidState neighborFluid, Operation<Boolean> original, BlockAndTintGetter level, BlockPos pos) {
        return original.call(fluidState, blockState, side, neighborFluid) &&
				(!AnglingAquariumPort.shouldCull(fluidState) || !AnglingAquariumPort.shouldCullFluid(level, pos, side));
    }

	@ModifyExpressionValue(
			method = "tesselate",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/FluidRenderer;isNeighborSameFluid(Lnet/minecraft/world/level/material/FluidState;Lnet/minecraft/world/level/material/FluidState;)Z")
	)
	private boolean hideGlassAbove(
			boolean original,
			BlockAndTintGetter level,
			BlockPos pos,
			FluidRenderer.Output output,
			BlockState blockState,
			FluidState fluidState
	) {
		return original || fluidState.getAmount() == 8 && AnglingAquariumPort.shouldCull(fluidState) && AnglingAquariumPort.shouldCullFluid(level, pos, Direction.UP);
	}
}
