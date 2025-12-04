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
                return "";
            }

            Manifest manifest = new Manifest(input);
            Attributes attr = manifest.getMainAttributes();
            return attr.getValue(name);

        } catch (Exception e) {
            return "";
        }
    }

    public static String getMainVersion() {
        return getStringAttribute("Main-Version");
    }

    public static String getBuildNumber() {
        return getStringAttribute("Build-Number");
    }

    public static String getFullVersion() {
        return getMainVersion() + "-" + getBuildNumber();
    }

}
