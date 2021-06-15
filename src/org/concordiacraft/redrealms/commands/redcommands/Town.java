package org.concordiacraft.redrealms.commands.redcommands;

import net.tnemc.core.economy.Account;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.concordiacraft.redrealms.data.RedData;
import org.concordiacraft.redrealms.data.RedPlayer;
import org.concordiacraft.redrealms.data.RedTown;
import org.concordiacraft.redrealms.gui.GUITown;
import org.concordiacraft.redrealms.main.RedRealms;
import org.concordiacraft.redrealms.requests.RequestTypes;
import org.concordiacraft.redrealms.requests.TownInvite;
import org.concordiacraft.redutils.commands.RedCommand;

import java.math.BigDecimal;

public class Town extends RedCommand {
    @Override
    public void init() {
        command = "/town";
        commands.put("invite", "Пригласить игрока в город");
        commands.put("leave", "Покинуть город");
        commands.put("menu", "Открыть меню города");
        commands.put("join", "Принять приглашение в город");
        commands.put("decline", "Принять приглашение в город");
        commands.put("balance", "Посмотреть баланс города");
        commands.put("deposit", "Положить золото в казну города");
        commands.put("withdraw", "Изъять золото из казны города");
    }

    @Override
    public void showHelp() {
        sender.sendMessage(RedRealms.getLocalization().getString("messages.help.town.header"));
        sender.sendMessage(RedRealms.getLocalization().getString("messages.help.town.menu"));
        sender.sendMessage(RedRealms.getLocalization().getString("messages.help.town.invite"));
        sender.sendMessage(RedRealms.getLocalization().getString("messages.help.town.leave"));
        sender.sendMessage(RedRealms.getLocalization().getString("messages.help.town.join"));
        sender.sendMessage(RedRealms.getLocalization().getString("messages.help.town.decline"));
        sender.sendMessage(RedRealms.getLocalization().getString("messages.help.town.balance"));
        sender.sendMessage(RedRealms.getLocalization().getString("messages.help.town.deposit"));
        sender.sendMessage(RedRealms.getLocalization().getString("messages.help.town.withdraw"));
    }

    public void leaveCMD() {
        if (!(sender instanceof Player)) {
            RedRealms.getPlugin().getRedLogger().info(RedRealms.getLocalization().getRawString("messages.errors.only-for-players"));
            return;
        }
        Player p = (Player) sender;

        if (!sender.hasPermission("redrealms.town.leave")) {
            ((Player) sender).sendRawMessage(RedRealms.getLocalization().getString("messages.errors.don't-have-permissions-command"));
            p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                    RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            return;
        }

        RedPlayer rp = RedData.loadPlayer(p);

        if (!rp.hasTown()) {
            p.sendRawMessage(RedRealms.getLocalization().getString("messages.errors.player-not-in-town"));
            p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                    RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            return;
        }

        RedTown rt = RedData.loadTown(rp.getTownName());
        rt.kickPlayer(p);
        p.sendRawMessage(String.format(RedRealms.getLocalization().getString("messages.notifications.leave-town"), rt.getName()));
    }

    public void menuCMD() {
        if (!(sender instanceof Player)) {
            RedRealms.getPlugin().getRedLogger().info(RedRealms.getLocalization().getRawString("messages.errors.only-for-players"));
            return;
        }

        Player p = (Player) sender;

        if (!sender.hasPermission("redrealms.town.menu.open")) {
            sender.sendMessage(RedRealms.getLocalization().getString("messages.errors.don't-have-permissions-command"));
            p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                    RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            return;
        }

        RedPlayer rp = RedData.loadPlayer(p);

        if (!rp.hasTown()) {
            p.sendRawMessage(RedRealms.getLocalization().getString("messages.errors.player-not-in-town"));
            p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                    RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            return;
        }


        GUITown gui = new GUITown();
        gui.openInventory((Player) sender);
    }

