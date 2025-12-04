package io.github.ethanz0x0.basekit.utils;

import io.github.ethanz0x0.basekit.config.Config;
import io.github.ethanz0x0.nucleus.config.FileConfiguration;

public class StartupInfo {

    public static void checkAndCorrect() {
        FileConfiguration si = Config.getStartupInfoConfig();
        String lastVersion = si.getString("launch-version", "undefined");
        String currentVersion = VersionHelper.getFullVersion();
        if (currentVersion.equalsIgnoreCase(lastVersion)) {
            si.set("first-start", false);
        } else {
            si.set("launch-version", currentVersion);
            si.set("first-start", true);
        }
        si.save();
    }

    public static boolean isFirstStart() {
        return Config.getStartupInfoConfig().getBoolean("first-start");
    }
}
