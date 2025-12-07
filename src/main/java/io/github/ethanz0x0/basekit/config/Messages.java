package io.github.ethanz0x0.basekit.config;

import io.github.ethanz0x0.basekit.utils.PlaceholderAPIHook;
import io.github.ethanz0x0.nucleus.object.format.Formatter;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;

public class Messages {

    public static String getPrefix() {
        return ChatColor.translateAlternateColorCodes('&', Config.getMessagesConfig().getString("prefix"));
    }

    public static String getMessage(String path) {
        return ChatColor.translateAlternateColorCodes('&', Config.getMessagesConfig().getString(path));
    }

    public static String getMessage(String path, BuiltinPlaceholders builtinPlaceholders) {
        return Formatter.format(getMessage(path), builtinPlaceholders.asPlaceholderFormat());
    }

    public static String getMessage(OfflinePlayer player, String path) {
        String result = getMessage(path);
        if (PlaceholderAPIHook.isHooked()) {
            return PlaceholderAPI.setPlaceholders(player, result);
        } else {
            return result;
        }
    }

    public static String getMessage(OfflinePlayer player, String path, BuiltinPlaceholders builtinPlaceholders) {
        return Formatter.format(getMessage(player, path), builtinPlaceholders.asPlaceholderFormat());
    }

    public static String getPrefixedMessage(String path) {
        return getPrefix() + getMessage(path);
    }

    public static String getPrefixedMessage(String path, BuiltinPlaceholders builtinPlaceholders) {
        return getPrefix() + getMessage(path, builtinPlaceholders);
    }

    public static String getPrefixedMessage(OfflinePlayer player, String path) {
        return getPrefix() + getMessage(player, path);
    }

    public static String getPrefixedMessage(OfflinePlayer player, String path, BuiltinPlaceholders builtinPlaceholders) {
        return getPrefix() + getMessage(player, path, builtinPlaceholders);
    }

}
