package io.github.ethanz0x0.basekit.schedulers;

import io.github.ethanz0x0.basekit.BaseKit;
import io.github.ethanz0x0.basekit.config.Config;
import io.github.ethanz0x0.basekit.utils.UpdateChecker;
import io.github.ethanz0x0.basekit.utils.VersionHelper;

public class UpdateCheckerScheduler implements Runnable {

    public static boolean NEED_UPDATE = false;

    private static final BaseKit plugin = BaseKit.getInstance();

    @Override
    public void run() {
        if (!Config.getMainConfig().getBoolean("update-checker")) {
            return;
        }
        plugin.getLogger().warning("-------------------------------------------------------");
        plugin.getLogger().info("Checking for version updates...");
        UpdateChecker.check().thenAccept(latest -> {
           if (latest) {
               plugin.getLogger().info("You're running the latest version of BaseKit! ("
                       + VersionHelper.getFullVersion() + ")");
               NEED_UPDATE = false;
           } else {
               plugin.getLogger().warning("There is a newer version of BaseKit, you can download it at " +
                       "https://github.com/Ethanz0x0/bukkit-basekit/releases/");
               NEED_UPDATE = true;
           }
            plugin.getLogger().warning("-------------------------------------------------------");
        });
    }
}
