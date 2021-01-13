package org.concordiacraft.redrealms.data;

import org.bukkit.Chunk;
import org.concordiacraft.redrealms.main.RedRealms;

import java.io.File;
import java.util.ArrayList;

public class DataTowns implements IPluginFile {
    private String townName;
    private String playerName;
    ArrayList<Integer> ChunkCoords = new ArrayList<>();
    ArrayList<ArrayList<Integer>> Chunks = new ArrayList<>();
    public DataTowns(String campName) {
        this.townName = campName;


    }

    //region Getters,setters,implemented functions
    public File getFile() {
        return new File(RedRealms.getPlugin().getDataFolder() + File.separator + "data" + File.separator +
                "towns" + File.separator + townName + ".yml");
    }

    public ArrayList<Integer> getChunkCoords() {
        return ChunkCoords;
    }

    public void setChunkCoords(ArrayList<Integer> chunkCoords) {
        ChunkCoords = chunkCoords;
    }
    public void setChunkCoords(int ChunkX,int ChunkZ) {
        ChunkCoords.add(ChunkX);
        ChunkCoords.add(ChunkZ);
    }
    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public void addChunk(int ChunkX,int ChunkZ){
        ArrayList<Integer> ChunkCoords = new ArrayList<Integer>() {
        };
        ChunkCoords.add(ChunkX);
        ChunkCoords.add(ChunkZ);
        Chunks.add(ChunkCoords);
    }
    public void addChunk(Chunk chunk){
        ArrayList<Integer> ChunkCoords = new ArrayList<Integer>() {
        };
        ChunkCoords.add(chunk.getX());
        ChunkCoords.add(chunk.getZ());
        Chunks.add(ChunkCoords);
    }
    public ArrayList<ArrayList<Integer>> getChunks (){
        return Chunks;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    //endregion
}

