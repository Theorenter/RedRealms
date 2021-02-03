package org.concordiacraft.redrealms.data;

import org.bukkit.World;
import org.concordiacraft.redrealms.main.RedRealms;
import org.bukkit.Chunk;
import org.concordiacraft.redrealms.utilits.ChunkWork;

import java.io.File;
import java.util.ArrayList;

public class DataChunk implements IPluginFile{
    private String world;
    private Integer X;
    private Integer Z;
    private String ownerTown;
    private Boolean municipality;
    private String townRegion;
    private String chunkProf;
    private String biomeKey;

    public DataChunk(Chunk chunk){
        this.setChunk(chunk);
        this.setWorld(chunk.getWorld());
        if (!readFile()) this.biomeKey = ChunkWork.getBiome(chunk);
    }
    //
    public DataChunk(ArrayList<Integer> chunk){
        this.X = chunk.get(0);
        this.Z = chunk.get(1);
        this.world = "world";
        readFile();
    }
    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
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
        return this.ownerTown;
    }

    public void setOwner(String owner) {
        this.ownerTown = owner;
    }

    public File getFile() {
        String fileName = world +"_"+ X.toString()+"_"+Z.toString();
        return new File(RedRealms.getPlugin().getDataFolder() + File.separator + "data" + File.separator +
                "chunks" + File.separator + fileName +  ".yml");
    }
    public void setWorld(World world){
        this.world = world.getName();
    }
    public void setChunk(Chunk chunk){
        X = chunk.getX();
        Z = chunk.getZ();

    }



    public Boolean getMunicipality() {
        return municipality;
    }

    public void setMunicipality(Boolean municipality) {
        this.municipality = municipality;
    }


    public String getTownRegion() {
        return townRegion;
    }

    public void setTownRegion(String townRegion) {
        this.townRegion = townRegion;
    }

    public String getChunkProf() {
        return chunkProf;
    }

    public void setChunkProf(String chunkProf) {
        this.chunkProf = chunkProf;
    }

    public String getBiomeKey() {
        return biomeKey;
    }
}
