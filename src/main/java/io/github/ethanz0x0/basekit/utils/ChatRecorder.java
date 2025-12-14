package io.github.ethanz0x0.basekit.utils;

import io.github.ethanz0x0.basekit.data.ChatRecord;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ChatRecorder {

    private static ArrayList<ChatRecord> chatRecords = new ArrayList<>();

    public static void record(Player player, String message) {
        chatRecords.add(new ChatRecord(player, message, LocalDateTime.now()));
    }

    public static void clear() {
        chatRecords.clear();
    }

    public static void clear(Player player) {
        chatRecords.removeIf(chatRecord -> chatRecord.sender().equals(player));
    }

    public static List<ChatRecord> getRecords(Player player) {
        return chatRecords.stream().filter(rec -> rec.sender().equals(player)).toList();
    }
}
