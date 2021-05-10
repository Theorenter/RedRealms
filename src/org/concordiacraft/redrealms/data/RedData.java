package org.concordiacraft.redrealms.data;

import org.bukkit.block.banner.Pattern;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;
import org.concordiacraft.redrealms.main.RedRealms;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class RedData {
    abstract File getFile();

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
        Field[] fields = getClass().getDeclaredFields();

        if (!getFile().exists()) {
            return false;
        }
        YamlConfiguration yamlFile = YamlConfiguration.loadConfiguration(getFile());
        for (Field field : fields){
            field.setAccessible(true);
            try {
                if (field.get(this) instanceof Map) {
                    HashMap<Object, Object> h = (HashMap<Object, Object>) field.get(this);
                    field.set(this, h);
                } else {
                    field.set(this, yamlFile.get(field.getName()));
                }
            }
            catch (IllegalAccessException e1){
                e1.printStackTrace();
            }
        }
        return true;
    }
    protected void deleteFile(){
        if (!getFile().exists())
        getFile().delete();
    }

}
