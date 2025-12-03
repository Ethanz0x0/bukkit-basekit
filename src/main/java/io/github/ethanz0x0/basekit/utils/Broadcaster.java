package io.github.ethanz0x0.basekit.utils;

import io.github.ethanz0x0.basekit.utils.sound.SoundData;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Broadcaster {

    public static void broadcastMessage(String message) {
        String msg = ChatColor.translateAlternateColorCodes('&', message);
        for (Player players : Bukkit.getOnlinePlayers()) {
            players.sendMessage(PlaceholderAPIHook.isHooked() ? PlaceholderAPI.setPlaceholders(players, msg) : msg);
        }
    }

    public static void broadcastSound(SoundData soundData) {
        for (Player players : Bukkit.getOnlinePlayers()) {
            soundData.playFor(players);
        }
    }
}
