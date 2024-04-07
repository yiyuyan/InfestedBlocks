package cn.ksmcbrigade.ibs.mixin;

import cn.ksmcbrigade.ibs.InfestedBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

import static cn.ksmcbrigade.ibs.InfestedBlocks.infestedBlocks$spawnInfestation;

@Mixin(BlockBehaviour.class)
public abstract class BlockBehaviourMixin extends ForgeRegistryEntry<Block> {

    @Inject(method = "spawnAfterBreak",at = @At("TAIL"))
    public void breakSpawn(BlockState p_60458_, ServerLevel p_60459_, BlockPos p_60460_, ItemStack p_60461_, CallbackInfo ci){
        if(!(Objects.requireNonNull(getRegistryName()).getNamespace().equalsIgnoreCase("minecraft") && Objects.requireNonNull(getRegistryName()).getPath().toLowerCase().contains("infested")) && !InfestedBlocks.con(getRegistryName())){
            if (p_60459_.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH,p_60461_) == 0) {
                infestedBlocks$spawnInfestation(p_60459_, p_60460_);
            }
        }
    }
}
