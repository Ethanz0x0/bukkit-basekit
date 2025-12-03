package io.github.ethanz0x0.basekit.config;

import com.cryptomorin.xseries.XSound;
import io.github.ethanz0x0.basekit.utils.PlaceholderAPIHook;
import io.github.ethanz0x0.basekit.utils.sound.SoundData;
import io.github.ethanz0x0.nucleus.object.format.Formatter;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;

public enum Module {

    PLAYER_JOIN("player-join"),
    PLAYER_QUIT("player-quit"),
    DISABLED_COMMANDS("disabled-commands");

    private final String module;

    Module(String module) {
        this.module = module;
    }

    public boolean isEnabled() {
        return Config.getMainConfig().getBoolean(module + ".enabled");
    }

    public void ifEnabled(Runnable run) {
        if (isEnabled()) {
            run.run();
        }
    }

    public <T> T getOption(String option, T def) {
        return Config.getMainConfig().get(module + "." + option, def);
    }

    public String getText(OfflinePlayer player, String option, BuiltinPlaceholders builtinPlaceholders) {
        return Formatter.format(getText(player, option), builtinPlaceholders.asPlaceholderFormat());
    }

    public String getText(String option, BuiltinPlaceholders builtinPlaceholders) {
        return Formatter.format(getText(option), builtinPlaceholders.asPlaceholderFormat());
    }

    public String getText(OfflinePlayer player, String option) {
        String result = getText(option);
        if (PlaceholderAPIHook.isHooked()) {
            return PlaceholderAPI.setPlaceholders(player, result);
        } else {
            return result;
        }
    }

    public String getText(String option) {
        return ChatColor.translateAlternateColorCodes('&', getOption(option, ""));
    }

    public SoundData getSound(String option) {
        String[] soundData = getOption(option, "").trim().split(",");
        return new SoundData(XSound.of(soundData[0]).orElse(XSound.ENTITY_VILLAGER_NO),
                Float.parseFloat(soundData[1]), Float.parseFloat(soundData[2]));
    }
}