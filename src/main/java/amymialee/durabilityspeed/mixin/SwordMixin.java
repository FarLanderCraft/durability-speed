package amymialee.durabilityspeed.mixin;

import amymialee.durabilityspeed.DurabilitySpeed;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.item.*;
import net.minecraft.tag.BlockTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SwordItem.class)
public class SwordMixin extends ToolItem {
    public SwordMixin(ToolMaterial material, Settings settings) {
        super(material, settings);
    }

    @Inject(method = "getMiningSpeedMultiplier", at = @At("RETURN"), cancellable = true)
    private void getMiningSpeedMultiplier(ItemStack stack, BlockState state, CallbackInfoReturnable<Float> cir) {
        if (DurabilitySpeed.configGet.effectSwords) {
            float multiplier = 1;
            multiplier = ((1 - ((float) stack.getDamage() / (float) stack.getMaxDamage())) * DurabilitySpeed.configGet.maximumSpeed - DurabilitySpeed.configGet.minimumSpeed) + DurabilitySpeed.configGet.minimumSpeed;
            if (state.isOf(Blocks.COBWEB)) {
                cir.setReturnValue(15.0F * multiplier);
            } else {
                Material material = state.getMaterial();
                cir.setReturnValue(material != Material.PLANT && material != Material.REPLACEABLE_PLANT && material != Material.UNUSED_PLANT && !state.isIn(BlockTags.LEAVES) && material != Material.GOURD ? 1.0F : multiplier * 1.5F);
            }
        }
    }
}
