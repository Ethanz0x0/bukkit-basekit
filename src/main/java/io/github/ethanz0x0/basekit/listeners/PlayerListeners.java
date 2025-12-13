package io.github.ethanz0x0.basekit.listeners;

import io.github.ethanz0x0.basekit.config.BuiltinPlaceholders;
import io.github.ethanz0x0.basekit.config.Messages;
import io.github.ethanz0x0.basekit.config.Module;
import io.github.ethanz0x0.basekit.schedulers.UpdateCheckerScheduler;
import io.github.ethanz0x0.basekit.utils.Broadcaster;
import io.github.ethanz0x0.basekit.utils.MessageUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;

public class PlayerListeners implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (UpdateCheckerScheduler.NEED_UPDATE && player.hasPermission("basekit.update-checker")) {
            MessageUtil.sendMessage(player, Messages.getPrefixedMessage("update-checker-reminder"));
        }
        Module.PLAYER_JOIN.ifEnabled(() -> {
            event.setJoinMessage(null);
            Broadcaster.broadcastMessage(
                    Module.PLAYER_JOIN.getText(player, "message",
                            BuiltinPlaceholders.builder()
                                    .player(player).build())
            );
            Broadcaster.broadcastSound(Module.PLAYER_JOIN.getSound("sound"));
        });
        Module.WELCOME_MESSAGE.ifEnabled(() -> {
            if (player.hasPlayedBefore()) {
                MessageUtil.sendMessage(player, Module.WELCOME_MESSAGE.getText(player, "welcome-message",
                        BuiltinPlaceholders.builder()
                                .player(player).build()));
            } else {
                MessageUtil.sendMessage(player, Module.WELCOME_MESSAGE.getText(player, "first-join-message",
                        BuiltinPlaceholders.builder()
                                .player(player).build()));
            }
        });
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Module.PLAYER_QUIT.ifEnabled(() -> {
            event.setQuitMessage(null);
            Broadcaster.broadcastMessage(
                    Module.PLAYER_QUIT.getText(player, "message",
                            BuiltinPlaceholders.builder()
                                    .player(player).build())
            );
            Broadcaster.broadcastSound(Module.PLAYER_QUIT.getSound("sound"));
        });
    }

    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        Module.HELP_COMMAND.ifEnabled(() -> {
            if (!"help".equalsIgnoreCase(event.getMessage().split(" ")[0].toLowerCase().substring(1))) {
                return;
            }
            MessageUtil.sendMessage(player, Module.HELP_COMMAND.getText(player, "message",
                    BuiltinPlaceholders.builder()
                            .player(player).build()));
            event.setCancelled(true);
        });

        Module.DISABLED_COMMANDS.ifEnabled(() -> {
            String command = event.getMessage().split(" ")[0].toLowerCase().substring(1);
            if (Module.HELP_COMMAND.isEnabled() && "help".equalsIgnoreCase(command)) {
                return;
            }
            if (player.hasPermission(Module.DISABLED_COMMANDS.getOption("bypass-permission", ""))) {
                return;
            }
            List<String> disabledCommands = Module.DISABLED_COMMANDS.getOption("commands", new ArrayList<>());
            if (disabledCommands.contains(command)) {
                event.setCancelled(true);
                MessageUtil.sendMessage(player, Module.DISABLED_COMMANDS.getText(player, "prompt-message",
                        BuiltinPlaceholders.builder().append("command", command)
                                .build()));
                event.setCancelled(true);
            }
        });
    }

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

        Module.CHAT_BLOCK_SIMILAR_MESSAGE.ifEnabled(() -> {
            if (event.isCancelled()) {
                return;
            }

            // TODO impl
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
        });
    }
}
