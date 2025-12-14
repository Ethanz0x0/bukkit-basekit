package io.github.ethanz0x0.basekit;

import io.github.ethanz0x0.basekit.commands.BaseKitCommand;
import io.github.ethanz0x0.basekit.config.Config;
import io.github.ethanz0x0.basekit.listeners.ChatListener;
import io.github.ethanz0x0.basekit.listeners.PlayerCommandListener;
import io.github.ethanz0x0.basekit.listeners.PlayerJoinQuitListener;
import io.github.ethanz0x0.basekit.schedulers.UpdateCheckerScheduler;
import io.github.ethanz0x0.basekit.utils.CommandUtil;
import io.github.ethanz0x0.basekit.utils.PlaceholderAPIHook;
import io.github.ethanz0x0.basekit.utils.StartupInfo;
import io.github.ethanz0x0.basekit.utils.VersionHelper;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class BaseKit extends JavaPlugin {

    private static BaseKit instance;

    public static BaseKit getInstance() {
        return instance;
    }

    private BukkitAudiences adventure;

    public BukkitAudiences adventure() {
        return adventure;
    }

    @Override
    public void onEnable() {
        instance = this;
        adventure = BukkitAudiences.create(this);

        getLogger().info("---+-------------------- BaseKit --------------------+---");
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
            getLogger().info("you can check the full changelog at " +
                    "https://github.com/Ethanz0x0/bukkit-basekit/releases/tag/" + VersionHelper.getMainVersion());
            getLogger().info("-------------------------------------------------------");
        }

        {
            int configVersion = Config.getMainConfig().getInteger("config-version", -1);
            if (configVersion < 0 || configVersion > Config.MAIN_CONFIG_VERSION) {
                getLogger().warning("-------------------------------------------------------");
                getLogger().warning("WARNING: We couldn't check your config version correctly!");
                getLogger().warning("Maybe your configuration file is broken.");
                getLogger().warning("You can backup the old config file and delete it. The new one will be " +
                        "regenerated at the next time you restart the server.");
                getLogger().warning("-------------------------------------------------------");
            } else if (configVersion < Config.MAIN_CONFIG_VERSION) {
                getLogger().warning("-------------------------------------------------------");
                getLogger().warning("WARNING: Your configuration file is outdated.");
                getLogger().warning("Keep using old config may cause unexpected errors.");
                getLogger().warning("You can backup the old config file and delete it. The new one will be " +
                        "regenerated at the next time you restart the server.");
                getLogger().warning("-------------------------------------------------------");
            }
        }

        registerCommands();
        registerListeners();
        scheduleTasks();

        getLogger().info("Plugin is successfully loaded! Thanks for using BaseKit.");
        getLogger().info("---+-------------------------------------------------+---");
    }

    @Override
    public void onDisable() {
        instance = null;
        if (adventure != null) {
            adventure.close();
            adventure = null;
        }
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new PlayerJoinQuitListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerCommandListener(), this);
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
    }

    private void scheduleTasks() {
        getServer().getScheduler().runTaskTimer(this, new UpdateCheckerScheduler(), 0L, 10*60*20L);
    }

    private void registerCommands() {
        CommandUtil.registerCommand(new BaseKitCommand());
    }
}
