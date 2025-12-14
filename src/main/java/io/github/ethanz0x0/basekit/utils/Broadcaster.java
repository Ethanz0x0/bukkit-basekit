package io.github.ethanz0x0.basekit.utils;

import io.github.ethanz0x0.basekit.data.SoundData;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Broadcaster {

    public static void broadcastMessage(String message) {
        for (Player players : Bukkit.getOnlinePlayers()) {
            MessageUtil.sendMessage(players, PlaceholderAPIHook.isHooked() ?
                    PlaceholderAPI.setPlaceholders(players, message) : message);
        }
    }

    public static void broadcastSound(SoundData soundData) {
        for (Player players : Bukkit.getOnlinePlayers()) {
            soundData.playFor(players);
        }
    }
}
