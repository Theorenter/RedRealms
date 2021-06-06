package org.concordiacraft.redrealms.data;

import org.bukkit.Chunk;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.concordiacraft.redrealms.main.RedRealms;
import org.concordiacraft.redutils.utils.RedDataConverter;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public abstract class RedData {
    abstract File getFile();


    private static Map<Chunk, RedChunk> allChunks = new HashMap<>();
    private static Map<String, RedPlayer> allPlayers = new HashMap<>();
    private static Map<String, RedTown> allTowns = new HashMap<>();

    protected boolean setCustomFile(){
        return false;
    };

    public void updateFile() {
        Field[] fields = getClass().getDeclaredFields();
        YamlConfiguration yamlFile = YamlConfiguration.loadConfiguration(getFile());

        try {
            for (Field field : fields) {
                // checks all fields of class which implemented RedData, allowing to automatically read/update all fields
                // lets you get value of field
                field.setAccessible(true);
                if (field.get(this) != null) {
                    yamlFile.set(field.getName(), field.get(this));
                } else {
                    yamlFile.set(field.getName(), null);
                }
            }
        }
        catch(IllegalAccessException e) {
            e.printStackTrace();
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    if (!getFile().exists()) {
                        getFile().createNewFile();
                    }
                    yamlFile.save(getFile());
                } catch (IOException e) {
                    e.getStackTrace();
                }
            }
        }.runTaskAsynchronously(RedRealms.getPlugin());
    }

    public boolean readFile() {
        RedRealms.getPlugin().getRedLogger().debug("Чтение...");
        Field[] fields = getClass().getDeclaredFields();

        if (!getFile().exists()) {
            return false;
        }
        YamlConfiguration yamlConfig = YamlConfiguration.loadConfiguration(getFile());

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (field.getType().isAssignableFrom(HashMap.class)) {
                    Map<Object, Object> h = (Map<Object, Object>) RedDataConverter.getMapFromSection((ConfigurationSection) yamlConfig.get(field.getName()));
                    field.set(this, h);
                } else {
                    field.set(this, yamlConfig.get(field.getName()));
                }
            }
            catch (IllegalAccessException | IllegalArgumentException e1){
                RedRealms.getPlugin().getRedLogger().error(field.getType().toString(), e1);
            }
        }
        return true;
    }

    protected void deleteFile(){
        if (getFile().exists())
            getFile().delete();
    }

    // chunks
    public static RedChunk loadChunk(Chunk chunk) {

        if (allChunks.containsKey(chunk)){
            return allChunks.get(chunk);
        } else {
            RedChunk newChunk = new RedChunk(chunk);
            allChunks.put(chunk,newChunk);
            return newChunk;
        }
    }

    // towns
    public static RedTown loadTown(String name) {
        if (allTowns.containsKey(name)) {
            return allTowns.get(name);
        } else {
            RedTown newTown = new RedTown(name);
            allTowns.put(name,newTown);
            return newTown;
        }
    }

    // players
    public static RedPlayer loadPlayer(Player player) {
        String playerID = player.getUniqueId().toString();
        if (allPlayers.containsKey(playerID)){

            return allPlayers.get(playerID);

        } else {

            RedPlayer newPlayer = new RedPlayer(player);
            allPlayers.put(playerID, newPlayer);
            return newPlayer;
        }
    }

    public static Map<Chunk, RedChunk> getAllChunks() {
        return allChunks;
    }

    public static Map<String, RedPlayer> getAllPlayers() {
        return allPlayers;
    }

    public static Map<String, RedTown> getAllTowns() {
        return allTowns;
    }

}
