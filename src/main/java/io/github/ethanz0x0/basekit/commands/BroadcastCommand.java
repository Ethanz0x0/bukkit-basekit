package io.github.ethanz0x0.basekit.commands;

import io.github.ethanz0x0.basekit.utils.Broadcaster;
import io.github.ethanz0x0.nucleus.object.StringUtil;
import org.bukkit.command.CommandSender;

public class BroadcastCommand extends SimpleCommand{

    public BroadcastCommand() {
        super("broadcast", "basekit.command.broadcast");
    }

    @Override
    public void onExecute(CommandSender sender, String entrance, String[] args) {
        if (args.length < 1) {
            usage(sender, "<message>");
            return;
        }

        Broadcaster.broadcastMessage(StringUtil.join(args, 0, " "));
    }
}
