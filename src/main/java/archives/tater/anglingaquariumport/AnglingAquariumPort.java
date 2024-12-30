package archives.tater.anglingaquariumport;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class AnglingAquariumPort {
    public static final String MOD_ID = "anglingaquariumport";

    public static final TagKey<Block> SIDED_GLASS_BLOCKS = TagKey.of(RegistryKeys.BLOCK, new Identifier(MOD_ID, "sided_glass_blocks"));
}
