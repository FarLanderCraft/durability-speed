package amymialee.durabilityspeed.mixin;

import amymialee.durabilityspeed.DurabilitySpeed;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterialType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

@Mixin(AxeItem.class)
public class AxeItemMixin extends ToolItem {
    protected AxeItemMixin(float f, ToolMaterialType toolMaterial, Set<Block> set) {
        super(f, toolMaterial, set);
    }

    @Inject(method = "getMiningSpeedMultiplier", at = @At("RETURN"), cancellable = true)
    private void getMiningSpeedMultiplier(ItemStack stack, Block block, CallbackInfoReturnable<Float> cir) {
        if (DurabilitySpeed.config.modEnabled) {
            Material material = block.getMaterial();
            float multiplier = ((1 - ((float) stack.getDamage() / (float) stack.getMaxDamage())) * DurabilitySpeed.config.maximumSpeed - DurabilitySpeed.config.minimumSpeed) + DurabilitySpeed.config.minimumSpeed;
            cir.setReturnValue(block.getMaterial() != Material.WOOD && block.getMaterial() != Material.PLANT && block.getMaterial() != Material.REPLACEABLE_PLANT ? super.getMiningSpeedMultiplier(stack, block) : this.miningSpeed * multiplier);
        }
    }
}
