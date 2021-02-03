package org.concordiacraft.redrealms.data;

import org.concordiacraft.redrealms.main.RedRealms;
import org.bukkit.entity.Player;

import java.io.File;

public class DataPlayer implements IPluginFile {

    // Fields
    private final String id;
    private String name;
    private String title;
    private String realm;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public String getId() {
        return id;
    }

    public static String getDataPath() {
        return (RedRealms.getPlugin().getDataFolder() + File.separator + "data" + File.separator + "players");
    }

    public File getFile() {
        return new File(RedRealms.getPlugin().getDataFolder() + File.separator + "data" + File.separator +
                "players" + File.separator + id.toString() + ".yml");
    }

    // Constructor
    public DataPlayer(Player p) {

        this.id = p.getUniqueId().toString();
        this.name = p.getName();
        readFile();
    }

}
