package io.github.ethanz0x0.basekit;

import io.github.ethanz0x0.basekit.listeners.PlayerListeners;
import io.github.ethanz0x0.basekit.utils.PlaceholderAPIHook;
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
        PlaceholderAPIHook.checkAndHook();
        getServer().getPluginManager().registerEvents(new PlayerListeners(), this);
    }

    @Override
    public void onDisable() {

    }
}
