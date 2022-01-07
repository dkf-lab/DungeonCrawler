package me.dkflab.dungeoncrawler.objects;

import org.bukkit.inventory.ItemStack;

public class ShopMenuObject {

    private String shop;
    private int slot;
    private ItemStack item;

    public ShopMenuObject(ItemStack item, String shop, int slot) {
        this.item = item;
        this.slot = slot;
        this.shop = shop;
    }

    public String getShop() {
        return this.shop;
    }

    public ItemStack getItem() {
        return item;
    }

    public int getSlot() {
        return slot;
    }
}
