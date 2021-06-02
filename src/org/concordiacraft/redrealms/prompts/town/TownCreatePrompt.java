package org.concordiacraft.redrealms.prompts.town;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.concordiacraft.redrealms.config.ConfigDefault;
import org.concordiacraft.redrealms.data.RedData;
import org.concordiacraft.redrealms.data.RedTown;
import org.concordiacraft.redrealms.data.PromptData;
import org.concordiacraft.redrealms.main.RedRealms;
import org.concordiacraft.redutils.utils.RedDataConverter;
import org.concordiacraft.redutils.utils.RedFormatter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class TownCreatePrompt extends ValidatingPrompt {

    private String biomeLocale;
    private String biomeType;
    private String hoverText;
    private boolean hasInvalidInput = false;
    private Chunk newTownChunk;
    private ItemStack townBanner;

    public TownCreatePrompt(String biomeType, Chunk newTownChunk, ItemStack townBanner) {

        this.biomeType = biomeType;
        this.newTownChunk = newTownChunk;
        this.townBanner = townBanner;

        biomeLocale = RedRealms.getLocalization().getRawString("biomes." + biomeType + "-biome");
        hoverText = RedRealms.getLocalization().getString("conversations.prompts.consul.town-create-start.hover-" + biomeType);

    }
    @Override
    public String getPromptText(ConversationContext context) {

        String stringSpace = RedRealms.getLocalization().getRawString("conversations.space");

        String charPortrait = RedRealms.getLocalization().getString("conversations.characters.consul.portrait");
        String charName = stringSpace + RedRealms.getLocalization().getString("conversations.name-preformat") +
                RedRealms.getLocalization().getString("conversations.characters.consul.name");
        String s1 = stringSpace;
        String s2 = stringSpace;
        String s3 = stringSpace;

        Player p = (Player) context.getForWhom();

        if (biomeLocale == null) {

            s1 += RedRealms.getLocalization().getString("conversations.prompts.consul.cannot-create-town-there.1");
            s2 += RedRealms.getLocalization().getString("conversations.prompts.consul.cannot-create-town-there.2");
            s3 += RedRealms.getLocalization().getString("conversations.prompts.consul.cannot-create-town-there.3");

            p.sendRawMessage(charPortrait);
            p.sendRawMessage(RedFormatter.format(charName));

            p.sendRawMessage(RedFormatter.format(s1));
            p.sendRawMessage(RedFormatter.format(s2));
            p.sendRawMessage(RedFormatter.format(s3));

            return "";
        }

        if (!hasInvalidInput) {
            String helpColor = RedRealms.getLocalization().getRawString("components-color.help");
            String convTextColor = RedRealms.getLocalization().getRawString("components-color.conversation-text");

            p.sendRawMessage(charPortrait);
            p.sendRawMessage(RedFormatter.format(charName));

            s1 = RedRealms.getLocalization().getString("conversations.prompts.consul.town-create-start.1");
            s2 += RedRealms.getLocalization().getString("conversations.prompts.consul.town-create-start.2");
            s3 += RedRealms.getLocalization().getString("conversations.prompts.consul.town-create-start.3");

            TextComponent tc = new TextComponent(stringSpace);

            TextComponent hoverComponent = new TextComponent(biomeLocale);
            hoverComponent.setColor(ChatColor.of(helpColor));
            hoverComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(hoverText)));
            hoverComponent.setBold(true);

            TextComponent tcText = new TextComponent(String.format(s1, ""));
            tcText.setColor(ChatColor.of(convTextColor));

            tc.addExtra(hoverComponent);
            tc.addExtra(tcText);

            p.spigot().sendMessage(tc);
            p.sendRawMessage(RedFormatter.format(s2));
            p.sendRawMessage(RedFormatter.format(s3));

            return "";
        }
        return RedRealms.getLocalization().getString("messages.notifications.town-creation-after-invalid");
    }

    @Override
    protected Prompt acceptValidatedInput(ConversationContext context, String s) {
        Player mayor = (Player) context.getForWhom();
        PromptData.removeFromPromptMap(((Player) context.getForWhom()).getUniqueId());

        Map<String, Boolean> ruleSet = null;
        switch(biomeType) {
            case ("snowy") : {
                ruleSet = new HashMap<>(RedRealms.getDefaultConfig().getSnowyTownPresets()); break;
            }
            case ("cold") : {
                ruleSet = new HashMap<>(RedRealms.getDefaultConfig().getColdTownPresets()); break;
            }
            case ("temperate") : {
                ruleSet = new HashMap<>(RedRealms.getDefaultConfig().getTemperateTownPresets()); break;
            }
            case ("warm") : {
                ruleSet = new HashMap<>(RedRealms.getDefaultConfig().getWarmTownPresets()); break;
            }
        }

        RedTown newTown = new RedTown(s, (Player) context.getForWhom(), townBanner, newTownChunk, ruleSet);
        Bukkit.getServer().broadcastMessage(String.format(RedRealms.getLocalization().getString("messages.notifications.new-town-was-created"), mayor.getName(), s));
        return null;
    }

    @Override
    protected boolean isInputValid(ConversationContext context, String input) {
        // Max-min length check
        if (input.length() < RedRealms.getDefaultConfig().getNameMinLength()) {
            context.getForWhom().sendRawMessage(String.format(RedRealms.getLocalization().getString("messages.errors.this-name-too-short"), RedRealms.getDefaultConfig().getNameMinLength()));
            hasInvalidInput = true;
            return false;
        }
        if (input.length() > RedRealms.getDefaultConfig().getNameMaxLength()) {
            context.getForWhom().sendRawMessage(String.format(RedRealms.getLocalization().getString("messages.errors.this-name-too-long"), RedRealms.getDefaultConfig().getNameMaxLength()));
            hasInvalidInput = true;
            return false;
        }

        // Regex check
        Pattern pattern = Pattern.compile(Objects.requireNonNull(RedRealms.getDefaultConfig().getNameRegex()));
        if (!pattern.matcher(input).matches()) {
            context.getForWhom().sendRawMessage(RedRealms.getLocalization().getString("messages.errors.this-name-out-of-regex"));
            hasInvalidInput = true;
            return false;
        }

        // Name check
        RedTown town = RedData.createTown(input);
        if (town.readFile()) {
            context.getForWhom().sendRawMessage(RedRealms.getLocalization().getString("messages.errors.this-town-name-already-taken"));
            hasInvalidInput = true;
            return false;
        }
        return true;
    }
}
