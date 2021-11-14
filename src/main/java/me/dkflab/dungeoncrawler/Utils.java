package me.dkflab.dungeoncrawler;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Utils {

    public static void noPerms(CommandSender s) {
        s.sendMessage(color("&c[!] &7Insufficient permissions."));
    }

    public static ItemStack rename(ItemStack item, String name) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(color(name));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack addLore(ItemStack item, List<String> lore) {
        ItemMeta meta = item.getItemMeta();
        List<String> l = new ArrayList<>();
        for (String s:lore) {
            l.add(color(s));
        }
        meta.setLore(l);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createPotionItem(PotionType type,int level, boolean extend, boolean upgraded) {
        ItemStack potion = new ItemStack(Material.POTION, 1);
        PotionMeta meta = (PotionMeta) potion.getItemMeta();
        meta.setBasePotionData(new PotionData(type,extend,upgraded));
        potion.setItemMeta(meta);
        return potion;
    }

    public static ItemStack createItem(Material material, int amount, String name, List<String>lore, HashMap<Enchantment, Integer> enchants, List<ItemFlag> flags, Boolean unbreakable) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(color(name));
        if (lore != null) {
            List<String> l = new ArrayList<>();
            for (String s:lore) {
                l.add(color(s));
            }
            meta.setLore(l);
        }
        if (enchants != null) {
            for (Enchantment e:enchants.keySet()) {
                meta.addEnchant(e, enchants.get(e), true);
            }
        }
        if (flags != null) {
            for (ItemFlag f : flags) {
                meta.addItemFlags(f);
            }
        }
        meta.setUnbreakable(unbreakable);

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack setArmorColor(ItemStack armor, Color color) {
        ItemStack temp = armor;
        LeatherArmorMeta meta = (LeatherArmorMeta) temp.getItemMeta();
        meta.setColor(color);
        temp.setItemMeta(meta);
        return temp;
    }

    public static String color(String s) {
        return (ChatColor.translateAlternateColorCodes('&',s));
    }

    public static void notPlayer(CommandSender s) {
        s.sendMessage(color("&cYou need to be a player to run that command!"));
    }

    public static ArrayList<Player> getNearbyPlayers(Player p, double range) {
        ArrayList<Player> nearby = new ArrayList<>();
        for (Entity e : p.getNearbyEntities(range,range,range)) {
            if (e.getType().equals(EntityType.PLAYER)) {
                nearby.add((Player)e);
            }
        }
        return nearby;
    }

    public static ItemStack blankPane() {
        return createItem(Material.GRAY_STAINED_GLASS_PANE,1,"&r",null,null,null,false);
    }

    public static int randomNumber(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
