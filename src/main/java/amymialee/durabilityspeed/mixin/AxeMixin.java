package amymialee.durabilityspeed.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.item.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.Set;

@Mixin(AxeItem.class)
public class AxeMixin extends MiningToolItem {
    @Shadow @Final private static Set<Material> field_23139;

    protected AxeMixin(float attackDamage, float attackSpeed, ToolMaterial material, Set<Block> effectiveBlocks, Settings settings) {
        super(attackDamage, attackSpeed, material, effectiveBlocks, settings);
    }

    @Inject(method = "getMiningSpeedMultiplier", at = @At("RETURN"), cancellable = true)
    private void getMiningSpeedMultiplier(ItemStack stack, BlockState state, CallbackInfoReturnable<Float> cir) {
        Material material = state.getMaterial();
        float multiplier = 1;
        multiplier = (1 - ((float) stack.getDamage() / (float) stack.getMaxDamage())) * 2;
        cir.setReturnValue(field_23139.contains(material) ? this.miningSpeed * multiplier : super.getMiningSpeedMultiplier(stack, state));
    }
}
