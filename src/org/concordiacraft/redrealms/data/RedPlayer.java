package org.concordiacraft.redrealms.data;

import org.concordiacraft.redrealms.main.RedRealms;
import org.bukkit.entity.Player;

import java.io.File;

public class RedPlayer extends RedData {

    // Fields
    private final String id;
    private String name;
    private String title;
    private String playerRealmName;
    private String playerTownName;

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

    public void setPlayerRealmName(String playerRealmName) {
        this.playerRealmName = playerRealmName;
    }

    public String getId() {
        return id;
    }

    public String getPlayerTownName() { return playerTownName; }

    public String getPlayerRealmName() { return playerRealmName; }

    public boolean hasTown() {
        if (playerTownName == null)
            return false;
        else return true;
    }


    public void setPlayerTownName(String playerTownName) { this.playerTownName = playerTownName; }

    public static String getDataPath() {
        return (RedRealms.getPlugin().getDataFolder() + File.separator + "data" + File.separator + "players");
    }

    public File getFile() {
        return new File(RedRealms.getPlugin().getDataFolder() + File.separator + "data" + File.separator +
                "players" + File.separator + id + ".yml");
    }

    // Constructor
    protected RedPlayer(Player p) {
        this.id = p.getUniqueId().toString();

        if (!readFile()) {
            this.name = p.getName();
            updateFile();
        }
    }
}
