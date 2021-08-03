package amymialee.durabilityspeed.mixin;

import amymialee.durabilityspeed.DurabilitySpeed;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.item.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SwordItem.class)
public class SwordMixin extends Item {
    public SwordMixin(Item.ToolMaterial material) {}

    @Inject(method = "getMiningSpeedMultiplier", at = @At("RETURN"), cancellable = true)
    private void getMiningSpeedMultiplier(ItemStack stack, BlockState state, CallbackInfoReturnable<Float> cir) {
        if (DurabilitySpeed.config.modEnabled) {
            float multiplier = ((1 - ((float) stack.getDamage() / (float) stack.getMaxDamage())) *
                    DurabilitySpeed.config.maximumSpeed - DurabilitySpeed.config.minimumSpeed) + DurabilitySpeed.config.minimumSpeed;
            if (state.getBlock() == Blocks.COBWEB) {
                cir.setReturnValue(15.0F * multiplier);
            } else {
                Material material = state.getMaterial();
                cir.setReturnValue(material != Material.PLANT && material != Material.REPLACEABLE_PLANT && !(state.getBlock() == Blocks.LEAVES) && material != Material.GOURD ? 1.0F : 1.5F * multiplier);
            }
        }
    }
}
