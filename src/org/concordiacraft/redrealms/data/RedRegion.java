package org.concordiacraft.redrealms.data;

import org.bukkit.Chunk;
import org.concordiacraft.redrealms.main.RedRealms;
import org.concordiacraft.redrealms.utilits.ChunkWork;

import java.io.File;
import java.util.ArrayList;

public class RedRegion extends RedData {
    private String name;
    private String townName;
    private ArrayList<ArrayList<Integer>> chunks = new ArrayList<>();
    public File getFile() {

        String fileName = getTownName() + "_" + getName();
        return new File(RedRealms.getPlugin().getDataFolder() + File.separator + "data" + File.separator +
                "region" + File.separator + fileName +  ".yml");
    }
    public RedRegion(String name, String townName){
        this.name = name;
        this.townName = townName;
        readFile();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }
    public void addChunk(Chunk chunk){
        this.chunks.add(ChunkWork.chunkCreate(chunk));
    }
    public void removeChunk(Chunk chunk) {this.chunks.remove(ChunkWork.chunkCreate(chunk));}
    public ArrayList<ArrayList<Integer>> getChunks() {
        return chunks;
    }

    public void setChunks(ArrayList<ArrayList<Integer>> chunks) {
        this.chunks = chunks;
    }
}