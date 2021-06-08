package org.concordiacraft.redrealms.prompts.town;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;
import org.bukkit.entity.Player;
import org.concordiacraft.redrealms.data.RedTown;
import org.concordiacraft.redrealms.main.RedRealms;

import java.awt.*;

public class TownInvite extends ValidatingPrompt {

    private final RedTown townInviting;
    private final Player playerInviter;
    private Boolean action = null;

    public TownInvite(RedTown townInviting, Player playerInviter) {
        this.townInviting = townInviting;
        this.playerInviter = playerInviter;
    }

    @Override
    public String getPromptText(ConversationContext context) {
        Player p = (Player) context.getForWhom();

        TextComponent tcChoose;

        TextComponent tcYes = new TextComponent("[" + RedRealms.getLocalization().getString( "messages.active-text.yes") + "]");
        tcYes.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "accept"));

        TextComponent tcNo = new TextComponent("[" + RedRealms.getLocalization().getString( "messages.active-text.no") + "]");
        tcNo.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "deny"));

        tcChoose = new TextComponent(tcYes + " " + tcNo);

        p.sendRawMessage(String.format(RedRealms.getLocalization().getString("messages.notifications.you-were-invited-town"), playerInviter, townInviting));
        p.spigot().sendMessage(tcChoose);
        return "";
    }

    @Override
    protected Prompt acceptValidatedInput(ConversationContext context, String s) {
        if (action == null) {

        }
        if (action) {

        } else {

        }
        return END_OF_CONVERSATION;
    }

    @Override
    protected boolean isInputValid(ConversationContext context, String s) {
        if (s.equals("accept"))
            action = true;
        if (s.equals("deny"))
            action = false;
        return true;
    }

}
