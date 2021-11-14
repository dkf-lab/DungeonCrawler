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

public class ShopScreen implements InventoryHolder {

    private final Inventory inv;

    private DungeonCrawler main;
    public ShopScreen(DungeonCrawler m) {
        main = m;
        inv = Bukkit.createInventory(this,45,"Shop for Gear");
        init();
    }

    private void init() {
        for (int i = 0; i < 45; i++) {
            inv.setItem(i, Utils.blankPane());
        }
        HashMap<Enchantment, Integer> enchants = new HashMap();
        enchants.put(Enchantment.LUCK, 1);
        inv.setItem(22, Utils.createItem(Material.GLASS_BOTTLE, 1, "&dPotions", Collections.singletonList("&7Open the potion store."), enchants, Collections.singletonList(ItemFlag.HIDE_ENCHANTS), false));
    }

    public void listener(InventoryClickEvent e) {
        Material mat = e.getCurrentItem().getType();
        if (mat.equals(Material.GLASS_BOTTLE)) {
            e.getWhoClicked().openInventory(main.getGUI().potionScreen.getInventory());
        }
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }

}