    public void inviteCMD() {
        if (!(sender instanceof Player)) {
            RedRealms.getPlugin().getRedLogger().info(RedRealms.getLocalization().getRawString("messages.errors.only-for-players"));
            return;
        }

        Player pSender = (Player) sender;

        if (!sender.hasPermission("redrealms.town.mayor.invite")) {
            sender.sendMessage(RedRealms.getLocalization().getString("messages.errors.don't-have-permissions-command"));
            pSender.playSound(pSender.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                    RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            return;
        }

        RedPlayer rpSender = RedData.loadPlayer(pSender);

        if (!rpSender.hasTown()) {
            pSender.sendRawMessage(RedRealms.getLocalization().getString("messages.errors.player-not-in-town"));
            pSender.playSound(pSender.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                    RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            return;
        }

        if (!rpSender.isMayor()) {
            pSender.sendRawMessage(RedRealms.getLocalization().getString("messages.errors.player-is-not-mayor"));
            pSender.playSound(pSender.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                    RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            return;
        }

        if (args.length != 2) {
            pSender.sendRawMessage(RedRealms.getLocalization().getString("messages.errors.invalid-length-arguments"));
            pSender.playSound(pSender.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                    RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            return;
        }

        if (Bukkit.getPlayer(args[1]) == null) {
            pSender.sendRawMessage(String.format(RedRealms.getLocalization().getString("messages.errors.player-not-on-the-server"), args[1]));
            pSender.playSound(pSender.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                    RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            return;
        }

        Player pReceiver = Bukkit.getPlayer(args[1]);
        RedPlayer rpReceiver = RedData.loadPlayer(pReceiver);

        if (rpReceiver.hasTown()) {
            pSender.sendRawMessage(String.format(RedRealms.getLocalization().getString("messages.errors.another-player-already-in-town"), args[1], rpReceiver.getTownName()));
            pSender.playSound(pSender.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                    RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            return;
        }

        if (rpReceiver.getRequests().containsKey(RequestTypes.TOWN_INVITE)) {
            pSender.sendRawMessage(String.format(RedRealms.getLocalization().getString("messages.errors.already-has-town-invite"), pReceiver.getName()));
            pSender.playSound(pSender.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                    RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            return;
        }
        RedRealms.getPlugin().getRedLogger().info("Player " + pSender.getName() + " sent an invitation to the town to the player " + pReceiver.getName() + ".");

        new TownInvite(pSender, pReceiver, RequestTypes.TOWN_INVITE);
        pSender.sendRawMessage(String.format(RedRealms.getLocalization().getString("messages.notifications.town-invite-was-sent"), pReceiver.getName()));
    }

    public void joinCMD() {

        Player p = (Player) sender;

        if (!sender.hasPermission("redrealms.town.join")) {
            sender.sendMessage(RedRealms.getLocalization().getString("messages.errors.don't-have-permissions-command"));
            p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                    RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            return;
        }

        RedPlayer rp = RedData.loadPlayer(p);

        if (!rp.hasRequest(RequestTypes.TOWN_INVITE)) {
            p.sendRawMessage(RedRealms.getLocalization().getString("messages.errors.has-not-invited-town"));
            p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                    RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            return;
        }

        if (rp.hasTown()) {
            p.sendRawMessage(RedRealms.getLocalization().getString("messages.errors.player-already-in-town"));
            p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                    RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            return;
        }

        TownInvite br = (TownInvite) rp.getRequests().get(RequestTypes.TOWN_INVITE);
        br.onAccept();
    }

    public void declineCMD() {

        Player p = (Player) sender;

        if (!sender.hasPermission("redrealms.town.decline")) {
            sender.sendMessage(RedRealms.getLocalization().getString("messages.errors.don't-have-permissions-command"));
            p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                    RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            return;
        }

        RedPlayer rp = RedData.loadPlayer(p);

        if (!rp.hasRequest(RequestTypes.TOWN_INVITE)) {
            p.sendRawMessage(RedRealms.getLocalization().getString("messages.errors.has-not-invited-town"));
            p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                    RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            return;
        }

        if (rp.hasTown()) {
            p.sendRawMessage(String.format(RedRealms.getLocalization().getString("messages.errors.player-already-in-town"), rp.getTownName()));
            p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                    RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            return;
        }

        TownInvite br = (TownInvite) rp.getRequests().get(RequestTypes.TOWN_INVITE);
        br.onDecline();
    }

    public void balanceCMD() {
        if (!(sender instanceof Player)) {
            RedRealms.getPlugin().getRedLogger().info(RedRealms.getLocalization().getRawString("messages.errors.only-for-players"));
            return;
        }

        Player p = (Player) sender;

        if (!sender.hasPermission("redrealms.town.balance")) {
            sender.sendMessage(RedRealms.getLocalization().getString("messages.errors.don't-have-permissions-command"));
            p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                    RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            return;
        }

        RedPlayer rp = RedData.loadPlayer(p);

        if (!rp.hasTown()) {
            p.sendRawMessage(RedRealms.getLocalization().getString("messages.errors.player-not-in-town"));
            p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                    RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            return;
        }
        RedTown rt = RedData.loadTown(rp.getTownName());

        p.sendRawMessage(String.format(RedRealms.getLocalization().getString("messages.notifications.town-balance"), rp.getTownName(), rt.getBalance()));
    }

    public void depositCMD() {
        if (!(sender instanceof Player)) {
            RedRealms.getPlugin().getRedLogger().info(RedRealms.getLocalization().getRawString("messages.errors.only-for-players"));
            return;
        }

        Player p = (Player) sender;

        if (!sender.hasPermission("redrealms.town.balance")) {
            sender.sendMessage(RedRealms.getLocalization().getString("messages.errors.don't-have-permissions-command"));
            p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                    RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            return;
        }

        RedPlayer rp = RedData.loadPlayer(p);

        if (!rp.hasTown()) {
            p.sendRawMessage(RedRealms.getLocalization().getString("messages.errors.player-not-in-town"));
            p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                    RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            return;

        }

        if (args.length != 2) {
            p.sendRawMessage(RedRealms.getLocalization().getString("messages.errors.invalid-length-arguments"));
            p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                    RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            return;
        }

        try {

            BigDecimal s = new BigDecimal(args[1]);
            RedTown rt = RedData.loadTown(rp.getTownName());

            double a = Double.parseDouble(args[1]);

            if (a < 0) {
                p.sendRawMessage(RedRealms.getLocalization().getString("messages.errors.number-must-be-positive"));
                p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                        RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
                return;
            }

            Account depAcc = RedRealms.getTNEAPI().getAccount(p.getUniqueId());
            if (!depAcc.hasHoldings(s)) {
                p.sendRawMessage(RedRealms.getLocalization().getString("messages.errors.not-enough-money"));
                p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                        RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
                return;
            }

            depAcc.removeHoldings(s);
            rt.addBalance(s);

            p.sendRawMessage(String.format(RedRealms.getLocalization().getString("messages.notifications.successful-deposit"), rp.getTownName(), s));
        } catch (NumberFormatException e) {
            p.sendRawMessage(RedRealms.getLocalization().getString("messages.errors.number-format-exception"));
            p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                    RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
        }
    }
    public void withdrawCMD() {
        if (!(sender instanceof Player)) {
            RedRealms.getPlugin().getRedLogger().info(RedRealms.getLocalization().getRawString("messages.errors.only-for-players"));
            return;
        }

        Player p = (Player) sender;

        if (!sender.hasPermission("redrealms.town.balance")) {
            sender.sendMessage(RedRealms.getLocalization().getString("messages.errors.don't-have-permissions-command"));
            p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                    RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            return;
        }

        RedPlayer rp = RedData.loadPlayer(p);

        if (!rp.isMayor()) {
            p.sendRawMessage(RedRealms.getLocalization().getString("messages.errors.player-is-not-mayor"));
            p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                    RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            return;

        }

        if (args.length != 2) {
            p.sendRawMessage(RedRealms.getLocalization().getString("messages.errors.invalid-length-arguments"));
            p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                    RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            return;
        }

        try {

            BigDecimal s = new BigDecimal(args[1]);

            double a = Double.parseDouble(args[1]);

            if (a < 0) {
                p.sendRawMessage(RedRealms.getLocalization().getString("messages.errors.number-must-be-positive"));
                p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                        RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
                return;
            }

            RedTown rt = RedData.loadTown(rp.getTownName());

            if (rt.getBalance().compareTo(s) < 0) {
                p.sendRawMessage(String.format(RedRealms.getLocalization().getString(("messages.errors.not-enough-money-town")), rp.getTownName(), rt.getBalance()));
                p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                        RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
                return;
            }

            rt.decBalance(s);
            RedRealms.getTNEAPI().getAccount(p.getUniqueId()).addHoldings(s);

            p.sendRawMessage(String.format(RedRealms.getLocalization().getString("messages.notifications.successful-withdraw"), s, rp.getTownName()));
        } catch (NumberFormatException e) {
            p.sendRawMessage(RedRealms.getLocalization().getString("messages.errors.number-format-exception"));
            p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                    RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
        }
    }
}
