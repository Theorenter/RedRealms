package org.concordiacraft.redrealms.data;

import org.concordiacraft.redrealms.main.RedRealms;

import java.io.File;

public class RedRealmsData {

    public static void initialization(RedRealms plugin)
    {
        String dataPath = plugin.getDataFolder() + File.separator + "data";

        //region Creation - folder of the data
        File data = new File(dataPath);
        if (!data.isDirectory()) {
            data.mkdir();
        }
        //endregion
        //region Creation - folders of the players, regions, towns and states
        File playersData = new File(dataPath + File.separator + "players");
        File regionsData = new File(dataPath + File.separator + "regions");
        File campsData = new File(dataPath + File.separator + "camps");
        File townsData = new File(dataPath + File.separator + "towns");
        File statesData = new File(dataPath + File.separator + "states");


        if (!playersData.isDirectory()) {
            playersData.mkdir();
        }
        if (!regionsData.isDirectory()) {
            regionsData.mkdir();
        }
        if (!campsData.isDirectory()) {
            campsData.mkdir();
        }
        if (!townsData.isDirectory()) {
            townsData.mkdir();
        }
        if (!statesData.isDirectory()) {
            statesData.mkdir();
        }
        //endregion
    }
    //endregion
}
