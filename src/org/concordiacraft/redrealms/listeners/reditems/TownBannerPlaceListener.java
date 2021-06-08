package org.concordiacraft.redrealms.listeners.reditems;

import org.bukkit.*;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.concordiacraft.reditems.main.RedItems;
import org.concordiacraft.redrealms.data.PromptData;
import org.concordiacraft.redrealms.data.RedChunk;
import org.concordiacraft.redrealms.data.RedData;
import org.concordiacraft.redrealms.data.RedPlayer;
import org.concordiacraft.redrealms.main.RedRealms;
import org.concordiacraft.redrealms.prompts.town.TownCreate;
import org.concordiacraft.redrealms.utilits.BiomeManager;
import org.concordiacraft.redrealms.utilits.ChunkWork;

/**
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
            p.sendMessage(RedRealms.getLocalization().getString("messages.errors.don't-have-permissions-to-do"));
            p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(), RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            return;
        }
        RedPlayer RedP = RedData.loadPlayer(e.getPlayer());

        // check world
        if (!RedRealms.getDefaultConfig().getAvailableWorlds().contains(e.getPlayer().getWorld().getName())) {
            p.sendRawMessage(RedRealms.getLocalization().getString("messages.errors.create-town-in-unavailable-world"));
            p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(), RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            return;
        }

        // check if player already in town
        if (RedP.hasTown()) {
            e.setCancelled(true);
            p.sendRawMessage(String.format(RedRealms.getLocalization().getString("messages.errors.player-already-in-town"), RedP.getTownName()));
            p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(), RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            return;
        }

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
        String biomeType = BiomeManager.getBiomeType(thisBiomeName);

        // check chunk
        RedChunk rc = RedData.loadChunk(e.getBlock().getChunk());
        if (rc.hasTownOwner()) {
            p.sendRawMessage(String.format(RedRealms.getLocalization().getString("messages.errors.this-territory-already-taken"), rc.getTownOwner()));
            p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                    RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            e.setCancelled(true);
        }

        // Conversation
        TownCreate prompt = new TownCreate(biomeType, p.getWorld().getChunkAt(p.getLocation()), townBanner);
        ConversationFactory cf = new ConversationFactory(RedRealms.getPlugin()).withFirstPrompt(prompt);
        cf.buildConversation(p).begin();
    }
}
