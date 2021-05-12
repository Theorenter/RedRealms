package org.concordiacraft.redrealms.data;

import org.concordiacraft.redrealms.main.RedRealms;

import java.io.File;

public class DataManager {

    public static void initialization(RedRealms plugin)
    {
        String dataPath = plugin.getDataFolder() + File.separator + "data";

        // Creation - folder of the data
        File data = new File(dataPath);
        if (!data.isDirectory()) {
            data.mkdir();
        }
        // Creation - folders of the players, regions, towns and states
        File playersData = new File(dataPath + File.separator + "players");
        File regionsData = new File(dataPath + File.separator + "chunks");
        File townsData = new File(dataPath + File.separator + "towns");
        File realmsData = new File(dataPath + File.separator + "realms");
        File regionData = new File(dataPath + File.separator + "region");


        if (!playersData.isDirectory()) {
            playersData.mkdir();
        }
        if (!regionData.isDirectory()){
            regionData.mkdir();
        }
        if (!regionsData.isDirectory()) {
            regionsData.mkdir();
        }
        if (!townsData.isDirectory()) {
            townsData.mkdir();
        }
        if (!realmsData.isDirectory()) {
            realmsData.mkdir();
        }
    }
}
