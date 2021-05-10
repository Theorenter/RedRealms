package org.concordiacraft.redrealms.listeners.reditems;

import org.bukkit.*;
import org.bukkit.block.banner.Pattern;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.concordiacraft.reditems.main.RedItems;
import org.concordiacraft.redrealms.config.ConfigDefault;
import org.concordiacraft.redrealms.config.ConfigLocalization;
import org.concordiacraft.redrealms.data.PromptData;
import org.concordiacraft.redrealms.main.RedRealms;
import org.concordiacraft.redrealms.prompts.town.TownCreatePrompt;
import org.concordiacraft.redrealms.utilits.ChunkWork;

import java.util.*;

/***
 * @author Theorenter
 * This class is responsible for the behavior of the
 * plugin when placing the banner for creating a town,
 * which belongs to the RedItems addon.
 */
public class TownBannerPlaceListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onTownBannerPlace(BlockPlaceEvent e) {

        PersistentDataContainer container = e.getItemInHand().getItemMeta().getPersistentDataContainer();
        if (!(container.has(new NamespacedKey(RedItems.getPlugin(), "UNQ-NEW-TOWN"), PersistentDataType.BYTE))) { return; } e.setCancelled(true);

        Player p = e.getPlayer();
        if (!p.hasPermission("redrealms.town.create")) {
            p.sendMessage(ConfigLocalization.getString("msg-error-dont-have-permissions-to-do"));
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 1, 1);
            return;
        }
        // TODO Учесть, что игрок не находится в другом городе/государстве

        // Item banner
        ItemStack townBanner = new ItemStack(e.getItemInHand());
        PromptData.addToPromptMap(p.getUniqueId(), townBanner);
        townBanner.setAmount(1);

        ItemStack inventoryStack = new ItemStack(e.getItemInHand());
        inventoryStack.setAmount(inventoryStack.getAmount() - 1);

        if (e.getHand().toString().equals("HAND")) {
            p.getInventory().setItemInMainHand(inventoryStack);
        } else {
            p.getInventory().setItemInOffHand(inventoryStack);
        }

        //Bukkit.getServer().getPluginManager().callEvent(new TownCreationConversationEvent(p, townBanner, p.getLocation().getChunk()));

        // Get biome type
        String thisBiomeName = ChunkWork.getBiome(p.getWorld().getChunkAt(p.getLocation())).name();
        String biomeType = null;

        for (Map.Entry<String, ArrayList<String>> entry : ConfigDefault.getBiomeMap().entrySet()) {
            for (String listBiomeName : entry.getValue()) {
                if (listBiomeName.equalsIgnoreCase(thisBiomeName)) {
                    biomeType = entry.getKey();
                    break;
                }
            }
        }

        // Conversation
        TownCreatePrompt prompt = new TownCreatePrompt(biomeType, p.getWorld().getChunkAt(p.getLocation()), townBanner);
        ConversationFactory cf = new ConversationFactory(RedRealms.getPlugin()).withFirstPrompt(prompt);
        cf.buildConversation(p).begin();



        //String townName = null;

        //p.sendMessage(ConfigLocalization.getFormatString("msg_town_create_1"));
        //Chunk chunk = p.getLocation().getChunk();

            //DataChunks dataChunk = new DataChunks();

        /*dataChunk.setWorld(chunk.getWorld());
        dataChunk.setChunk(chunk);
        dataChunk.readFile();
        if (dataChunk.getOwner() != null) {
            p.sendMessage(ConfigLocalization.getFormatString("msg_error_this_territory_already"));
        }
        dataChunk.setOwner(strings[0]);
        dataChunk.updateFile();*/

        //if (dataChunk.getOwner() != null){
        //    townCreator.sendMessage(ConfigLocalization.getFormatString("msg_error_this_territory_already"));
        //    return;
        //}
        //ItemStack townBanner = e.getItem();
        //townBanner.setAmount(townBanner.getAmount() - 1);
        //p.getInventory().setItemInMainHand(townBanner);
        // Установить здесь, чтобы израсходовался один баннер
        //Biome b = Objects.requireNonNull(e.getClickedBlock()).getBiome();
        //showBannerInfo(e.getItem(), e.getPlayer());
    }
    private static void showBannerInfo(ItemStack banner, Player p) {
        BannerMeta b = (BannerMeta) banner.getItemMeta();
        b.getPatterns();
        p.sendMessage("Паттерн баннера таков:");
        List<Pattern> tbPatterns = b.getPatterns();
        for (Pattern pt: tbPatterns) {
            p.sendMessage("Паттерн: " + pt.getPattern().getIdentifier() + " | Цвет:" + pt.getColor().toString());
        }
        p.sendMessage("Цвет флага: " + banner.getType().getKey().getKey());
    }
}
