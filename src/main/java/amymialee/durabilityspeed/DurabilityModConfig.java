package amymialee.durabilityspeed;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "durabilityspeed")
public
class DurabilityModConfig implements ConfigData {
    public boolean effectSwords = true;
    public boolean effectPickaxes = true;
    public boolean effectAxes = true;
    public boolean effectShovels = true;
    public boolean effectOther = true;
    public float maximumSpeed = 2;
    public float minimumSpeed = 0;
}
