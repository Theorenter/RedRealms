package org.concordacraft.redrealms.data;

import org.bukkit.Chunk;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

public interface IPluginFile {
    File getFile();
    default boolean setCustomFile(){
        return false;
    };

    default boolean updateFile()  {
        Field[] fields = getClass().getDeclaredFields();
        try {
            if (!getFile().exists()) getFile().createNewFile();
            YamlConfiguration yamlFile = YamlConfiguration.loadConfiguration(getFile());
            for (Field field : fields) { // checks all fields of class which implemented IPluginFile, allowing to automatically read/update all fields;
                field.setAccessible(true); // lets you get value of field
                if (field.get(this) != null) {
                            yamlFile.set(field.getName(), field.get(this));
                } else
                {
                    yamlFile.set(field.getName(), null);
                }
            }
            yamlFile.save(getFile());
        }
            catch(IllegalAccessException | IOException e1 ){
                    e1.printStackTrace();
                }


        return true;
    };
    default boolean readFile()  {
        Field[] fields = getClass().getDeclaredFields();
        if(!getFile().exists()) return false;
        YamlConfiguration yamlFile = YamlConfiguration.loadConfiguration(getFile());
        for (Field field : fields){
            field.setAccessible(true);
            try {

                            field.set(this,yamlFile.get(field.getName()));

            }
            catch (IllegalAccessException e1){
                e1.printStackTrace();
            }
        }

        return true;

    }
    default boolean deleteFile(){
        if (!getFile().exists()) return false;
        getFile().delete();
        return true;
    }

}
