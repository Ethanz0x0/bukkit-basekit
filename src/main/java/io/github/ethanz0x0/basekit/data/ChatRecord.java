package io.github.ethanz0x0.basekit.data;

import org.bukkit.OfflinePlayer;

import java.util.Date;

public record ChatRecord(
        OfflinePlayer sender,
        String message,
        Date time
) {
}
