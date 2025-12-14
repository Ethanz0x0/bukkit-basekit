package io.github.ethanz0x0.basekit.commands;

import io.github.ethanz0x0.basekit.config.BuiltinPlaceholders;
import io.github.ethanz0x0.basekit.config.Messages;
import io.github.ethanz0x0.basekit.utils.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class SimpleCommand extends Command {

    private final String permission;

    public SimpleCommand(String name, String permission, String... aliases) {
        super(name);
        this.permission = permission;
        setAliases(List.of(aliases));
    }

    public abstract void onExecute(CommandSender sender, String entrance, String[] args);

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!sender.hasPermission(permission)) {
            MessageUtil.sendMessage(sender, Messages.getPrefixedMessage("command-no-permission"));
            return true;
        }
        onExecute(sender, commandLabel, args);
        return false;
    }

    public void usage(CommandSender sender, String usage) {
        MessageUtil.sendMessage(sender, Messages.getPrefixedMessage("command-usage",
                BuiltinPlaceholders.builder()
                        .append("usage", "/" + getName() + " " + usage)
                        .build()));
    }

    public void playerNotExist(CommandSender sender, String who) {
        MessageUtil.sendMessage(sender, Messages.getPrefixedMessage("player-not-exist",
                BuiltinPlaceholders.builder()
                        .append("player", who)
                        .build()));
    }

    public void playerNotOnline(CommandSender sender, String who) {
        MessageUtil.sendMessage(sender, Messages.getPrefixedMessage("player-not-online",
                BuiltinPlaceholders.builder()
                        .append("player", who)
                        .build()));
    }

    public void argumentTypeMismatch(CommandSender sender, int index, Class<?> clazz) {
        MessageUtil.sendMessage(sender, Messages.getPrefixedMessage("argument-type-mismatch",
                BuiltinPlaceholders.builder()
                        .append("index", index)
                        .append("type", clazz.getSimpleName())
                        .build()));
    }
}
