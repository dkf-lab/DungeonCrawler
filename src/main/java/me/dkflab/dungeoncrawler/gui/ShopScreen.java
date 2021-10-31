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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

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
        lore.add(Utils.color("&7A sharper sword."));
        lore.add(Utils.color("&r"));
        lore.add(Utils.color("&6Price: &a5x Emerald"));
        inv.setItem(22, Utils.createItem(Material.DIAMOND_SWORD, 1, "&b&lDiamond Sword", lore, enchants, Collections.singletonList(ItemFlag.HIDE_ENCHANTS), false));
    }

    public void listener(InventoryClickEvent e) {
        Material mat = e.getCurrentItem().getType();
        Player p = (Player)e.getWhoClicked();
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }
}
