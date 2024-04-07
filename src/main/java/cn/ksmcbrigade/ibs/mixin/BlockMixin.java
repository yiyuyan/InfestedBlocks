package cn.ksmcbrigade.ibs.mixin;

import cn.ksmcbrigade.ibs.InfestedBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.common.extensions.IForgeBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

import static cn.ksmcbrigade.ibs.InfestedBlocks.infestedBlocks$spawnInfestation;

@Mixin(Block.class)
public abstract class BlockMixin extends BlockBehaviour implements IForgeBlock {

    public BlockMixin(Properties p_60452_) {
        super(p_60452_);
    }

    @Inject(method = "wasExploded",at = @At("TAIL"))
    public void explodedSpawn(Level p_49844_, BlockPos p_49845_, Explosion p_49846_, CallbackInfo ci){
        if(!(Objects.requireNonNull(getRegistryName()).getNamespace().equalsIgnoreCase("minecraft") && Objects.requireNonNull(getRegistryName()).getPath().toLowerCase().contains("infested")) && !InfestedBlocks.con(getRegistryName()) && (p_49844_ instanceof ServerLevel serverLevel)){
            infestedBlocks$spawnInfestation(serverLevel,p_49845_);
        }
    }
}
