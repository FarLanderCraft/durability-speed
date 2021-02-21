package amymialee.durabilityspeed.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

@Mixin(MiningToolItem.class)
public abstract class ToolMixin {
    @Shadow @Final private Set<Block> effectiveBlocks;

    @Shadow @Final protected float miningSpeed;

    @Inject(method = "getMiningSpeedMultiplier", at = @At("RETURN"), cancellable = true)
    private void getMiningSpeedMultiplier(ItemStack stack, BlockState state, CallbackInfoReturnable<Float> cir) {
        float multiplier = 1;
        multiplier = (1 - ((float) stack.getDamage() / (float) stack.getMaxDamage())) * 2;
        cir.setReturnValue(this.effectiveBlocks.contains(state.getBlock()) ? this.miningSpeed * multiplier : 1.0F);
    }
}
