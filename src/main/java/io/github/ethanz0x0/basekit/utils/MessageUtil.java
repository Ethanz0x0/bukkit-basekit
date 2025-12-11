package io.github.ethanz0x0.basekit.utils;

import io.github.ethanz0x0.basekit.BaseKit;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageUtil {

    public static void sendMessage(CommandSender sender, Component message) {
        BaseKit.getInstance().adventure().sender(sender).sendMessage(message);
    }

    public static void sendMessage(CommandSender sender, String message) {
        sendMessage(sender, MiniMessage.miniMessage().deserialize(message));
    }

    public static void sendMessage(Player player, Component message) {
        BaseKit.getInstance().adventure().player(player).sendMessage(message);
    }

    public static void sendMessage(Player player, String message) {
        sendMessage(player, MiniMessage.miniMessage().deserialize(message));
    }
}
