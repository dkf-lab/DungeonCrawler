package me.dkflab.dungeoncrawler.objects;

import me.dkflab.dungeoncrawler.Utils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShopObject {

    private ConfigurationSection sec;
    public ShopObject(ConfigurationSection sec) {
        this.sec = sec;
        for (String key : sec.getKeys(false)) {
            // Item IDs
            ConfigurationSection ns = sec.getConfigurationSection(key);
            // Get information from config about item
            if (ns != null) {
                ItemStack item = ns.getItemStack("item");
                int slot = ns.getInt("slot");
                int price = ns.getInt("price");
                addItem(item,slot,price);
            }
        }
    }

    // yes, this isn't ideal, but it works
    private HashMap<ItemStack, Integer> itemsAndPrice = new HashMap<>();
    private HashMap<ItemStack, Integer> itemsAndSlot = new HashMap<>();

    public String getID() {
        return this.sec.getName();
    }

    public List<ItemStack> getListOfItems() {
        return new ArrayList<>(itemsAndPrice.keySet());
    }

    public int getItemSlot(ItemStack item) {
        return itemsAndSlot.get(item);
    }

    public int getItemPrice(ItemStack item) {
        return itemsAndPrice.get(item);
    }

    public void addItem(ItemStack item, int slot, int price) {
        itemsAndPrice.put(item,price);
        itemsAndSlot.put(item,slot);
    }

    public Inventory getInventory() {
        Inventory inv = Bukkit.createInventory(null, 45, "Shop");
        for (int i = 0; i < 45; i++) {
            inv.setItem(i, Utils.blankPane());
        }
        for (ItemStack item : getListOfItems()) {
            inv.setItem(getItemSlot(item),Utils.priceLore(item,getItemPrice(item)));
        }
        return inv;
    }
}
