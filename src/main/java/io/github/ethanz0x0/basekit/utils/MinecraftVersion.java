package io.github.ethanz0x0.basekit.utils;

import org.bukkit.Bukkit;

public enum MinecraftVersion {

    MINECRAFT_1_18(757, "1.18"),
    MINECRAFT_1_18_2(758, "1.18.2"),
    MINECRAFT_1_19(759, "1.19"),
    MINECRAFT_1_19_1(760, "1.19.1"),
    MINECRAFT_1_19_3(761, "1.19.3"),
    MINECRAFT_1_19_4(762, "1.19.4"),
    MINECRAFT_1_20(763, "1.20"),
    MINECRAFT_1_20_2(764, "1.20.2"),
    MINECRAFT_1_20_3(765, "1.20.3"),
    MINECRAFT_1_20_5(766, "1.20.5"),
    MINECRAFT_1_21(767, "1.21"),
    MINECRAFT_1_21_2(768, "1.21.2"),
    MINECRAFT_1_21_4(769, "1.21.4"),
    MINECRAFT_1_21_5(770, "1.21.5"),
    MINECRAFT_1_21_6(771, "1.21.6"),
    MINECRAFT_1_21_7(772, "1.21.7"),
    MINECRAFT_1_21_9(773, "1.21.9");

    public static MinecraftVersion getCurrentVersion() {
        return getByVersionString(Bukkit.getBukkitVersion().split("-")[0]);
    }

    public static MinecraftVersion getByProtocol(int protocol) {
        for (MinecraftVersion version : values()) {
            if (version.getProtocolVersion() == protocol) {
                return version;
            }
        }
        return null;
    }

    public static MinecraftVersion getByVersionString(String versionName) {
        for (MinecraftVersion version : values()) {
            if (version.getMinecraftVersion().equalsIgnoreCase(versionName)) {
                return version;
            }
        }
        return null;
    }

    private final int protocolVersion;
    private final String minecraftVersion;

    MinecraftVersion(int protocolVersion, String minecraftVersion) {
        this.protocolVersion = protocolVersion;
        this.minecraftVersion = minecraftVersion;
    }

    public int getProtocolVersion() {
        return protocolVersion;
    }

    public String getMinecraftVersion() {
        return minecraftVersion;
    }

    public boolean isAfterOrCurrent(MinecraftVersion version) {
        return this.protocolVersion > version.protocolVersion;
    }

    public boolean isBeforeOrCurrent(MinecraftVersion version) {
        return this.protocolVersion < version.protocolVersion;
    }
}