package io.github.ethanz0x0.basekit;

import org.bukkit.plugin.java.JavaPlugin;

public class BaseKit extends JavaPlugin {

    private static BaseKit instance;

    public static BaseKit getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("BaseKit is loading...");


    }

    @Override
    public void onDisable() {

    }
}
