package org.concordacraft.redrealms.data;

import org.concordacraft.redrealms.main.RedLog;
import org.concordacraft.redrealms.main.RedRealms;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class DataPlayer {

    // Fields
    private final UUID ID;
    private String Name;
    private String Title;
    private String Realm;

    // Getters and Setters
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getRealm() {
        return Realm;
    }

    public void setRealm(String realm) {
        this.Realm = realm;
    }

    public UUID getID() {
        return ID;
    }

    public static String getDataPath() {
        return (RedRealms.getPlugin().getDataFolder() + File.separator + "data" + File.separator + "players");
    }

    public File getDataFile() {
        return new File(RedRealms.getPlugin().getDataFolder() + File.separator + "data" + File.separator +
                "players" + File.separator + ID.toString() + ".yml");
    }

    // Constructor
    public DataPlayer(Player p, File dataPlayerFile) {

        this.ID = p.getUniqueId();
        this.Name = p.getName();

        FileConfiguration DataPlayerConfig = new YamlConfiguration();

        DataPlayerConfig.set("Name", this.Name);
        try {
            RedLog.info("Data file for player " + this.Name + " [" + this.ID.toString() + "] was successful created!");
            DataPlayerConfig.save(dataPlayerFile);
        } catch (IOException e) {
            RedLog.error("Cannot create data for " + this.Name + " [" + this.ID.toString() + "]!", e);
        }
    }

}
