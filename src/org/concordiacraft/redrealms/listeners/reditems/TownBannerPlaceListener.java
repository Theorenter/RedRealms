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
            p.playSound(p.getLocation(), ConfigDefault.getErrorSoundName(), ConfigDefault.getErrorSoundVolume(), ConfigDefault.getErrorSoundPitch());
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
    }
}
