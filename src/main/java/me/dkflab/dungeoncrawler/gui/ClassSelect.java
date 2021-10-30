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
        inv.setItem(21, Utils.createItem(Material.BLAZE_ROD, 1, "&6&lHealer", Collections.singletonList("&7Select the 'Healer' class."), enchants, Collections.singletonList(ItemFlag.HIDE_ENCHANTS), false));
        inv.setItem(23, Utils.createItem(Material.STONE_SWORD, 1, "&lTank", Collections.singletonList("&7Select the 'Tank' class."), enchants, Collections.singletonList(ItemFlag.HIDE_ENCHANTS), false));
    }

    public void listener(InventoryClickEvent e) {
        Material mat = e.getCurrentItem().getType();
        Player p = (Player)e.getWhoClicked();
        if (mat.equals(Material.BLAZE_ROD)) {
            main.getMM().setClassOfPlayer(p, main.kits.healer);
            p.closeInventory();
        }
        if (mat.equals(Material.STONE_SWORD)) {
            main.getMM().setClassOfPlayer(p, main.kits.tank);
            p.closeInventory();
        }
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }
}
