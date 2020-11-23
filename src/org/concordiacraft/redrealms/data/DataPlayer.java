package org.concordiacraft.redrealms.data;

import org.concordiacraft.redrealms.main.RedRealms;
import org.bukkit.entity.Player;

import java.io.File;

public class DataPlayer implements IPluginFile {

    // Fields
    private final String ID;
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

    public String getID() {
        return ID;
    }

    public static String getDataPath() {
        return (RedRealms.getPlugin().getDataFolder() + File.separator + "data" + File.separator + "players");
    }

    public File getFile() {
        return new File(RedRealms.getPlugin().getDataFolder() + File.separator + "data" + File.separator +
                "players" + File.separator + ID.toString() + ".yml");
    }

    // Constructor
    public DataPlayer(Player p) {

        this.ID = p.getUniqueId().toString();
        this.Name = p.getName();

    }

}
