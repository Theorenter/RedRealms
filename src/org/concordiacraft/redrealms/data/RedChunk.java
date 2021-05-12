package org.concordiacraft.redrealms.data;

import org.bukkit.World;
import org.concordiacraft.redrealms.main.RedRealms;
import org.bukkit.Chunk;
import org.concordiacraft.redrealms.utilits.ChunkWork;

import java.io.File;
import java.util.ArrayList;

/**
 * @author Theorneter, Nicekita
 * Class for working with data about chunks
 */

public class RedChunk extends RedData {
    private String worldName;
    private Integer X;
    private Integer Z;
    private String ownerTown;
    private Boolean municipality;
    private String townRegion;
    private String chunkProf;
    private String biomeKey;

    @Deprecated
    public RedChunk(Chunk chunk) {
        this.setChunk(chunk);
        if (!readFile()){
            this.setWorld(chunk.getWorld());
            this.biomeKey = ChunkWork.getBiome(chunk).name();
            updateFile();
        }
    }

    public RedChunk(ArrayList<Integer> chunk){
        RedRealms.getPlugin().getRedLogger().debug("Создание нового чанка...");
        this.X = chunk.get(0);
        this.Z = chunk.get(1);
        if (!readFile()){
            updateFile();
        }
    }

    public String getWorldName() {
        return worldName;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
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
        String fileName = worldName +"_"+ X.toString()+"_"+Z.toString();
        return new File(RedRealms.getPlugin().getDataFolder() + File.separator + "data" + File.separator +
                "chunks" + File.separator + fileName +  ".yml");
    }
    public void setWorld(World world){
        this.worldName = world.getName();
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
