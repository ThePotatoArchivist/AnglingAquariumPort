package archives.tater.anglingaquariumport.mixin;

import archives.tater.anglingaquariumport.AnglingAquariumPort;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
	@ModifyReturnValue(
			method = "shouldRenderSide",
			at = @At("RETURN")
	)
	private static boolean hideGlassAdjacent(
			boolean original,
			@Local(argsOnly = true, ordinal = 0) FluidState fluidState,
			@Local(argsOnly = true) BlockRenderView world,
			@Local(argsOnly = true) BlockPos pos,
			@Local(argsOnly = true) Direction direction
	) {
		if (!original) return false;
		return !AnglingAquariumPort.shouldCull(world, pos, fluidState, direction);
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
		return original || fluidState.getLevel() == 8 && AnglingAquariumPort.shouldCull(world, pos, fluidState, Direction.UP);
	}
}
