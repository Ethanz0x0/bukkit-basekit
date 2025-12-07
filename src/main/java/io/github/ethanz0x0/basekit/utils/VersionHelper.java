package io.github.ethanz0x0.basekit.utils;

import io.github.ethanz0x0.basekit.BaseKit;

import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

public class VersionHelper {

    public static String getStringAttribute(String name) {
        try (InputStream input = BaseKit.class.getClassLoader()
                .getResourceAsStream("META-INF/MANIFEST.MF")) {

            if (input == null) {
                return null;
            }

            Manifest manifest = new Manifest(input);
            Attributes attr = manifest.getMainAttributes();
            return attr.getValue(name);

        } catch (Exception e) {
            return null;
        }
    }

    public static String getMainVersion() {
        String mainVersion = getStringAttribute("Main-Version");
        if (mainVersion == null || mainVersion.equalsIgnoreCase("null")) {
            return "unknown";
        }
        return mainVersion;
    }

    public static String getBuildNumber() {
        String buildNumber = getStringAttribute("Build-Number");
        if (buildNumber == null || buildNumber.equalsIgnoreCase("null")) {
            return "unknown";
        }
        return buildNumber;
    }

    public static String getFullVersion() {
        return getMainVersion() + "-" + getBuildNumber();
    }

}
