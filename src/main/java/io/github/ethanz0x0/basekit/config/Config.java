package io.github.ethanz0x0.basekit.config;

import io.github.ethanz0x0.basekit.BaseKit;
import io.github.ethanz0x0.nucleus.config.ConfigurationException;
import io.github.ethanz0x0.nucleus.config.FileConfiguration;
import io.github.ethanz0x0.nucleus.config.adapters.YamlConfigurationAdapter;

import java.io.File;
import java.io.InputStream;

public class Config {

    public static final int MAIN_CONFIG_VERSION = 1;

    private static final BaseKit plugin = BaseKit.getInstance();
    private static final FileConfiguration mainConfig;
    private static final FileConfiguration messagesConfig;
    private static final FileConfiguration startupInfoConfig;

    static {
        File pluginDirectory = plugin.getDataFolder();
        if (!pluginDirectory.exists()) {
            pluginDirectory.mkdir();
        }

        mainConfig = initConfig(new File(pluginDirectory, "config.yml"),
                Config.class.getResourceAsStream("/config.yml"));
        messagesConfig = initConfig(new File(pluginDirectory, "messages.yml"),
                Config.class.getResourceAsStream("/messages.yml"));
        startupInfoConfig = initConfig(new File(pluginDirectory, "STARTUP_INFO"), null);

    }

    private static FileConfiguration initConfig(File file, InputStream in) {
        try {
            return new FileConfiguration(file, in, YamlConfigurationAdapter.class);
        } catch (ConfigurationException e) {
            plugin.getLogger().warning("Failed to init config: " + file.getName());
            throw new RuntimeException(e);
        }
    }

    public static FileConfiguration getMainConfig() {
        return mainConfig;
    }

    public static FileConfiguration getMessagesConfig() {
        return messagesConfig;
    }

    public static FileConfiguration getStartupInfoConfig() {
        return startupInfoConfig;
    }
}
