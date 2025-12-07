package io.github.ethanz0x0.basekit;

import io.github.ethanz0x0.basekit.listeners.PlayerListeners;
import io.github.ethanz0x0.basekit.schedulers.UpdateCheckerScheduler;
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
        getLogger().info("Minecraft version: " + Bukkit.getBukkitVersion());
        getLogger().info("Plugin version: " + VersionHelper.getFullVersion());
        StartupInfo.checkAndCorrect();
        PlaceholderAPIHook.checkAndHook();
        if (VersionHelper.getFullVersion().endsWith("unknown")) {
            getLogger().warning("-------------------------------------------------------");
            getLogger().warning("Couldn't detect your plugin version, maybe this is a custom build?");
            getLogger().warning("Please note that we are not responsible for any consequences arising " +
                    "from custom builds.");
            getLogger().warning("You can download the latest official version at " +
                    "https://github.com/Ethanz0x0/bukkit-basekit/releases/tag/" + VersionHelper.getMainVersion());
            getLogger().warning("-------------------------------------------------------");
        }
        if (StartupInfo.isFirstStart()) {
            getLogger().info("-------------------------------------------------------");
            getLogger().info("It seems that you are launching the current " +
                    "version of the plugin for the first time, ");
            getLogger().info("please check the full changelog at " +
                    "https://github.com/Ethanz0x0/bukkit-basekit/releases/tag/" + VersionHelper.getMainVersion());
            getLogger().info("-------------------------------------------------------");
        }
        getServer().getPluginManager().registerEvents(new PlayerListeners(), this);
        getServer().getScheduler().runTaskTimer(this, new UpdateCheckerScheduler(), 0L, 10*60*20L);
    }

    @Override
    public void onDisable() {

    }
}
