package amymialee.durabilityspeed.mixin;

import amymialee.durabilityspeed.DurabilitySpeed;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterialType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.Set;

@Mixin(PickaxeItem.class)
public class PickaxeItemMixin extends ToolItem {
    @Shadow private static Block[] field_13366;
    protected PickaxeItemMixin(int i, ToolMaterialType toolMaterialType) {
        super(i, 2.0F, toolMaterialType, field_13366);
    }

    @Inject(method = "getMiningSpeedMultiplier", at = @At("RETURN"), cancellable = true)
    private void getMiningSpeedMultiplier(ItemStack stack, Block block, CallbackInfoReturnable<Float> cir) {
        if (DurabilitySpeed.config.modEnabled) {

            float multiplier = ((1 - ((float) stack.getDamage() / (float) stack.getMaxDamage())) * DurabilitySpeed.config.maximumSpeed - DurabilitySpeed.config.minimumSpeed) + DurabilitySpeed.config.minimumSpeed;
            cir.setReturnValue(block == null || block.material != net.minecraft.block.Material.IRON && block.material != net.minecraft.block.Material.IRON2 && block.material != net.minecraft.block.Material.STONE ? super.getMiningSpeedMultiplier(stack, block) : this.miningSpeed * multiplier);
        }
    }
}
