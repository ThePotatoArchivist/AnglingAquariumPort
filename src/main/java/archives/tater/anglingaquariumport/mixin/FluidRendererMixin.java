package archives.tater.anglingaquariumport.mixin;

import archives.tater.anglingaquariumport.AnglingAquariumPort;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.block.LiquidBlockRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(LiquidBlockRenderer.class)
public class FluidRendererMixin {
	@WrapOperation(
			method = "tesselate",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/LiquidBlockRenderer;shouldRenderFace(Lnet/minecraft/world/level/BlockAndTintGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/material/FluidState;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;Lnet/minecraft/world/level/material/FluidState;)Z")
	)
	private boolean hideGlassAdjacent(BlockAndTintGetter world, BlockPos pos, FluidState fluidState, BlockState blockState, Direction direction, FluidState neighborFluidState, Operation<Boolean> original) {
		if (!original.call(world, pos, fluidState, blockState, direction, neighborFluidState)) return false;
		return !AnglingAquariumPort.shouldCull(fluidState) || !AnglingAquariumPort.shouldCullFluid(world, pos, direction);
	}

	@ModifyExpressionValue(
			method = "tesselate",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/LiquidBlockRenderer;isNeighborSameFluid(Lnet/minecraft/world/level/material/FluidState;Lnet/minecraft/world/level/material/FluidState;)Z")
	)
	private boolean hideGlassAbove(
			boolean original,
			@Local(argsOnly = true) BlockAndTintGetter world,
			@Local(argsOnly = true) BlockPos pos,
			@Local(argsOnly = true) FluidState fluidState
	) {
		return original || fluidState.getAmount() == 8 && AnglingAquariumPort.shouldCull(fluidState) && AnglingAquariumPort.shouldCullFluid(world, pos, Direction.UP);
	}
}
