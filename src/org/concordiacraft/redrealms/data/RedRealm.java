package org.concordiacraft.redrealms.data;

import org.concordiacraft.redrealms.main.RedRealms;

import java.io.File;

public class RedRealm extends RedData {
    private String name;
    public File getFile() {
        return new File(RedRealms.getPlugin().getDataFolder() + File.separator + "data" + File.separator +
                "realms" + File.separator + name + ".yml");
    }
}
