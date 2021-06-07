package org.concordiacraft.redrealms.data;

import org.bukkit.World;
import org.concordiacraft.redrealms.main.RedRealms;
import org.bukkit.Chunk;
import org.concordiacraft.redrealms.utilits.BiomeManager;
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
    private String biomeType;

    /**
     * RedChunk constructor.
     * WARNING: All valid RedChunks must be created via RedData.loadChunk()!
     * @param chunk default minecraft chunk.
     */
    public RedChunk(Chunk chunk) {
        this.setChunk(chunk);
        if (!readFile()){
            this.setWorld(chunk.getWorld());
            this.biomeType = BiomeManager.getBiomeType(ChunkWork.getBiome(chunk).name());
            updateFile();
        }
    }

    /**
     * RedChunk constructor.
     * WARNING: All valid RedChunks must be created via RedData.loadChunk()!
     * @param chunkLocation X (first value) and Z (second value) location values of the chunk.
     */
    public RedChunk(ArrayList<Integer> chunkLocation){
        RedRealms.getPlugin().getRedLogger().debug("Создание нового чанка...");
        this.X = chunkLocation.get(0);
        this.Z = chunkLocation.get(1);
        if (!readFile()){
            updateFile();
        }
    }

    /**
     * @return world name of the chunk.
     */
    public String getWorldName() {
        return worldName;
    }

    /**
     * @param worldName name of the chunk's world.
     */
    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    /**
     * @return the X coordinate of the RedChunk.
     */
    public Integer getX() { return X; }

    /**
     * @param x the X coordinate of the RedChunk.
     */
    public void setX(Integer x) { X = x; }

    /**
     * @return the Z coordinate of the RedChunk.
     */
    public Integer getZ() { return Z; }

    /**
     * @param z the Z coordinate of the RedChunk.
     */
    public void setZ(Integer z) { Z = z; }

    /**
     * @return which town is the owner of the RedChunk.
     */
    public String getTownOwner() {
        return this.ownerTown;
    }

    /**
     * @param townOwner the town that will own the chunk.
     */
    public void setTownOwner(String townOwner) {
        this.ownerTown = townOwner;
    }

    /**
     * @return RedChunk's file.
     */
    public File getFile() {
        String fileName = worldName + "_" + X.toString() + "_" + Z.toString();
        return new File(RedRealms.getPlugin().getDataFolder() + File.separator + "data" + File.separator +
                "chunks" + File.separator + fileName +  ".yml");
    }

    /**
     * @param world default minecraft object of the world.
     */
    public void setWorld(World world) {
        this.worldName = world.getName();
    }

    /**
     * @param chunk default minecraft chunk.
     */
    public void setChunk(Chunk chunk) {
        X = chunk.getX();
        Z = chunk.getZ();
    }

    /**
     * @return true if the RedChunk is directly owned by the town and false if the RedChunk belongs to a citizen of the town.
     */
    public Boolean isMunicipality() {
        return municipality;
    }

    /**
     * @param municipality true if the RedChunk is directly owned by the town and false if the RedChunk belongs to a citizen of the town.
     */
    public void setMunicipality(Boolean municipality) {
        this.municipality = municipality;
    }

    public String getTownRegion() {
        return townRegion;
    }

    public void setTownRegion(String townRegion) {
        this.townRegion = townRegion;
    }

    /**
     * @return the name of the professional purpose of the chunk.
     */
    public String getChunkProf() {
        return chunkProf;
    }

    /**
     * @param chunkProf the name of the professional purpose of the chunk.
     */
    public void setChunkProf(String chunkProf) {
        this.chunkProf = chunkProf;
    }

    public String getBiomeType() {
        return biomeType;
    }
}