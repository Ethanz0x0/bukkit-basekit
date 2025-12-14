package io.github.ethanz0x0.basekit.listeners;

import io.github.ethanz0x0.basekit.config.BuiltinPlaceholders;
import io.github.ethanz0x0.basekit.config.Module;
import io.github.ethanz0x0.basekit.utils.MessageUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerCommandListener implements Listener {

    private static final HashMap<Player, Long> lastExecution = new HashMap<>();

    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        Module.COMMAND_EXECUTION_LIMITER.ifEnabled(() -> {
            if (event.isCancelled()) {
                return;
            }
            if ((System.currentTimeMillis() - lastExecution.getOrDefault(player, 0L)) / 1000L <
                    Module.COMMAND_EXECUTION_LIMITER.getOption("interval", 3)) {
                event.setCancelled(true);
                MessageUtil.sendMessage(player, Module.HELP_COMMAND.getText(player, "prompt-message"));
            } else {
                lastExecution.put(player, System.currentTimeMillis());
            }
        });

        Module.HELP_COMMAND.ifEnabled(() -> {
            if (event.isCancelled()) {
                return;
            }
            if (!"help".equalsIgnoreCase(event.getMessage().split(" ")[0].toLowerCase().substring(1))) {
                return;
            }
            MessageUtil.sendMessage(player, Module.HELP_COMMAND.getText(player, "message",
                    BuiltinPlaceholders.builder()
                            .player(player).build()));
            event.setCancelled(true);
        });

        Module.DISABLED_COMMANDS.ifEnabled(() -> {
            if (event.isCancelled()) {
                return;
            }
            String command = event.getMessage().split(" ")[0].toLowerCase().substring(1);
            if (Module.HELP_COMMAND.isEnabled() && "help".equalsIgnoreCase(command)) {
                return;
            }
            if (player.hasPermission(Module.DISABLED_COMMANDS.getOption("bypass-permission", ""))) {
                return;
            }
            ArrayList<String> disabledCommands = Module.DISABLED_COMMANDS.getOption("commands", new ArrayList<>());
            if (disabledCommands.contains(command)) {
                event.setCancelled(true);
                MessageUtil.sendMessage(player, Module.DISABLED_COMMANDS.getText(player, "prompt-message",
                        BuiltinPlaceholders.builder().append("command", command)
                                .build()));
                event.setCancelled(true);
            }
        });
    }
}
