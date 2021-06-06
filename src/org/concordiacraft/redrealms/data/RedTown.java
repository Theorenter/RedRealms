package org.concordiacraft.redrealms.data;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.block.banner.Pattern;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.concordiacraft.redrealms.events.TownCreationConversationEvent;
import org.concordiacraft.redrealms.main.RedRealms;
import org.concordiacraft.redrealms.rules.RuleManager;
import org.concordiacraft.redrealms.utilits.ChunkWork;

import java.io.File;
import java.util.*;

public class RedTown extends RedData {
    private String name;
    private String mayorID;

    private List<String> citizensIDs = new ArrayList<>();
    private List<Integer> capitalChunk = new ArrayList<>();

    private HashMap<String, List<Pattern>> townBanner;
    private List<List<Integer>> chunks = new ArrayList<>();

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
        this.name = townName;
        readFile();
    }

    /**
     * Valid town constructor.
     *
     * @param townName name of the town
     * @param townFounder founder of the town.
     * @param townBanner town flag.
     * @param capitalChunk the chunk that the town will be based on.
     */
    public RedTown(String townName, Player townFounder, ItemStack townBanner, Chunk capitalChunk) {
        this.name = townName;
        this.mayorID = townFounder.getUniqueId().toString();
        this.townBanner = new HashMap<>(); this.townBanner.put(townBanner.getType().getKey().getKey(), ((BannerMeta) townBanner.getItemMeta()).getPatterns());
        this.citizensIDs = new ArrayList<>(); this.citizensIDs.add(townFounder.getUniqueId().toString());

        RedPlayer redPlayer = RedData.loadPlayer(townFounder);
        redPlayer.setTownName(townName);
        redPlayer.updateFile();

        // rules
        this.craftRules = RuleManager.getCraftPattern();
        this.useRules = RuleManager.getUsePattern();

        setCapitalChunk(capitalChunk);
        addChunk(capitalChunk);

        Bukkit.getServer().getPluginManager().callEvent(new TownCreationConversationEvent(townName, townFounder, townBanner, capitalChunk));
        RedChunk rc = RedData.loadChunk(capitalChunk);
        rc.setTownOwner(name);
        rc.updateFile();
        updateFile();
    }

    // Getters, setters, implemented functions

    /**
     * @return file of the town.
     */
    public File getFile() {
        return new File(RedRealms.getPlugin().getDataFolder() + File.separator + "data" + File.separator +
                "towns" + File.separator + name + ".yml");
    }

    /**
     * @return capital chunk.
     */
    public List<Integer> getCapitalChunk() {
        return capitalChunk;
    }

    /**
     * @param capitalChunk main town Chunk.
     */
    public void setCapitalChunk(List<Integer> capitalChunk) {
        this.capitalChunk = capitalChunk;

    }

    /**
     * @param capitalChunk main town Chunk.
     */
    public void setCapitalChunk(Chunk capitalChunk) {
        this.setCapitalChunk(ChunkWork.chunkCreate(capitalChunk));
    }

    /**
     * @return town name;
     */
    public String getName() {
        return name;
    }

    /**
     * @param name name of the town.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Add a chunk to the town.
     * @param chunk
     */
    public void addChunk(List<Integer> chunk) {
        chunks.add(chunk);
    }

    /**
     * Add a chunk to the town.
     * @param chunk chunk to add to the town.
     */
    public void addChunk(Chunk chunk) {
        ArrayList<Integer> ChunkCoords = ChunkWork.chunkCreate(chunk);
        chunks.add(ChunkCoords);
    }

    /**
     * @return all chunks within the town.
     */
    public List<List<Integer>> getChunks() {
        return chunks;
    }

    /**
     * @param playerID UUID of the player to be appointed as the head of the town.
     */
    public void setMayorID(String playerID) {
        this.mayorID = playerID;
    }

    /**
     * @return UUID of all citizens of the town.
     */
    public List<String> getCitizenIDs() {
        return citizensIDs;
    }


    /**
     * Add a player to the town.
     * @param playerID UUID of the citizen.
     */
    public void addCitizen(String playerID){
        this.citizensIDs.add(playerID);
    }

    /**
     * @return craft rules.
     */
    public Map<String, Boolean> getCraftRules() { return craftRules; }

    /**
     * @return use rules.
     */
    public Map<String, Boolean> getUseRules() { return useRules; }

    public void kickPlayer(Player player) {

        RedPlayer rp = RedData.loadPlayer(player);
        citizensIDs.remove(player.getUniqueId().toString());
        rp.setTownName(null);
        rp.updateFile();
        if (citizensIDs.isEmpty()) {
            delete();
            for (Player p : Bukkit.getServer().getOnlinePlayers())
                p.sendRawMessage(String.format(RedRealms.getLocalization().getString("messages.notifications.town-was-deserted"), name));
            return;
        }

        if (this.mayorID.equals(rp.getId())) {
            for (String citizenID : citizensIDs) {
                if (!citizenID.equals(this.mayorID)) {
                    this.setMayorID(citizenID);
                    updateFile();
                    return;
                }
            }
        }

        updateFile();
    }

    public void delete() {
        getAllTowns().remove(this.name);
        deleteFile();
    }



    /*public void setResidentID(ArrayList<String> residentNames) {
        this.residentsIDList = residentNames;
    }*/
}

