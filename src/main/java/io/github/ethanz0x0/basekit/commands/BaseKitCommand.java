package io.github.ethanz0x0.basekit.commands;

import io.github.ethanz0x0.basekit.config.Config;
import io.github.ethanz0x0.basekit.config.Messages;
import io.github.ethanz0x0.basekit.schedulers.UpdateCheckerScheduler;
import io.github.ethanz0x0.basekit.utils.MessageUtil;
import io.github.ethanz0x0.basekit.utils.UpdateChecker;
import org.bukkit.command.CommandSender;

public class BaseKitCommand extends SimpleCommand {

    public BaseKitCommand() {
        super("basekit", "basekit.command.main", "bk");
    }

    @Override
    public void onExecute(CommandSender sender, String entrance, String[] args) {
        if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "version", "ver" -> {
                    MessageUtil.sendMessage(sender, Messages.getPrefixedMessage("command-check-update-checking"));
                    UpdateChecker.check().thenAccept(latest -> {
                        UpdateCheckerScheduler.NEED_UPDATE = !latest;
                        if (latest) {
                            MessageUtil.sendMessage(sender, Messages.getPrefixedMessage("command-check-update-latest"));
                        } else {
                            MessageUtil.sendMessage(sender, Messages.getPrefixedMessage("update-checker-reminder"));
                        }
                    });
                    return;
                }
                case "reload" -> {
                    MessageUtil.sendMessage(sender, Messages.getPrefixedMessage("command-reload-performing"));
                    Config.getMainConfig().reload();
                    Config.getMessagesConfig().reload();
                    Config.getStartupInfoConfig().reload();
                    MessageUtil.sendMessage(sender, Messages.getPrefixedMessage("command-reload-success"));
                    return;
                }
            }
        }

        sender.sendMessage("Under development...");
    }
}
