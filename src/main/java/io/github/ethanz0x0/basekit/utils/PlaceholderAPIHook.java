package io.github.ethanz0x0.basekit.utils;

import io.github.ethanz0x0.basekit.config.Config;
import org.bukkit.Bukkit;

public class PlaceholderAPIHook {

    private static boolean hooked = false;

    public static boolean isHooked() {
        return hooked;
    }

    public static boolean checkAndHook() {
        if (!Config.getMainConfig().getBoolean("placeholderapi-hook")) {
            return true;
        }
        hooked = Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
        return hooked;
    }
}
