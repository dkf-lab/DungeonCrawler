package me.dkflab.dungeoncrawler.gui;

import me.dkflab.dungeoncrawler.DungeonCrawler;
import me.dkflab.dungeoncrawler.Utils;
import me.dkflab.dungeoncrawler.objects.Dungeon;
import me.dkflab.dungeoncrawler.objects.Kit;
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

public class ClassSelect implements InventoryHolder {

    private final Inventory inv;

    private DungeonCrawler main;
    public ClassSelect(DungeonCrawler main) {
        this.main = main;
        inv = Bukkit.createInventory(this,45,"Enter the dungeon!");
        init();
    }

    private void init() {
        for (int i = 0; i < 45; i++) {
            inv.setItem(i, Utils.blankPane());
        }
        HashMap<Enchantment, Integer> enchants = new HashMap();
        enchants.put(Enchantment.LUCK, 1);
        for (Kit c: main.classManager.getLoadedKits()) {
            inv.setItem(c.getSlot(), Utils.createItem(c.getIconMaterial(), 1, c.getFancyName(), Collections.singletonList("&7Select the '" + c.getName() + "&7' class."), enchants, Collections.singletonList(ItemFlag.HIDE_ENCHANTS), false));
        }
    }

    public void listener(InventoryClickEvent e) {
        Player p = (Player)e.getWhoClicked();
        String s = e.getCurrentItem().getItemMeta().getDisplayName();

        for (Kit c : main.classManager.getLoadedKits()) {
            if (c.getFancyName().equalsIgnoreCase(s)) {
                main.getMM().setClassOfPlayer(p,c);
                p.closeInventory();
                return;
            }
        }
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }
}
