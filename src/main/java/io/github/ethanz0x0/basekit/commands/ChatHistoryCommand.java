package io.github.ethanz0x0.basekit.commands;

import io.github.ethanz0x0.basekit.config.Messages;
import io.github.ethanz0x0.basekit.config.Module;
import io.github.ethanz0x0.basekit.utils.MessageUtil;
import org.bukkit.command.CommandSender;

public class ChatHistoryCommand extends SimpleCommand {

    public ChatHistoryCommand() {
        super("chathistory", "basekit.command.chathistory");
    }

    @Override
    public void onExecute(CommandSender sender, String entrance, String[] args) {
        if (!Module.CHAT_HISTORY.isEnabled()) {
            MessageUtil.sendMessage(sender, Messages.getPrefixedMessage("command-chat-history-unavailable"));
            return;
        }

        if (args.length == 1) {

        }

        usage(sender, "<player name> [page]");
    }
}
