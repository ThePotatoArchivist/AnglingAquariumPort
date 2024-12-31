package archives.tater.anglingaquariumport;

import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SideShapeType;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockRenderView;

import java.util.List;

public class AnglingAquariumPort {
    public static final String MOD_ID = "anglingaquariumport";

    public static final List<Block> VANILLA_GLASS_BLOCKS = List.of(
            Blocks.GLASS,
            Blocks.TINTED_GLASS,
            Blocks.WHITE_STAINED_GLASS,
            Blocks.ORANGE_STAINED_GLASS,
            Blocks.MAGENTA_STAINED_GLASS,
            Blocks.LIGHT_BLUE_STAINED_GLASS,
            Blocks.YELLOW_STAINED_GLASS,
            Blocks.LIME_STAINED_GLASS,
            Blocks.PINK_STAINED_GLASS,
            Blocks.GRAY_STAINED_GLASS,
            Blocks.LIGHT_GRAY_STAINED_GLASS,
            Blocks.CYAN_STAINED_GLASS,
            Blocks.PURPLE_STAINED_GLASS,
            Blocks.BLUE_STAINED_GLASS,
            Blocks.BROWN_STAINED_GLASS,
            Blocks.GREEN_STAINED_GLASS,
            Blocks.RED_STAINED_GLASS,
            Blocks.BLACK_STAINED_GLASS
    );

    public static final TagKey<Block> SIDED_GLASS_BLOCKS = TagKey.of(RegistryKeys.BLOCK, Identifier.of(MOD_ID, "sided_glass_blocks"));

    public static boolean shouldCull(BlockRenderView world, BlockPos pos, FluidState fluidState, Direction direction) {
        if (!fluidState.isIn(FluidTags.WATER)) return false;
        var offsetPos = pos.offset(direction);
        var state = world.getBlockState(offsetPos);
        return AnglingAquariumPort.VANILLA_GLASS_BLOCKS.contains(state.getBlock()) || state.isIn(ConventionalBlockTags.GLASS_BLOCKS) || state.isIn(AnglingAquariumPort.SIDED_GLASS_BLOCKS) && state.isSideSolid(world, offsetPos, direction.getOpposite(), SideShapeType.FULL);
    }
}
