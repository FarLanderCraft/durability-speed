package amymialee.durabilityspeed;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;

public class DurabilitySpeed implements ModInitializer {
    DurabilityModConfig config;
    @Override
    public void onInitialize() {
        AutoConfig.register(DurabilityModConfig.class, Toml4jConfigSerializer::new);
        config = AutoConfig.getConfigHolder(DurabilityModConfig.class).getConfig();
    }
}

