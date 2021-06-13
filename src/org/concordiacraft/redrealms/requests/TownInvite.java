package org.concordiacraft.redrealms.requests;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.entity.Player;
import org.concordiacraft.redrealms.data.RedData;
import org.concordiacraft.redrealms.data.RedPlayer;
import org.concordiacraft.redrealms.data.RedTown;
import org.concordiacraft.redrealms.main.RedRealms;
import org.concordiacraft.redutils.requests.BinaryRequest;
import org.concordiacraft.redutils.requests.RequestType;
import net.md_5.bungee.api.chat.TextComponent;

public class TownInvite extends BinaryRequest {

    private final RedPlayer rSender;
    private final RedPlayer rReceiver;

    public TownInvite(Player sender, Player receiver, RequestType type) {
        super(sender, receiver, type);
        this.rSender = RedData.loadPlayer(sender);
        this.rReceiver = RedData.loadPlayer(receiver);

        showRequest();
        rReceiver.addRequest(this);
    }

    @Override
    public void onAccept() {

        if (!rSender.hasTown()) {
            requestReceiver.sendRawMessage(RedRealms.getLocalization().getString("messages.errors.town-does-not-exist"));
            requestReceiver.playSound(requestReceiver.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                    RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            rReceiver.removeRequest(type);
            return;
        }

        RedTown redTown = RedData.loadTown(rSender.getTownName());
        redTown.addCitizen(rReceiver);
        requestSender.sendRawMessage(String.format(RedRealms.getLocalization().getString("messages.notifications.player-accept-town-invite"), rReceiver.getName()));
        rReceiver.removeRequest(type);
    }

    @Override
    public void onDecline() {
        requestSender.sendRawMessage(String.format(RedRealms.getLocalization().getString("messages.notifications.player-decline-town-invite"), rReceiver.getName()));
        rReceiver.removeRequest(type);
    }

    @Override
    public void showRequest() {
        requestReceiver.sendRawMessage(String.format(RedRealms.getLocalization().getString("messages.notifications.you-were-invited-town"), rSender.getName(), rSender.getTownName()));
        TextComponent acceptC = new TextComponent();
        acceptC.setText("[" + RedRealms.getLocalization().getString("messages.active-text.accept") + "]");
        acceptC.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(RedRealms.getLocalization().getString("messages.hovers.accept-town-invite"))));
        acceptC.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/town join"));

        TextComponent declineC = new TextComponent();
        declineC.setText("[" + RedRealms.getLocalization().getString("messages.active-text.decline") + "]");
        declineC.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(RedRealms.getLocalization().getString("messages.hovers.decline-town-invite"))));
        declineC.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/town decline"));

        TextComponent universalComponent = new TextComponent();
        universalComponent.addExtra(acceptC); universalComponent.addExtra(" "); universalComponent.addExtra(declineC);

        requestReceiver.spigot().sendMessage(universalComponent);

    }

}
