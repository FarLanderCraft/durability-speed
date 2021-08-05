package amymialee.durabilityspeed.mixin;

import amymialee.durabilityspeed.DurabilitySpeed;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.item.tool.Shears;
import net.minecraft.tile.Tile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Shears.class)
public class ShearsMixin extends ItemType {
    public ShearsMixin(int hi) {super(hi);}

    //@Inject(method = "method_438", at = @At("RETURN"), cancellable = true)
    /***/
    @Overwrite
    public float method_438(ItemInstance stack, Tile block) {
        //if (DurabilitySpeed.config.modEnabled) {
            float multiplier = ((1 - ((float) stack.getDamage() / (float) 238)) * 2.0f - 0.0f) + 0.0f;
            System.out.println(multiplier);
            if (!(block == Tile.WEB) && !(block == Tile.LEAVES)) {
                return block.id == Tile.WOOL.id ? 5.0F * multiplier : super.method_438(stack, block);
            } else {
                return 5.0F * multiplier;
            }
        //}
    }
}
