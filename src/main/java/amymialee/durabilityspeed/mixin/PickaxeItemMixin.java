package amymialee.durabilityspeed.mixin;

import amymialee.durabilityspeed.DurabilitySpeed;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterialType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.Set;

@Mixin(PickaxeItem.class)
public class PickaxeItemMixin extends ToolItem {
    protected PickaxeItemMixin(float f, ToolMaterialType toolMaterial, Set<Block> set) {
        super(f, toolMaterial, set);
    }

    @Inject(method = "getMiningSpeedMultiplier", at = @At("RETURN"), cancellable = true)
    private void getMiningSpeedMultiplier(ItemStack stack, Block block, CallbackInfoReturnable<Float> cir) {
        if (DurabilitySpeed.config.modEnabled) {
            Material material = block.getMaterial();
            float multiplier = ((1 - ((float) stack.getDamage() / (float) stack.getMaxDamage())) * DurabilitySpeed.config.maximumSpeed - DurabilitySpeed.config.minimumSpeed) + DurabilitySpeed.config.minimumSpeed;
            cir.setReturnValue(block.getMaterial() != Material.IRON && block.getMaterial() != Material.IRON2 && block.getMaterial() != Material.STONE ? super.getMiningSpeedMultiplier(stack, block) : this.miningSpeed * multiplier);
        }
    }
}
