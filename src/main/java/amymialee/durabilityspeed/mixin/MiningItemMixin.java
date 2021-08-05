package amymialee.durabilityspeed.mixin;

import amymialee.durabilityspeed.DurabilitySpeed;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

@Mixin(ToolItem.class)
public class MiningItemMixin extends Item {
    protected MiningItemMixin(int i, float f, ToolMaterialType toolMaterialType, Block[] blocks) {
        super(i);
    }


    @Shadow protected float miningSpeed;
    @Shadow private Block[] field_13191;

    @Inject(method = "getMiningSpeedMultiplier", at = @At("RETURN"), cancellable = true)
    private void getMiningSpeedMultiplier(ItemStack stack, Block block, CallbackInfoReturnable<Float> cir) {
        if (DurabilitySpeed.config.modEnabled) {
            float multiplier = ((1 - ((float) stack.getDamage() / (float) stack.getMaxDamage())) *
                    DurabilitySpeed.config.maximumSpeed - DurabilitySpeed.config.minimumSpeed) + DurabilitySpeed.config.minimumSpeed;
            for(int var3 = 0; var3 < this.field_13191.length; ++var3) {
                if (this.field_13191[var3] == block) {
                    cir.setReturnValue(this.miningSpeed * multiplier);
                }
            }
            cir.setReturnValue(1.0F);
        }
    }
}
