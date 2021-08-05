package amymialee.durabilityspeed.mixin;

import amymialee.durabilityspeed.DurabilitySpeed;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SwordItem.class)
public class SwordMixin extends Item {
    @Shadow private float attackMultiplier;
    @Shadow @Final private ToolMaterialType material;

    public SwordMixin(int i, ToolMaterialType toolMaterialType) {
        super(i);
        this.maxCount = 1;
        this.setMaxDamage(toolMaterialType.getMaxDurability());
        this.setItemGroup(ItemGroup.COMBAT);
        this.attackMultiplier = 4.0F + toolMaterialType.getAttackMultiplier();
    }

    @Inject(method = "getMiningSpeedMultiplier", at = @At("RETURN"), cancellable = true)
    private void getMiningSpeedMultiplier(ItemStack stack, Block block, CallbackInfoReturnable<Float> cir) {
        if (DurabilitySpeed.config.modEnabled) {
            float multiplier = ((1 - ((float) stack.getDamage() / (float) stack.getMaxDamage())) *
                    DurabilitySpeed.config.maximumSpeed - DurabilitySpeed.config.minimumSpeed) + DurabilitySpeed.config.minimumSpeed;
            if (block == Block.WEB) {
                cir.setReturnValue(15.0F * multiplier);
            } else {
                net.minecraft.block.Material material = block.material;
                cir.setReturnValue(material != net.minecraft.block.Material.PLANT && material != net.minecraft.block.Material.REPLACEABLE_PLANT && material != net.minecraft.block.Material.SWORD && material != net.minecraft.block.Material.FOILAGE && material != net.minecraft.block.Material.PUMPKIN ? 1.0F : 1.5F * multiplier);
            }
        }
    }
}
