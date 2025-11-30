package io.github.ethanz0x0.basekit.config;

import io.github.ethanz0x0.nucleus.object.format.presets.PlaceholderFormat;
import org.bukkit.OfflinePlayer;

import java.util.HashMap;
import java.util.Map;

public class BuiltinPlaceholders {

    public static BuiltinPlaceholders.Builder builder() {
        return new Builder();
    }

    private final Map<String, Object> replacementTable;

    private BuiltinPlaceholders(Map<String, Object> replacementTable) {
        this.replacementTable = replacementTable;
    }

    public PlaceholderFormat asPlaceholderFormat() {
        return PlaceholderFormat.builder().placeholderSection("%%{@a}%%")
                .append(replacementTable).build();
    }

    public static class Builder {

        private final Map<String, Object> replacementTable = new HashMap<>();

        private Builder() {
        }

        public Builder append(String placeholder, Object replacement) {
            replacementTable.put(placeholder, replacement);
            return this;
        }

        public Builder player(OfflinePlayer player) {
            return append("player", player.getName());
        }

        public BuiltinPlaceholders build() {
            return new BuiltinPlaceholders(replacementTable);
        }
    }
}
