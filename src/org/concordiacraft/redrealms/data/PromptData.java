package org.concordiacraft.redrealms.data;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.concordiacraft.redrealms.main.RedRealms;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class PromptData {

    private static HashMap<UUID, Object> promptMap = new HashMap<>();

    private final static String fileName = "prompt-data.yml";
    private final static File promptDataFile = new File(RedRealms.getPlugin().getDataFolder() + File.separator + "data" + File.separator + fileName);
    private final static YamlConfiguration yamlConf = new YamlConfiguration();;

    public static void loadPromptData(RedRealms plugin) {
        if (promptDataFile.exists()) {
            try {
                yamlConf.load(promptDataFile);
                if (yamlConf.get("prompt-data") != null) {
                    ConfigurationSection promptData = yamlConf.getConfigurationSection("prompt-data");
                    Set<String> pIDs = promptData.getKeys(false);
                    for (String pID : pIDs) {
                        ItemStack i = (ItemStack) yamlConf.get("prompt-data." + pID);
                        UUID uuid = UUID.fromString(pID);
                        promptMap.put(uuid, i);
                    }
                }
            }
            catch (IOException | InvalidConfigurationException e) {
                plugin.getRedLogger().error("Cannot load configuration from " + fileName + "xxxxxx file."); }
        } else {
            try { promptDataFile.createNewFile(); }
            catch (IOException e) { plugin.getRedLogger().error("Cannot create " + fileName + " file."); }
        }
    }

    private static void savePromptData() {
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    HashMap<String, Object> saveMap = new HashMap<>();
                    promptMap.forEach((id, o) -> saveMap.put(id.toString(), o));
                    yamlConf.set("prompt-data", saveMap);
                    yamlConf.save(promptDataFile);
                } catch (IOException e) {
                    RedRealms.getPlugin().getRedLogger().error("Cannot save " + fileName + " file.");
                }
            }
        }.runTaskAsynchronously(RedRealms.getPlugin());
    }

    public static HashMap getPromptMap() {
        return promptMap;
    }

    public static void addToPromptMap(UUID pID, Object o) {
        promptMap.put(pID, o);
        savePromptData();
    }
    public static void removeFromPromptMap(UUID pID) {
        promptMap.remove(pID);
        savePromptData();
    }
}
