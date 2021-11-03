package me.dkflab.dungeoncrawler.gui;

import me.dkflab.dungeoncrawler.DungeonCrawler;
import me.dkflab.dungeoncrawler.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;

import java.util.*;

import static me.dkflab.dungeoncrawler.Utils.color;

public class ShopScreen implements InventoryHolder {

    private final Inventory inv;

    private DungeonCrawler main;
    public ShopScreen(DungeonCrawler main) {
        this.main = main;
        inv = Bukkit.createInventory(this,45,"Shop");
        init();
    }

    private void init() {
        for (int i = 0; i < 45; i++) {
            inv.setItem(i, Utils.blankPane());
        }
        HashMap<Enchantment, Integer> enchants = new HashMap();
        enchants.put(Enchantment.LUCK, 1);
        List<String> lore = new ArrayList<>();
        lore.add(color("&7Upgrade your weapon's power."));
        lore.add(color("&r"));
        lore.add(color("&6Price: &a50 Emeralds"));
        inv.setItem(21, Utils.createItem(Material.DIAMOND_SWORD, 1, "&f&lUpgrade Weapons", lore, enchants, Collections.singletonList(ItemFlag.HIDE_ENCHANTS), false));
        lore.clear();
        lore.add(color("&7Upgrade your armor's protection."));
        lore.add(color("&r"));
        lore.add(color("&6Price: &a50 Emeralds"));
        inv.setItem(23, Utils.createItem(Material.DIAMOND_CHESTPLATE, 1, "&f&lUpgrade Armor", lore, enchants, Collections.singletonList(ItemFlag.HIDE_ENCHANTS), false));
    }

    public void listener(InventoryClickEvent e) {
        Material mat = e.getCurrentItem().getType();
        Player p = (Player)e.getWhoClicked();
        UUID u = p.getUniqueId();
        if (mat.equals(Material.DIAMOND_SWORD)) {
            if (main.currencyManager.purchase(u, 50)) {
                main.upgradeManager.addWeaponLevel(u,1);
                p.closeInventory();
                p.sendMessage(color("&a&lPurchase success!"));
            } else {
                p.sendMessage(color("&c&l[!] &7Insufficient funds!"));
            }
        }
        if (mat.equals(Material.DIAMOND_CHESTPLATE)) {
            if (main.currencyManager.purchase(u, 50)) {
                main.upgradeManager.addArmorLevel(u,1);
                p.closeInventory();
                p.sendMessage(color("&a&lPurchase success!"));
            } else {
                p.sendMessage(color("&c&l[!] &7Insufficient funds!"));
            }
        }
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }
}
