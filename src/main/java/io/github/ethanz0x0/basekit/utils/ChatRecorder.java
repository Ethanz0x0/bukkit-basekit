package io.github.ethanz0x0.basekit.utils;

import io.github.ethanz0x0.basekit.data.ChatRecord;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatRecorder {

    private static ArrayList<ChatRecord> chatRecords = new ArrayList<>();

    public static void record(Player player, String message) {
        chatRecords.add(new ChatRecord(player, message, new Date()));
    }

    public static void clear() {
        chatRecords.clear();
    }

    public static void clear(OfflinePlayer player) {
        chatRecords.removeIf(chatRecord -> chatRecord.sender().equals(player));
    }

    public static List<ChatRecord> getRecords(OfflinePlayer player) {
        return chatRecords.stream().filter(rec -> rec.sender().equals(player)).toList();
    }
}
