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
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinQuitListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (UpdateCheckerScheduler.NEED_UPDATE && player.hasPermission("basekit.update-checker")) {
            MessageUtil.sendMessage(player, Messages.getPrefixedMessage("update-checker-reminder"));
        }
        io.github.ethanz0x0.basekit.config.Module.PLAYER_JOIN.ifEnabled(() -> {
            event.setJoinMessage(null);
            Broadcaster.broadcastMessage(
                    io.github.ethanz0x0.basekit.config.Module.PLAYER_JOIN.getText(player, "message",
                            BuiltinPlaceholders.builder()
                                    .player(player).build())
            );
            Broadcaster.broadcastSound(io.github.ethanz0x0.basekit.config.Module.PLAYER_JOIN.getSound("sound"));
        });
        io.github.ethanz0x0.basekit.config.Module.WELCOME_MESSAGE.ifEnabled(() -> {
            if (player.hasPlayedBefore()) {
                MessageUtil.sendMessage(player, io.github.ethanz0x0.basekit.config.Module.WELCOME_MESSAGE.getText(player, "welcome-message",
                        BuiltinPlaceholders.builder()
                                .player(player).build()));
            } else {
                MessageUtil.sendMessage(player, io.github.ethanz0x0.basekit.config.Module.WELCOME_MESSAGE.getText(player, "first-join-message",
                        BuiltinPlaceholders.builder()
                                .player(player).build()));
            }
        });
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        io.github.ethanz0x0.basekit.config.Module.PLAYER_QUIT.ifEnabled(() -> {
            event.setQuitMessage(null);
            Broadcaster.broadcastMessage(
                    io.github.ethanz0x0.basekit.config.Module.PLAYER_QUIT.getText(player, "message",
                            BuiltinPlaceholders.builder()
                                    .player(player).build())
            );
            Broadcaster.broadcastSound(Module.PLAYER_QUIT.getSound("sound"));
        });
    }
}
