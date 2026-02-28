package archives.tater.anglingaquariumport;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

import static net.fabricmc.fabric.api.tag.client.v1.ClientTags.isInWithLocalFallback;

public class AnglingAquariumPort implements ClientModInitializer {
    public static final String MOD_ID = "anglingaquariumport";

    public static final TagKey<Block> SIDED_GLASS_BLOCKS = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MOD_ID, "sided_glass_blocks"));

    public static boolean shouldCull(FluidState fluid) {
        return fluid.is(FluidTags.WATER);
    }

    public static boolean shouldCullFluid(BlockGetter world, BlockPos fluidPos, Direction direction) {
        var offsetPos = fluidPos.relative(direction);
        var state = world.getBlockState(offsetPos);
        return isCullingGlass(world, offsetPos, state, direction);
    }

    public static boolean isCullingGlass(BlockGetter world, BlockPos pos, BlockState state, Direction direction) {
        return isInWithLocalFallback(ConventionalBlockTags.GLASS_BLOCKS, state.getBlock()) ||
                (isInWithLocalFallback(AnglingAquariumPort.SIDED_GLASS_BLOCKS, state.getBlock())
                        && state.isFaceSturdy(world, pos, direction.getOpposite(), SupportType.FULL));
    }

    @Override
    public void onInitializeClient() {
    }
}
