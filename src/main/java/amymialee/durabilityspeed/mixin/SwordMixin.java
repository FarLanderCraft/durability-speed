package amymialee.durabilityspeed.mixin;

import amymialee.durabilityspeed.DurabilitySpeed;
import net.minecraft.block.Block;
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
    public SwordMixin(ToolMaterialType material) {}

    @Inject(method = "getMiningSpeedMultiplier", at = @At("RETURN"), cancellable = true)
    private void getMiningSpeedMultiplier(ItemStack stack, Block block, CallbackInfoReturnable<Float> cir) {
        if (DurabilitySpeed.config.modEnabled) {
            float multiplier = ((1 - ((float) stack.getDamage() / (float) stack.getMaxDamage())) *
                    DurabilitySpeed.config.maximumSpeed - DurabilitySpeed.config.minimumSpeed) + DurabilitySpeed.config.minimumSpeed;
            if (block == Blocks.COBWEB) {
                cir.setReturnValue(15.0F * multiplier);
            } else {
                Material material = block.getMaterial();
                cir.setReturnValue(material != Material.PLANT && material != Material.REPLACEABLE_PLANT && !(block == Blocks.LEAVES) && material != Material.PUMPKIN ? 1.0F : 1.5F * multiplier);
            }
        }
    }
}
