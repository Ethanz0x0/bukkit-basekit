package io.github.ethanz0x0.basekit.commands;

import io.github.ethanz0x0.basekit.config.Messages;
import io.github.ethanz0x0.basekit.utils.MessageUtil;
import io.github.ethanz0x0.basekit.utils.UpdateChecker;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BaseKitCommand extends Command {

    public BaseKitCommand() {
        super("basekit");
        setAliases(List.of("bk"));
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!sender.hasPermission("basekit.command.main")) {
            MessageUtil.sendMessage(sender, Messages.getPrefixedMessage("command-no-permission"));
            return true;
        }

        if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "version" -> {
                    MessageUtil.sendMessage(sender, Messages.getPrefixedMessage("command-check-update-checking"));
                    UpdateChecker.check().thenAccept(latest -> {
                        if (latest) {
                            MessageUtil.sendMessage(sender, Messages.getPrefixedMessage("command-check-update-latest"));
                        } else {
                            MessageUtil.sendMessage(sender, Messages.getPrefixedMessage("update-checker-reminder"));
                        }
                    });
                    return true;
                }
            }
        }

        sender.sendMessage("Under development...");
        return false;
    }
}
