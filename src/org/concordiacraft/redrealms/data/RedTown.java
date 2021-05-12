package org.concordiacraft.redrealms.data;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.block.banner.Pattern;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.concordiacraft.redrealms.data.RedChunk;
import org.concordiacraft.redrealms.data.RedData;
import org.concordiacraft.redrealms.events.TownCreationConversationEvent;
import org.concordiacraft.redrealms.main.RedRealms;
import org.concordiacraft.redrealms.utilits.ChunkWork;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RedTown extends RedData {
    private String townName;
    private String mayorID;

    private ArrayList<String> residentID = new ArrayList<>();
    ArrayList<Integer> capitalChunk = new ArrayList<>();

    private HashMap<String, List<Pattern>> townBanner = new HashMap<>();
    ArrayList<ArrayList<Integer>> chunks = new ArrayList<>();

    public RedTown(String townName) {
        this.townName = townName;
        readFile();
    }

    public RedTown(String townName, Player mayor, ItemStack townBanner, Chunk capitalChunk) {
        if(!readFile())
        this.townName = townName;
        this.mayorID = mayor.getUniqueId().toString();
        this.townBanner.put(townBanner.getType().getKey().getKey(), ((BannerMeta) townBanner.getItemMeta()).getPatterns());
        addChunk(capitalChunk);
        Bukkit.getServer().getPluginManager().callEvent(new TownCreationConversationEvent(townName, mayor, townBanner, capitalChunk));
        RedData.createChunk(capitalChunk);
        updateFile();
    }

    //region Getters,setters,implemented functions
    public File getFile() {
        return new File(RedRealms.getPlugin().getDataFolder() + File.separator + "data" + File.separator +
                "towns" + File.separator + townName + ".yml");
    }

    public ArrayList<Integer> getCapitalChunk() {
        return capitalChunk;
    }

    public void setCapitalChunk(ArrayList<Integer> capitalChunk) {
        this.capitalChunk = capitalChunk;
    }
    public void setChunkCoords(int ChunkX,int ChunkZ) {
        capitalChunk.add(ChunkX);
        capitalChunk.add(ChunkZ);
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

    public void setMayorID(String playerID) {
        this.mayorID = playerID;
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

