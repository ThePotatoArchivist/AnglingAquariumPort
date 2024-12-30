package archives.tater.anglingaquariumport.mixin;

import archives.tater.anglingaquariumport.AnglingAquariumPort;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBlockTags;
import net.minecraft.block.SideShapeType;
import net.minecraft.client.render.block.FluidRenderer;
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
			@Local(argsOnly = true) BlockRenderView world,
			@Local(argsOnly = true) BlockPos pos,
			@Local(argsOnly = true) Direction direction
	) {
		if (!original) return false;
		var offsetPos = pos.offset(direction);
		var state = world.getBlockState(offsetPos);
		return !AnglingAquariumPort.VANILLA_GLASS_BLOCKS.contains(state.getBlock()) && !state.isIn(ConventionalBlockTags.GLASS_BLOCKS) && !(state.isIn(AnglingAquariumPort.SIDED_GLASS_BLOCKS) && state.isSideSolid(world, offsetPos, direction.getOpposite(), SideShapeType.FULL));
	}
}
