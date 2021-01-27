package org.concordiacraft.redrealms.data;

import org.bukkit.Chunk;
import org.concordiacraft.redrealms.main.RedRealms;
import org.concordiacraft.redrealms.utilits.ChunkWork;

import java.io.File;
import java.util.ArrayList;

public class DataTown implements IPluginFile {
    private String townName;
    private String playerID;
    private ArrayList<String> residentID = new ArrayList<>();
    ArrayList<Integer> chunkCoords = new ArrayList<>();
    ArrayList<ArrayList<Integer>> chunks = new ArrayList<>();
    public DataTown(String townName) {
        this.townName = townName;
        readFile();

    }

    //region Getters,setters,implemented functions
    public File getFile() {
        return new File(RedRealms.getPlugin().getDataFolder() + File.separator + "data" + File.separator +
                "towns" + File.separator + townName + ".yml");
    }

    public ArrayList<Integer> getChunkCoords() {
        return chunkCoords;
    }

    public void setChunkCoords(ArrayList<Integer> chunkCoords) {
        this.chunkCoords = chunkCoords;
    }
    public void setChunkCoords(int ChunkX,int ChunkZ) {
        chunkCoords.add(ChunkX);
        chunkCoords.add(ChunkZ);
    }
    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public void addChunk(int ChunkX,int ChunkZ){

        ArrayList<Integer> ChunkCoords = ChunkWork.chunkCreate(ChunkX,ChunkZ);
        chunks.add(ChunkCoords);
    }
    public void addChunk(Chunk chunk){
        ArrayList<Integer> ChunkCoords = ChunkWork.chunkCreate(chunk);
        chunks.add(ChunkCoords);
    }
    public ArrayList<ArrayList<Integer>> getChunks (){
        return chunks;
    }

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public ArrayList<String> getResidentNames() {
        return residentID;
    }
    public void addResident(String playerID){
        this.residentID.add(playerID);
    }
    public void setResidentID(ArrayList<String> residentNames) {
        this.residentID = residentNames;
    }
    //endregion
}

