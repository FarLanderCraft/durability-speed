package amymialee.durabilityspeed.mixin;

import amymialee.durabilityspeed.DurabilityModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

@Mixin(PickaxeItem.class)
public class PickaxeMixin extends MiningToolItem {
    protected PickaxeMixin(float attackDamage, float attackSpeed, ToolMaterial material, Set<Block> effectiveBlocks, Settings settings) {
        super(attackDamage, attackSpeed, material, effectiveBlocks, settings);
    }

    public DurabilityModConfig config = AutoConfig.getConfigHolder(DurabilityModConfig.class).getConfig();

    @Inject(method = "getMiningSpeedMultiplier", at = @At("RETURN"), cancellable = true)
    private void getMiningSpeedMultiplier(ItemStack stack, BlockState state, CallbackInfoReturnable<Float> cir) {
        if (config.effectPickaxes) {
            Material material = state.getMaterial();
            float multiplier = 1;
            multiplier = ((1 - ((float) stack.getDamage() / (float) stack.getMaxDamage())) * (config.maximumSpeed - config.minimumSpeed)) + config.minimumSpeed;
            cir.setReturnValue(material != Material.METAL && material != Material.REPAIR_STATION && material != Material.STONE ? super.getMiningSpeedMultiplier(stack, state) : this.miningSpeed * multiplier);
        }
    }
}
