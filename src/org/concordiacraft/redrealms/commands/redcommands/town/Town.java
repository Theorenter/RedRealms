package org.concordiacraft.redrealms.commands.redcommands.town;

import org.bukkit.entity.Player;
import org.concordiacraft.redrealms.gui.GUIMenu;
import org.concordiacraft.redrealms.main.RedRealms;
import org.concordiacraft.redutils.commands.RedCommand;

public class Town extends RedCommand {
    @Override
    public void init() {
        command = "/town";
        commands.put("leave", "Покинуть город");
        commands.put("menu", "Отрктыь меню города");
    }

    @Override
    public void showHelp() {
        sender.sendMessage("Анекдот");
    }

    public void leaveCMD() {

    }

    public void menuCMD() {
        if (!(sender instanceof Player)) {
            RedRealms.getPlugin().getRedLogger().info(RedRealms.getLocalization().getRawString("messages.errors.only-for-players"));
        }
        GUIMenu gui = new GUIMenu();
        gui.openInventory((Player) sender);
    }
}
