package amymialee.durabilityspeed.mixin;

import amymialee.durabilityspeed.DurabilitySpeed;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

@Mixin(ToolItem.class)
public class MiningItemMixin extends Item {
    protected MiningItemMixin(float f, float g, ToolMaterialType toolMaterial, Set<Block> set) {}
    @Shadow @Final private Set<Block> effectiveBlocks;

    @Shadow protected float miningSpeed;

    @Inject(method = "getMiningSpeedMultiplier", at = @At("RETURN"), cancellable = true)
    private void getMiningSpeedMultiplier(ItemStack stack, Block block, CallbackInfoReturnable<Float> cir) {
        if (DurabilitySpeed.config.modEnabled) {
            float multiplier = ((1 - ((float) stack.getDamage() / (float) stack.getMaxDamage())) *
                    DurabilitySpeed.config.maximumSpeed - DurabilitySpeed.config.minimumSpeed) + DurabilitySpeed.config.minimumSpeed;
            cir.setReturnValue(this.effectiveBlocks.contains(block) ? this.miningSpeed * multiplier : 1.0F);
        }
    }

}
