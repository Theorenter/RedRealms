package org.concordiacraft.redrealms.data;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.block.banner.Pattern;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.concordiacraft.redrealms.events.TownCreationConversationEvent;
import org.concordiacraft.redrealms.main.RedRealms;
import org.concordiacraft.redrealms.utilits.ChunkWork;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RedTown extends RedData {
    private String townName;
    private String mayorID;

    private List<String> residentsIDList;
    private List<Integer> capitalChunk;

    private Map<String, List<Pattern>> townBanner;
    private List<ArrayList<Integer>> chunks = new ArrayList<>();

    // Rules
    private Map<String, Boolean> craftRules = new HashMap<>();
    private Map<String, Boolean> useRules = new HashMap<>();

    /**
     * THIS CONSTRUCTOR IS NOT USED TO CREATE TOWNS.
     * you should use RedData.CreateTown(params) or
     * another constructors
     * @param townName
     */
    protected RedTown(String townName) {
        this.townName = townName;
        readFile();
    }

    /**
     * Valid town constructor.
     *
     * @param townName - name of the town
     * @param townFounder - founder of the town.
     * @param townBanner - town flag.
     * @param capitalChunk - the chunk that the town will be based on.
     */
    public RedTown(String townName, Player townFounder, ItemStack townBanner, Chunk capitalChunk) {
        this.townName = townName;
        this.mayorID = townFounder.getUniqueId().toString();
        this.townBanner = new HashMap<>(); this.townBanner.put(townBanner.getType().getKey().getKey(), ((BannerMeta) townBanner.getItemMeta()).getPatterns());
        this.residentsIDList = new ArrayList<>(); this.residentsIDList.add(townFounder.getUniqueId().toString());

        RedPlayer redPlayer = RedData.loadPlayer(townFounder);
        redPlayer.setTownName(townName);
        redPlayer.updateFile();

        addChunk(capitalChunk);
        Bukkit.getServer().getPluginManager().callEvent(new TownCreationConversationEvent(townName, townFounder, townBanner, capitalChunk));
        RedData.loadChunk(capitalChunk);
        updateFile();
    }

    // Getters, setters, implemented functions

    /**
     * @return file of the town.
     */
    public File getFile() {
        return new File(RedRealms.getPlugin().getDataFolder() + File.separator + "data" + File.separator +
                "towns" + File.separator + townName + ".yml");
    }

    /**
     * @return capital chunk.
     */
    public List<Integer> getCapitalChunk() {
        return capitalChunk;
    }

    /**
     * @param capitalChunk - main town Chunk.
     */
    public void setCapitalChunk(ArrayList<Integer> capitalChunk) {
        this.capitalChunk = capitalChunk;
    }

    /**
     * @return town name;
     */
    public String getTownName() {
        return townName;
    }

    /**
     * @param townName - name of the town.
     */
    public void setTownName(String townName) {
        this.townName = townName;
    }

    /**
     * Add a chunk to the town.
     * @param ChunkX - Z coordinate of the chunk.
     * @param ChunkZ - X coordinate of the chunk.
     */
    public void addChunk(int ChunkX, int ChunkZ) {
        ArrayList<Integer> ChunkCoords = ChunkWork.chunkCreate(ChunkX,ChunkZ);
        chunks.add(ChunkCoords);
    }

    /**
     * Add a chunk to the town.
     * @param chunk - chunk to add to the town.
     */
    public void addChunk(Chunk chunk) {
        ArrayList<Integer> ChunkCoords = ChunkWork.chunkCreate(chunk);
        chunks.add(ChunkCoords);
    }

    /**
     * @return all chunks within the town.
     */
    public List<ArrayList<Integer>> getChunks() {
        return chunks;
    }

    /**
     * @param playerID - UUID of the player to be appointed as the head of the town.
     */
    public void setMayorID(String playerID) {
        this.mayorID = playerID;
    }

    /**
     * @return UUID of all citizens of the town.
     */
    public List<String> getCitizenIDs() {
        return residentsIDList;
    }


    /**
     * Add a player to the town.
     * @param playerID - UUID of the citizen.
     */
    public void addCitizen(String playerID){
        this.residentsIDList.add(playerID);
    }

    // rules

    /**
     * @return craft rules.
     */
    public Map<String, Boolean> getCraftRules() { return craftRules; }

    /**
     * @return use rules.
     */
    public Map<String, Boolean> getUseRules() { return useRules; }


    /*public void setResidentID(ArrayList<String> residentNames) {
        this.residentsIDList = residentNames;
    }*/
}

