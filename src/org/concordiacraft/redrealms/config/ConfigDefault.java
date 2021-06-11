package org.concordiacraft.redrealms.config;

import org.concordiacraft.redrealms.main.RedRealms;
import org.concordiacraft.redutils.config.ExtendedRedConfig;
import org.concordiacraft.redutils.utils.RedDataConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Theorenter
 * Configuration of the RedRealms plugin
 */
public final class ConfigDefault extends ExtendedRedConfig {

    // fields main
    private final boolean debug;
    private final String localizationFileName;
    private final boolean hasCustomAdvancements;

    // network
    private final boolean isNetworkEnabled;
    private final String hostname;
    private final String apiKey;

    // fields world
    private final List<String> availableWorlds;

    // fields biomes
    private final Map<String, List<String>> biomeTypes;
    private final List<String> availableBiomeTypes;

    // fields string-format
    private final int nameMaxLength;
    private final int nameMinLength;
    private final String nameRegex;

    // fields effects-sounds
        // error-sound
    private final String errorSoundName;
    private final int errorSoundPitch;
    private final int errorSoundVolume;

    ConfigDefault(RedRealms plugin, String YMLFileName) {
        super(plugin, YMLFileName);

        // main
        this.debug = customConfig.getBoolean("main.debug");
        RedRealms.getPlugin().setDebug(debug);
        this.localizationFileName = customConfig.getString("main.localization");
        this.hasCustomAdvancements = customConfig.getBoolean("main.custom-advancements");

        // network
        this.isNetworkEnabled = customConfig.getBoolean("network.enable");
        this.hostname = customConfig.getString("network.host");
        this.apiKey = customConfig.getString("network.api-key");

        // world
        this.availableWorlds = new ArrayList<>(customConfig.getStringList("world.available-worlds"));

        // biomes
        this.biomeTypes = new HashMap(RedDataConverter.getMapFromSection(customConfig.getConfigurationSection("biomes.biomes-list")));
        this.availableBiomeTypes = new ArrayList<>(customConfig.getStringList("biomes.available-types"));

        // string-format
        this.nameMaxLength = customConfig.getInt("string-format.name-max-length");
        this.nameMinLength = customConfig.getInt("string-format.name-min-length");
        this.nameRegex = customConfig.getString("string-format.name-regex");

        // effects-sounds
        this.errorSoundName = customConfig.getString("effects-sounds.error-sound.name");
        this.errorSoundPitch = customConfig.getInt("effects-sounds.error-sound.pitch");
        this.errorSoundVolume = customConfig.getInt("effects-sounds.error-sound.volume");



        // goodbye
        customConfig = null;
    }

    // Getters

    public boolean isDebug() { return debug; }

    public String getLocalizationFileName() { return localizationFileName; }

    public boolean hasCustomAdvancements() { return hasCustomAdvancements; }

    public boolean isNetworkEnabled() { return isNetworkEnabled; }

    public String getHostname() { return hostname; }

    public String getApiKey() { return apiKey; }

    public List<String> getAvailableWorlds() { return availableWorlds; }

    public Map<String, List<String>> getBiomeTypes() { return biomeTypes; }

    public List<String> getAvailableBiomeTypes() { return availableBiomeTypes; }

    public int getNameMaxLength() { return nameMaxLength; }

    public int getNameMinLength() { return nameMinLength; }

    public String getNameRegex() { return nameRegex; }

    public String getErrorSoundName() { return errorSoundName; }

    public int getErrorSoundPitch() { return errorSoundPitch; }

    public int getErrorSoundVolume() { return errorSoundVolume; }

}
