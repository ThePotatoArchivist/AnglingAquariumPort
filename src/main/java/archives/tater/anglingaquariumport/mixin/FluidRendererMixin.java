package archives.tater.anglingaquariumport.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBlockTags;
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
		return original && !(world.getBlockState(pos.offset(direction)).isIn(ConventionalBlockTags.GLASS_BLOCKS));
	}
}
