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

import java.util.Collections;
import java.util.HashMap;

public class DungeonSelect implements InventoryHolder {

    private final Inventory inv;

    private DungeonCrawler main;
    public DungeonSelect(DungeonCrawler m) {
        main = m;
        inv = Bukkit.createInventory(this,45,"Enter the dungeon!");
        init();
    }

    private void init() {
        for (int i = 0; i < 45; i++) {
            inv.setItem(i, Utils.blankPane());
        }
        HashMap<Enchantment, Integer> enchants = new HashMap();
        enchants.put(Enchantment.LUCK, 1);
        inv.setItem(21, Utils.createItem(Material.STRING, 1, "&dDungeon One", Collections.singletonList("&7Enter the first dungeon."), enchants, Collections.singletonList(ItemFlag.HIDE_ENCHANTS), false));
        inv.setItem(22, Utils.createItem(Material.BONE, 2, "&dDungeon Two", Collections.singletonList("&7Enter the second dungeon."), enchants, Collections.singletonList(ItemFlag.HIDE_ENCHANTS), false));
        inv.setItem(23, Utils.createItem(Material.SPIDER_EYE, 3, "&dDungeon Three", Collections.singletonList("&7Enter the third dungeon."), enchants, Collections.singletonList(ItemFlag.HIDE_ENCHANTS), false));
    }

    public void listener(InventoryClickEvent e) {
        Material mat = e.getCurrentItem().getType();
        if (mat.equals(Material.STRING)) {
            main.getMM().addPlayerToDungeon((Player) e.getWhoClicked(),main.dungeonManager.one);
        }
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }
}
