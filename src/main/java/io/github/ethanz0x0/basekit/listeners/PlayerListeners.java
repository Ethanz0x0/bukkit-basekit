package io.github.ethanz0x0.basekit.listeners;

import io.github.ethanz0x0.basekit.config.BuiltinPlaceholders;
import io.github.ethanz0x0.basekit.config.Module;
import io.github.ethanz0x0.basekit.utils.Broadcaster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;

public class PlayerListeners implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Module.PLAYER_JOIN.ifEnabled(() -> {
            event.setJoinMessage(null);
            Broadcaster.broadcastMessage(
                    Module.PLAYER_JOIN.getText("message",
                            BuiltinPlaceholders.builder()
                                    .player(player).build())
            );
            Broadcaster.broadcastSound(Module.PLAYER_JOIN.getSound("sound"));
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
        Module.DISABLED_COMMANDS.ifEnabled(() -> {
            if (player.hasPermission(Module.DISABLED_COMMANDS.getOption("bypass-permission", ""))) {
                return;
            }
            List<String> disabledCommands = Module.DISABLED_COMMANDS.getOption("commands", new ArrayList<>());
            String command = event.getMessage().split(" ")[0].toLowerCase().substring(1);
            if (disabledCommands.contains(command)) {
                event.setCancelled(true);
                player.sendMessage(Module.DISABLED_COMMANDS.getText("prompt-message",
                        BuiltinPlaceholders.builder().append("command", command)
                                .build()));
            }
        });
    }
}
