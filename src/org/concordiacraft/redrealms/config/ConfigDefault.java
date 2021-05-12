package org.concordiacraft.redrealms.config;

import org.concordiacraft.redrealms.main.RedRealms;
import org.concordiacraft.redutils.main.config.ConfigAbstractSetup;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Theorenter
 * Default RedRealms configuration
 */
public final class ConfigDefault extends ConfigAbstractSetup {

    private static boolean debugMode;
    private static String localization;

    private static String nameRegex;
    private static int nameMaxLength;
    private static int nameMinLength;

    private static String errorSoundName;
    private static int errorSoundVolume;
    private static int errorSoundPitch;

    private static HashMap<String, ArrayList<String>> biomeMap = new HashMap<>();

    ConfigDefault(RedRealms plugin, String YMLFileName) {
        super(plugin, YMLFileName);
        debugMode = customConfig.getBoolean("main.debug");
        localization = customConfig.getString("main.localization");

        nameRegex = customConfig.getString("string-format.name-regex");
        nameMaxLength = customConfig.getInt("string-format.name-max-length");
        nameMinLength = customConfig.getInt("string-format.name-min-length");

        for (String key : customConfig.getConfigurationSection("biomes.biomes-list").getKeys(false)) {
            biomeMap.put(key, (ArrayList<String>) customConfig.get("biomes.biomes-list." + key));
        }

        errorSoundName = customConfig.getString("effects-sounds.error-sound.name");
        errorSoundPitch = customConfig.getInt("effects-sounds.error-sound.pitch");
        errorSoundVolume = customConfig.getInt("effects-sounds.error-sound.volume");

    }
    public static boolean isDebugMode() { return debugMode; }
    public static String getGlobalLocalization() { return localization; }

    public static String getNameRegex() { return nameRegex; }
    public static long getNameMaxLength() { return nameMaxLength; }
    public static long getNameMinLength() { return nameMinLength; }
    public static HashMap<String, ArrayList<String>> getBiomeMap() { return biomeMap; }

    public static String getErrorSoundName() { return errorSoundName; }
    public static int getErrorSoundPitch() { return errorSoundPitch; }
    public static int getErrorSoundVolume() { return errorSoundVolume; }
}
