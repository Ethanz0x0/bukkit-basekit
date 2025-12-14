package io.github.ethanz0x0.basekit.commands;

import io.github.ethanz0x0.basekit.config.BuiltinPlaceholders;
import io.github.ethanz0x0.basekit.config.Messages;
import io.github.ethanz0x0.basekit.config.Module;
import io.github.ethanz0x0.basekit.data.ChatRecord;
import io.github.ethanz0x0.basekit.utils.ChatRecorder;
import io.github.ethanz0x0.basekit.utils.MessageUtil;
import io.github.ethanz0x0.nucleus.Pager;
import io.github.ethanz0x0.nucleus.object.DateUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.TimeZone;

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
            OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
            outputHistory(sender, player, 1);
            return;
        }

        if (args.length == 2) {
            OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
            int page = 1;
            try {
                page = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                argumentTypeMismatch(sender, 1, Integer.class);
                return;
            }
            outputHistory(sender, player, page);
            return;
        }

        usage(sender, "<player name> [page]");
    }

    private void outputHistory(CommandSender sender, OfflinePlayer who, int page) {
        List<ChatRecord> records = ChatRecorder.getRecords(who);
        if (records.isEmpty()) {
            MessageUtil.sendMessage(sender, Messages.getMessage("command-chat-history-title",
                    BuiltinPlaceholders.builder()
                            .player(who)
                            .append("currentPage", 0)
                            .append("maxPage", 0)
                            .build()));
            return;
        }

        int maxPage = Pager.calculatePages(records.size(), 10);
        page = Math.min(maxPage, page);
        page = Math.max(page, 1);

        MessageUtil.sendMessage(sender, Messages.getMessage("command-chat-history-title",
                BuiltinPlaceholders.builder()
                        .player(who)
                        .append("currentPage", page)
                        .append("maxPage", maxPage)
                        .build()));

        List<ChatRecord> pagedRecords = Pager.getPage(records, 10, page);
        for (ChatRecord pagedRecord : pagedRecords) {
            MessageUtil.sendMessage(sender, "<white>" + DateUtil.format("yyyy-MM-dd HH-mm-ss",
                    pagedRecord.time(), TimeZone.getDefault()) + "</white> <gray>-</gray> <white>" +
                    pagedRecord.message() + "</white>");
        }
    }
}
