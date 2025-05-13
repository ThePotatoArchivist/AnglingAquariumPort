package archives.tater.anglingaquariumport;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.tag.client.v1.ClientTags;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SideShapeType;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

import static net.fabricmc.fabric.api.tag.client.v1.ClientTags.isInWithLocalFallback;

public class AnglingAquariumPort implements ClientModInitializer {
    public static final String MOD_ID = "anglingaquariumport";

    public static final TagKey<Block> SIDED_GLASS_BLOCKS = TagKey.of(RegistryKeys.BLOCK, Identifier.of(MOD_ID, "sided_glass_blocks"));

    public static boolean shouldCull(FluidState fluid) {
        return fluid.isIn(FluidTags.WATER);
    }

    public static boolean shouldCullFluid(BlockView world, BlockPos fluidPos, Direction direction) {
        var offsetPos = fluidPos.offset(direction);
        var state = world.getBlockState(offsetPos);
        return isCullingGlass(world, offsetPos, state, direction);
    }

    public static boolean isCullingGlass(BlockView world, BlockPos pos, BlockState state, Direction direction) {
        return isInWithLocalFallback(ConventionalBlockTags.GLASS_BLOCKS, state.getRegistryEntry()) ||
                (isInWithLocalFallback(AnglingAquariumPort.SIDED_GLASS_BLOCKS, state.getRegistryEntry())
                        && state.isSideSolid(world, pos, direction.getOpposite(), SideShapeType.FULL));
    }

    @Override
    public void onInitializeClient() {
        ClientTags.getOrCreateLocalTag(ConventionalBlockTags.GLASS_BLOCKS);
        ClientTags.getOrCreateLocalTag(SIDED_GLASS_BLOCKS);
    }
}
