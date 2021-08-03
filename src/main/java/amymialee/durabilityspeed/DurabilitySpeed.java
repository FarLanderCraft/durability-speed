package amymialee.durabilityspeed;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.api.ModInitializer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

public class DurabilitySpeed implements ModInitializer {
    public static DurabilityModConfig config;
    DurabilityModConfig daDataForReal = new DurabilityModConfig(true, 2, 0);
    public Gson daData = new GsonBuilder().setPrettyPrinting().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
    Path configPath = Paths.get("config/durability-speed.json");

    public void saveDaData() {
        try{
            if (configPath.toFile().exists()) {
                config = daData.fromJson(new String(Files.readAllBytes(configPath)), DurabilityModConfig.class);
            } else {
                Files.write(configPath, Collections.singleton(daData.toJson(daDataForReal)));
                config = daDataForReal;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onInitialize() {
        saveDaData();
    }
}
