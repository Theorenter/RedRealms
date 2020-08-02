package com.eosproject.redrealms;

import com.eosproject.redrealms.file.LoaderFileManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class RedRealms extends JavaPlugin {

    static public Logger log = Logger.getLogger("Minecraft");

    @Override
    public void onEnable() {
        LoaderFileManager loaderManager = new LoaderFileManager(this);
    }

    @Override
    public void onDisable() {

    }

}
