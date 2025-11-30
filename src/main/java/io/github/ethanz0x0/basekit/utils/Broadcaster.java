package io.github.ethanz0x0.basekit.utils;

import io.github.ethanz0x0.basekit.utils.sound.SoundData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Broadcaster {

    public static void broadcastMessage(String message) {
        for (Player players : Bukkit.getOnlinePlayers()) {
            players.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        }
    }

    public static void broadcastSound(SoundData soundData) {
        for (Player players : Bukkit.getOnlinePlayers()) {
            soundData.playFor(players);
        }
    }
}
