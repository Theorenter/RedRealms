package org.concordacraft.redrealms.data;

import org.concordacraft.redrealms.main.RedLog;
import org.concordacraft.redrealms.main.RedRealms;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class DataRegions {


    public static void createRegionCamp(Player player, Chunk capitalChunk) {
        setCustomFile();


    }

    private static void setCustomFile() {
        File fileDirectory = new File(RedRealms.getPlugin().getDataFolder() + File.separator + "data" + File.separator + "regions");
        if (!fileDirectory.isDirectory()) {
            fileDirectory.getParentFile().mkdirs();
            fileDirectory.mkdir();
        }
    }

    private static void regionRecord(Chunk ch) {
        File chunkFile = new File(RedRealms.getPlugin().getDataFolder() + File.separator + "data" + File.separator + "regions" + File.separator + String.valueOf(ch.getX()) + "_" + String.valueOf(ch.getZ()) + ".dat");
        try {
            chunkFile.createNewFile();
        } catch (IOException e) {
            RedLog.error("Cannot create " + String.valueOf(ch.getX()) + "_" + String.valueOf(ch.getZ()) + ".dat region file!", e);
        }
    }
}
