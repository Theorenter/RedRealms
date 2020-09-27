package org.concordacraft.redrealms.utilits;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.concordacraft.redrealms.main.RedLog;

import java.util.ArrayList;
import java.util.List;

public final class TestItem {

    public static ItemStack create() {
        ItemStack item = new ItemStack(Material.TRIDENT);
        item = NBTEditor.set(item, "ConcordiaCraft", "spear", "shieldBreaker");

        // Meta
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();

        lore.add(ChatColor.RESET + "Средняя скорость атаки");
        lore.add(ChatColor.RESET + "");
        lore.add(ChatColor.RED + "⚔ Средний урон: 3");
        lore.add(ChatColor.RESET + "");
        lore.add(ChatColor.GOLD + "❤ Может пробивать щиты");

        meta.setDisplayName(ChatColor.RESET + "Деревянное копье");
        meta.setLore(lore);

        item.setItemMeta(meta);
        NBTEditor.NBTCompound compound = NBTEditor.getItemNBTTag(item);

        // To add one attribute
        compound.set( "generic.attackDamage", "tag", "AttributeModifiers", null, "AttributeName" );
        compound.set( "Attack Damage", "tag", "AttributeModifiers", 0, "Name" );
        compound.set( "mainhand", "tag", "AttributeModifiers", 0, "Slot" );
        compound.set( 0, "tag", "AttributeModifiers", 0, "Operation" );
        compound.set( 3.0, "tag", "AttributeModifiers", 0, "Amount" );
        compound.set( 99L, "tag", "AttributeModifiers", 0, "UUIDMost" );
        compound.set( 77530600L, "tag", "AttributeModifiers", 0, "UUIDLeast" );

        // To add another attribute
        compound.set( "generic.attackKnockback", "tag", "AttributeModifiers", null, "AttributeName" );
        compound.set( "Knockback", "tag", "AttributeModifiers", 1, "Name" );
        compound.set( "mainhand", "tag", "AttributeModifiers", 1, "Slot" );
        compound.set( 0, "tag", "AttributeModifiers", 1, "Operation" );
        compound.set( 10.0, "tag", "AttributeModifiers", 1, "Amount" );
        compound.set( 99L, "tag", "AttributeModifiers", 1, "UUIDMost" );
        compound.set( 77530600L, "tag", "AttributeModifiers", 1, "UUIDLeast" );

        // A third attribute
        compound.set( "generic.attackSpeed", "tag", "AttributeModifiers", null, "AttributeName" );
        compound.set( "Attack Speed", "tag", "AttributeModifiers", 2, "Name" );
        compound.set( "mainhand", "tag", "AttributeModifiers", 2, "Slot" );
        compound.set( 0, "tag", "AttributeModifiers", 2, "Operation" );
        compound.set( 3.0, "tag", "AttributeModifiers", 2, "Amount" );
        compound.set( 99L, "tag", "AttributeModifiers", 2, "UUIDMost" );
        compound.set( 77530600L, "tag", "AttributeModifiers", 2, "UUIDLeast" );

        item = NBTEditor.getItemFromTag(compound);

        RedLog.debug(item.getData().toString());
        return item;
    }
}
