package archives.tater.anglingaquariumport.mixin;

//@Mixin(BlockOcclusionCache.class)
//public class SodiumBlockOcclusionCacheMixin {
//    @WrapOperation(
//            method = "shouldDrawFullBlockFluidSide",
//            at = @At(value = "INVOKE", target = "Lnet/caffeinemc/mods/sodium/client/services/PlatformBlockAccess;shouldOccludeFluid(Lnet/minecraft/core/Direction;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/material/FluidState;)Z")
//    )
//    private boolean checkGlass(PlatformBlockAccess instance, Direction direction, BlockState blockState, FluidState fluidState, Operation<Boolean> original, @Local(argsOnly = true) BlockGetter view, @Local BlockPos.MutableBlockPos otherPos) {
//        return original.call(instance, direction, blockState, fluidState) || (direction != Direction.DOWN || fluidState.getAmount() == 8) && AnglingAquariumPort.shouldCull(fluidState) && AnglingAquariumPort.isCullingGlass(view, otherPos, blockState, direction);
//    }
//}
