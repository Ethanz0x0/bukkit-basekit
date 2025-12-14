package io.github.ethanz0x0.basekit.data;

import org.bukkit.entity.Player;

import java.time.LocalDateTime;

public record ChatRecord(
        Player sender,
        String message,
        LocalDateTime time
) {
}
