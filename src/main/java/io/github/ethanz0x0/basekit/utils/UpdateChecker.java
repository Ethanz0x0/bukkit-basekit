package io.github.ethanz0x0.basekit.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.github.ethanz0x0.basekit.BaseKit;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

public class UpdateChecker {

    private static final BaseKit plugin = BaseKit.getInstance();
    private static final String GITHUB_RELEASES_API = "https://api.github.com/repos/Ethanz0x0/bukkit-basekit/releases";

    public static CompletableFuture<Boolean> check() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(GITHUB_RELEASES_API).openConnection();
                connection.setRequestProperty("Accept", "application/vnd.github.v3+json");
                connection.setRequestProperty("User-Agent", "BaseKit-Plugin");

                if (connection.getResponseCode() != 200) {
                    plugin.getLogger().warning("Update checker failed to fetch releases, response code: "
                            + connection.getResponseCode());
                    return true;
                }

                JsonArray releases = JsonParser.parseReader(new InputStreamReader(connection.getInputStream())).getAsJsonArray();
                if (releases.isEmpty()) {
                    return true;
                }

                JsonElement latestRelease = releases.get(0);
                String latestTag = latestRelease.getAsJsonObject().get("tag_name").getAsString();
                String currentVersion = VersionHelper.getMainVersion();

                return latestTag.equalsIgnoreCase(currentVersion);

            } catch (Exception e) {
                plugin.getLogger().warning("Update checker failed to fetch release with an error.");
                e.printStackTrace();
                return true;
            }
        });
    }
}