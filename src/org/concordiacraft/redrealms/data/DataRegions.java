package org.concordiacraft.redrealms.data;

import org.bukkit.World;
import org.concordiacraft.redrealms.main.RedRealms;
import org.bukkit.Chunk;

import java.io.File;

public class DataRegions implements IPluginFile{
    private String World;
    private Integer X;

    public String getWorld() {
        return World;
    }

    public void setWorld(String world) {
        World = world;
    }

    public Integer getX() {
        return X;
    }

    public void setX(Integer x) {
        X = x;
    }

    public Integer getZ() {
        return Z;
    }

    public void setZ(Integer z) {
        Z = z;
    }

    public String getOwner() {
        return Owner;
    }

    public void setOwner(String owner) {
        Owner = owner;
    }

    private Integer Z;
    private String Owner;
    public File getFile() {
        String fileName = World +"_"+ X.toString()+"_"+Z.toString();
        return new File(RedRealms.getPlugin().getDataFolder() + File.separator + "data" + File.separator +
                "regions" + File.separator + fileName +  ".yml");
    }
    public void setWorld(World world){
        World = world.getName();
    }
    public void setChunk(Chunk chunk){
        X= chunk.getX();
        Z=chunk.getZ();
    }
}
