package archives.tater.anglingaquariumport.mixin;

import archives.tater.anglingaquariumport.AnglingAquariumPort;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.block.FluidRenderer;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockRenderView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(FluidRenderer.class)
public class FluidRendererMixin {
	@WrapOperation(
			method = "render",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/block/FluidRenderer;shouldRenderSide(Lnet/minecraft/world/BlockRenderView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/fluid/FluidState;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/Direction;Lnet/minecraft/fluid/FluidState;)Z")
	)
	private boolean hideGlassAdjacent(BlockRenderView world, BlockPos pos, FluidState fluidState, BlockState blockState, Direction direction, FluidState neighborFluidState, Operation<Boolean> original) {
		if (!original.call(world, pos, fluidState, blockState, direction, neighborFluidState)) return false;
		return !AnglingAquariumPort.shouldCull(fluidState) || !AnglingAquariumPort.shouldCullFluid(world, pos, direction);
	}

	@ModifyExpressionValue(
			method = "render",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/block/FluidRenderer;isSameFluid(Lnet/minecraft/fluid/FluidState;Lnet/minecraft/fluid/FluidState;)Z")
	)
	private boolean hideGlassAbove(
			boolean original,
			@Local(argsOnly = true) BlockRenderView world,
			@Local(argsOnly = true) BlockPos pos,
			@Local(argsOnly = true) FluidState fluidState
	) {
		return original || fluidState.getLevel() == 8 && AnglingAquariumPort.shouldCull(fluidState) && AnglingAquariumPort.shouldCullFluid(world, pos, Direction.UP);
	}
}
