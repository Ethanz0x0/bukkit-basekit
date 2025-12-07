package io.github.ethanz0x0.basekit;

import io.github.ethanz0x0.basekit.listeners.PlayerListeners;
import io.github.ethanz0x0.basekit.utils.PlaceholderAPIHook;
import io.github.ethanz0x0.basekit.utils.StartupInfo;
import io.github.ethanz0x0.basekit.utils.VersionHelper;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class BaseKit extends JavaPlugin {

    private static BaseKit instance;

    public static BaseKit getInstance() {
        return instance;
    }


    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("-------------------- BaseKit --------------------");
        getLogger().info("Minecraft version: ");
        StartupInfo.checkAndCorrect();
        PlaceholderAPIHook.checkAndHook();
        System.out.println(Bukkit.getVersion());
        System.out.println(Bukkit.getBukkitVersion());
        if (StartupInfo.isFirstStart()) {
            getLogger().info("-------------------------------------------------------");
            getLogger().info("It seems that you are launching the current " +
                    "version of the plugin for the first time, ");
            getLogger().info("please check the full changelog at " +
                    "https://github.com/Ethanz0x0/bukkit-basekit/releases/tag/" + VersionHelper.getMainVersion());
            getLogger().info("-------------------------------------------------------");
        }
        getServer().getPluginManager().registerEvents(new PlayerListeners(), this);
    }

    @Override
    public void onDisable() {

    }
}
