package org.concordiacraft.redrealms.data;

import org.concordiacraft.redrealms.main.RedRealms;

import java.io.File;
import java.util.List;

public class RedRealm extends RedData {
    private String name;
    private String type;
    private List<String> towns;
    private String capitalName;
    private String leaderID;

    public File getFile() {
        return new File(RedRealms.getPlugin().getDataFolder() + File.separator + "data" + File.separator +
                "realms" + File.separator + name + ".yml");
    }
}
