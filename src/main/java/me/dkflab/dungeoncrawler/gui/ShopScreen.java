package me.dkflab.dungeoncrawler.gui;

import me.dkflab.dungeoncrawler.DungeonCrawler;
import me.dkflab.dungeoncrawler.Utils;
import me.dkflab.dungeoncrawler.objects.ShopMenuObject;
import me.dkflab.dungeoncrawler.objects.ShopObject;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ShopScreen implements InventoryHolder {

    private final Inventory inv;

    private DungeonCrawler main;
    public ShopScreen(DungeonCrawler m) {
        main = m;
        inv = Bukkit.createInventory(this,45,Utils.color("&f&lShop"));
        init();
    }

    List<ShopMenuObject> menuObjectList;

    private void init() {
        for (int i = 0; i < 45; i++) {
            inv.setItem(i, Utils.blankPane());
        }
        HashMap<Enchantment, Integer> enchants = new HashMap();
        enchants.put(Enchantment.LUCK, 1);
        menuObjectList = main.getShopManager().getMenuObjects();
        for (ShopMenuObject s : menuObjectList) {
            inv.setItem(s.getSlot(),s.getItem());
        }
    }

    public void listener(InventoryClickEvent e) {
        ItemStack clickedItem = e.getCurrentItem();
        for (ShopMenuObject s : menuObjectList) {
            if (s.getItem().isSimilar(clickedItem)) {
                main.getShopManager().openShop(s.getShop(), (Player) e.getWhoClicked());
            }
        }
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }

}
