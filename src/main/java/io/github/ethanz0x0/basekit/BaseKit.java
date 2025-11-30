package io.github.ethanz0x0.basekit;

import io.github.ethanz0x0.basekit.config.Config;
import org.bukkit.plugin.java.JavaPlugin;

public class BaseKit extends JavaPlugin {

    private static BaseKit instance;

    public static BaseKit getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        getLogger().info("BaseKit is loading...");
        Config.getMainConfig().get("");
    }

    @Override
    public void onDisable() {

    }
}
