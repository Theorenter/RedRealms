package com.eosproject.redrealms.main;

import com.eosproject.redrealms.config.RedRealmsSettings;
import com.eosproject.redrealms.listener.ListenerTest;
import org.bukkit.plugin.java.JavaPlugin;

public class RedRealms extends JavaPlugin {

    @Override
    public void onEnable() {
        RedLog.heading(getName());
        // Settings init
        RedRealmsSettings.initialization();

        //test
        getServer().getPluginManager().registerEvents(new ListenerTest(), this);
    }

    @Override
    public void onDisable() {

    }

}
