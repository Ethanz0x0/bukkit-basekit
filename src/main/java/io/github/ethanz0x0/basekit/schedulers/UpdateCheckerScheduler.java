package io.github.ethanz0x0.basekit.schedulers;

import io.github.ethanz0x0.basekit.config.Config;
import io.github.ethanz0x0.basekit.utils.UpdateChecker;
import io.github.ethanz0x0.basekit.utils.VersionHelper;
import org.bukkit.Bukkit;

public class UpdateCheckerScheduler implements Runnable {

    public static boolean NEED_UPDATE = false;

    @Override
    public void run() {
        if (!Config.getMainConfig().getBoolean("update-checker")) {
            return;
        }
        Bukkit.getLogger().info("Checking for version updates...");
        UpdateChecker.check().thenAccept(latest -> {
           if (latest) {
               Bukkit.getLogger().info("You're running the latest version of BaseKit! ("
                       + VersionHelper.getFullVersion() + ")");
               NEED_UPDATE = false;
           } else {
               Bukkit.getLogger().warning("There is a newer version of BaseKit, you can download it at " +
                       "https://github.com/Ethanz0x0/bukkit-basekit/releases/");
               NEED_UPDATE = true;
           }
        });
    }
}
