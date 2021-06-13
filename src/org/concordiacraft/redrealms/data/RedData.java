package org.concordiacraft.redrealms.data;

import org.bukkit.Chunk;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.concordiacraft.redrealms.main.RedRealms;
import org.concordiacraft.redrealms.utilits.ChunkWork;
import org.concordiacraft.redutils.utils.RedDataConverter;

import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class RedData {
    abstract File getFile();


    private static Map<ArrayList<Integer>, RedChunk> allChunks = new HashMap<>();
    private static Map<String, RedPlayer> allPlayers = new HashMap<>();
    private static Map<String, RedTown> allTowns = new HashMap<>();

    protected boolean setCustomFile(){
        return false;
    };
    public void updateFile() {
        boolean isUpdate = false;
        if (getFile().exists()) isUpdate = true;

        Field[] fields = getClass().getDeclaredFields();
        YamlConfiguration yamlFile = YamlConfiguration.loadConfiguration(getFile());
        try {
            for (Field field : fields) {

                // fields starting with an underscore should not be written.
                if (field.getName().lastIndexOf('_') == 0)
                    continue;

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

        if (this instanceof RedTown){
            RedTown rt = (RedTown) this;
            RedData.allTowns.replace(rt.getName(),rt);
            rt.updateTown(isUpdate);
            RedRealms.getPlugin().getRedLogger().debug("Обновил "+ rt.getName());
        }
        if (this instanceof RedChunk){
            RedChunk rt = (RedChunk) this;
            ArrayList<Integer> chunkcoords=new ArrayList<>();
            chunkcoords.add(rt.getX());
            chunkcoords.add(rt.getZ());
            RedData.allChunks.replace(chunkcoords,rt);
            RedRealms.getPlugin().getRedLogger().debug("Обновил "+ rt.getTownOwner());
        }
        if (this instanceof RedPlayer){
            RedPlayer rp = (RedPlayer) this;
            if(RedData.allPlayers.replace(rp.getId(),rp)==null)
                RedData.allPlayers.put(rp.getId(),rp);
            rp.updatePlayer(isUpdate);
        }

    }

    public boolean readFile() {
        Field[] fields = getClass().getDeclaredFields();

        if (!getFile().exists()) {
            return false;
        }
        YamlConfiguration yamlConfig = YamlConfiguration.loadConfiguration(getFile());

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (field.getName().lastIndexOf('_') == 0) {
                    continue;
                }
                if (field.get(this) instanceof BigDecimal) {
                    field.set(this, new BigDecimal(yamlConfig.getDouble(field.getName())));
                    continue;
                }
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

        if (allChunks.containsKey(ChunkWork.chunkCreate(chunk))){
            return allChunks.get(ChunkWork.chunkCreate(chunk));
        } else {
            RedChunk newChunk = new RedChunk(chunk);
            allChunks.put(ChunkWork.chunkCreate(chunk),newChunk);
            return newChunk;
        }
    }
    public static RedChunk loadChunk(ArrayList<Integer> chunk) {

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
    public static RedPlayer loadPlayer(String playerID) {
        if (allPlayers.containsKey(playerID)){

            return allPlayers.get(playerID);

        } else {

            RedPlayer newPlayer = new RedPlayer(playerID);
            allPlayers.put(playerID, newPlayer);
            return newPlayer;
        }
    }
    public static Map<ArrayList<Integer>, RedChunk> getAllChunks() {
        return allChunks;
    }

    public static Map<String, RedPlayer> getAllPlayers() {
        return allPlayers;
    }

    public static Map<String, RedTown> getAllTowns() {
        return allTowns;
    }

}
