package io.github.ethanz0x0.basekit.listeners;

import io.github.ethanz0x0.basekit.config.BuiltinPlaceholders;
import io.github.ethanz0x0.basekit.config.Module;
import io.github.ethanz0x0.basekit.data.ChatRecord;
import io.github.ethanz0x0.basekit.utils.Broadcaster;
import io.github.ethanz0x0.basekit.utils.ChatRecorder;
import io.github.ethanz0x0.basekit.utils.MessageUtil;
import io.github.ethanz0x0.nucleus.object.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ChatListener implements Listener {

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        String message = event.getMessage();
        Module.CHAT_BLOCK_BAD_WORDS.ifEnabled(() -> {
            if (event.isCancelled()) {
                return;
            }

            for (String keyWord : Module.CHAT_BLOCK_BAD_WORDS.getOption("key-words", new ArrayList<String>())) {
                if (message.toLowerCase().contains(keyWord.toLowerCase())) {
                    event.setCancelled(true);
                    MessageUtil.sendMessage(player, Module.CHAT_BLOCK_BAD_WORDS.getText("prompt-message"));
                    return;
                }
            }
        });

        Module.CHAT_HISTORY.ifEnabled(() -> {
            Module.CHAT_BLOCK_SIMILAR_MESSAGE.ifEnabled(() -> {
                List<ChatRecord> records = ChatRecorder.getRecords(player);
                ChatRecord lastMessage = records.get(records.size() - 1);

                if (Duration.between(LocalDateTime.now(), lastMessage.time()).getSeconds() <= 120) {
                    if (StringUtil.calculateSimilarity(lastMessage.message(), message) >=
                            Module.CHAT_BLOCK_SIMILAR_MESSAGE.getOption("similarity", 0.7D)) {
                        event.setCancelled(true);
                        MessageUtil.sendMessage(player, Module.CHAT_BLOCK_SIMILAR_MESSAGE.getText("prompt-message"));
                    }
                }
            });

            if (event.isCancelled()) {
                return;
            }

            ChatRecorder.record(player, message);
        });

        Module.CHAT_MESSAGE_FORMAT.ifEnabled(() -> {
            if (event.isCancelled()) {
                return;
            }

            event.setCancelled(true);
            Broadcaster.broadcastMessage(Module.CHAT_MESSAGE_FORMAT.getText("format",
                    BuiltinPlaceholders.builder()
                            .player(player)
                            .append("message", message)
                            .build()));
            if (Module.CHAT_MESSAGE_FORMAT.getOption("console-output", false)) {
                Bukkit.getLogger().info("[CHAT] <" + player.getName() + "> " + message);
            }
        });
    }
}
