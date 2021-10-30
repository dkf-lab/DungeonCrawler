package me.dkflab.dungeoncrawler.gui;

import me.dkflab.dungeoncrawler.DungeonCrawler;
import me.dkflab.dungeoncrawler.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;

import java.util.Collections;
import java.util.HashMap;

public class UpgradeScreen implements InventoryHolder {

    private final Inventory inv;

    private DungeonCrawler main;
    public UpgradeScreen(DungeonCrawler m) {
        main = m;
        inv = Bukkit.createInventory(this,54,"Upgrade Items");
        init();
    }

    private void init() {
        for (int i = 0; i < 54; i++) {
            inv.setItem(i, Utils.blankPane());
        }
        HashMap<Enchantment, Integer> enchants = new HashMap();
        enchants.put(Enchantment.LUCK, 1);
        inv.setItem(21, Utils.createItem(Material.DIAMOND_SWORD, 1, "&aInput", Collections.singletonList("&7Input item"), null, Collections.singletonList(ItemFlag.HIDE_ENCHANTS), false));
        inv.setItem(30, Utils.createItem(Material.ENCHANTED_BOOK,1, "&aInput Enchantment Book", Collections.singletonList("&7Example book"), enchants, Collections.singletonList(ItemFlag.HIDE_ENCHANTS),false));
        inv.setItem(23, Utils.createItem(Material.DIAMOND_SWORD, 1, "&cOutput", Collections.singletonList("&7Output item"), enchants, Collections.singletonList(ItemFlag.HIDE_ENCHANTS), false));
    }

    public void listener(InventoryClickEvent e) {
        Material mat = e.getCurrentItem().getType();
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }

}
