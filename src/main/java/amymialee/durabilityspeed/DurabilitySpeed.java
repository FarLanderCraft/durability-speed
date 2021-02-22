package amymialee.durabilityspeed;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;

public class DurabilitySpeed implements ModInitializer {
    public static DurabilityModConfig configGet;
    @Override
    public void onInitialize() {
        AutoConfig.register(DurabilityModConfig.class, GsonConfigSerializer::new);
        configGet = AutoConfig.getConfigHolder(DurabilityModConfig.class).getConfig();
    }
}

