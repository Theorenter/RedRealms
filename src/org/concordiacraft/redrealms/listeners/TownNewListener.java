package org.concordiacraft.redrealms.listeners;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Biome;
import org.bukkit.block.banner.Pattern;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.concordiacraft.redrealms.main.RedRealms;

import java.util.List;
import java.util.Objects;

public class TownNewListener implements Listener {
    RedRealms plugin;

    public TownNewListener(RedRealms plugin) { this.plugin = plugin; }

    @EventHandler(priority = EventPriority.LOWEST)
    public  void onTownNew(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) { return; }
        if (e.getPlayer().getInventory().getItemInMainHand().getType() == Material.AIR) { return; }
        PersistentDataContainer container = e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer();
        if (!(container.has(new NamespacedKey(RedRealms.getPlugin(), "UNQ-NEW-TOWN"), PersistentDataType.BYTE))) { return; }
        e.setCancelled(true);

        Player p = e.getPlayer();
        if (!p.hasPermission("redrealms.town.create")) { return; }

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
        ItemStack townBanner = e.getItem();
        townBanner.setAmount(townBanner.getAmount() - 1);
        p.getInventory().setItemInMainHand(townBanner);
        // Установить здесь, чтобы израсходовался один баннер
        Biome b = Objects.requireNonNull(e.getClickedBlock()).getBiome();
    }
    public static void showBannerInfo(ItemStack banner, Player p) {
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
