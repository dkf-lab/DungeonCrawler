package me.dkflab.dungeoncrawler.gui;

import me.dkflab.dungeoncrawler.DungeonCrawler;
import me.dkflab.dungeoncrawler.Utils;
import me.dkflab.dungeoncrawler.objects.Dungeon;
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

        for (Dungeon d : main.dungeonManager.getActiveDungeons()) {
            inv.setItem(d.getSlot(), Utils.createItem(d.getMaterial(), 1, d.getFancyName(), Collections.singletonList("&7Enter the dungeon!"),enchants,Collections.singletonList(ItemFlag.HIDE_ENCHANTS), false));
        }
    }

    public void listener(InventoryClickEvent e) {
        if (e.getCurrentItem() == null) {
            return;
        }
        String s = e.getCurrentItem().getItemMeta().getDisplayName();
        for (Dungeon d : main.dungeonManager.getActiveDungeons()) {
            if (s.equalsIgnoreCase(d.getFancyName())) {
                main.getMM().addPlayerToDungeon((Player)e.getWhoClicked(), d);
            }
        }
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }
}
