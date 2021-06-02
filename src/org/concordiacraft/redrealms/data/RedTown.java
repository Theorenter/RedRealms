package org.concordiacraft.redrealms.data;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.block.banner.Pattern;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.concordiacraft.redrealms.events.TownCreationConversationEvent;
import org.concordiacraft.redrealms.main.RedRealms;
import org.concordiacraft.redrealms.rules.RuleManaged;
import org.concordiacraft.redrealms.utilits.ChunkWork;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RedTown extends RedData implements RuleManaged {
    private String townName;
    private String mayorID;

    private List<String> residentsIDList;
    private List<Integer> capitalChunk;

    private Map<String, List<Pattern>> townBanner;
    private List<ArrayList<Integer>> chunks = new ArrayList<>();

    private Map<String, Boolean> rules;

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
    public RedTown(String townName, Player townFounder, ItemStack townBanner, Chunk capitalChunk, Map<String, Boolean> ruleSet) {
        this.townName = townName;
        this.mayorID = townFounder.getUniqueId().toString();
        this.townBanner = new HashMap<>(); this.townBanner.put(townBanner.getType().getKey().getKey(), ((BannerMeta) townBanner.getItemMeta()).getPatterns());
        this.residentsIDList = new ArrayList<>(); this.residentsIDList.add(townFounder.getUniqueId().toString());
        this.rules = new HashMap<>(); this.rules = ruleSet;

        RedPlayer redPlayer = RedData.createPlayer(townFounder);
        redPlayer.setPlayerTownName(townName);
        redPlayer.updateFile();

        addChunk(capitalChunk);
        Bukkit.getServer().getPluginManager().callEvent(new TownCreationConversationEvent(townName, townFounder, townBanner, capitalChunk));
        RedData.createChunk(capitalChunk);
        updateFile();
    }

    @Override
    public void changeRule(String ruleID, boolean value) {
        this.rules.put(ruleID, value);
    }

    // Getters, setters, implemented functions
    public File getFile() {
        return new File(RedRealms.getPlugin().getDataFolder() + File.separator + "data" + File.separator +
                "towns" + File.separator + townName + ".yml");
    }

    public List<Integer> getCapitalChunk() {
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

    public void addChunk(int ChunkX, int ChunkZ){

        ArrayList<Integer> ChunkCoords = ChunkWork.chunkCreate(ChunkX,ChunkZ);
        chunks.add(ChunkCoords);
    }
    public void addChunk(Chunk chunk){
        ArrayList<Integer> ChunkCoords = ChunkWork.chunkCreate(chunk);
        chunks.add(ChunkCoords);
    }
    public List<ArrayList<Integer>> getChunks (){
        return chunks;
    }

    public void setMayorID(String playerID) {
        this.mayorID = playerID;
    }

    public List<String> getResidentNames() {
        return residentsIDList;
    }

    public void addResident(String playerID){
        this.residentsIDList.add(playerID);
    }

    public void setResidentID(ArrayList<String> residentNames) {
        this.residentsIDList = residentNames;
    }
    //endregion
}

