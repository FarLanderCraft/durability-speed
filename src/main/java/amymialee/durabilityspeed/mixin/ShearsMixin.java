package amymialee.durabilityspeed.mixin;

import amymialee.durabilityspeed.DurabilitySpeed;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShearsItem.class)
public class ShearsMixin extends Item {
    public ShearsMixin() {}

    @Inject(method = "getMiningSpeedMultiplier", at = @At("RETURN"), cancellable = true)
    private void getMiningSpeedMultiplier(ItemStack stack, BlockState state, CallbackInfoReturnable<Float> cir) {
        if (DurabilitySpeed.config.modEnabled) {
            float multiplier = ((1 - ((float) stack.getDamage() / (float) stack.getMaxDamage())) * DurabilitySpeed.config.maximumSpeed - DurabilitySpeed.config.minimumSpeed) + DurabilitySpeed.config.minimumSpeed;
            if (!(state.getBlock() == Blocks.COBWEB) && !(state.getBlock() == Blocks.LEAVES) && !(state.getBlock() == Blocks.LEAVES2)) {
                cir.setReturnValue(state.getBlock() == Blocks.WOOL ? 5.0F * multiplier : super.getMiningSpeedMultiplier(stack, state));
            } else {
                cir.setReturnValue(15.0F * multiplier);
            }
        }
    }
}
