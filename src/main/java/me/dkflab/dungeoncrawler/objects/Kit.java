package me.dkflab.dungeoncrawler.objects;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.List;

public class Kit {

    private String name;
    private List<ItemStack> items;
    private List<ItemStack> armor;
    private ItemStack abilityItem;

    public Kit(String name, List<ItemStack> items, List<ItemStack> armor, ItemStack abilityItem) {
        this.name = name;
        this.items = items;
        this.armor = armor;
        this.abilityItem = abilityItem;
    }

    public String getName() {
        return this.name;
    }

    public List<ItemStack> getItems() {
        return this.items;
    }

    public List<ItemStack> getArmor() {
        return this.armor;
    }

    public ItemStack getAbilityItem() {
        return this.abilityItem;
    }

    public void giveToPlayer(Player p) {
        PlayerInventory inv = p.getInventory();
        for (ItemStack item : this.items) {
            inv.addItem(item);
        }
        inv.addItem(this.abilityItem);
        inv.setHelmet(getHelmet());
        inv.setChestplate(getChestplate());
        inv.setLeggings(getLeggings());
        inv.setBoots(getBoots());
    }

    public ItemStack getHelmet() {
        for (ItemStack item : armor) {
            if (item.getType().toString().contains("HELMET")) {
                return item;
            }
        }
        return null;
    }

    public ItemStack getChestplate() {
        for (ItemStack item : armor) {
            if (item.getType().toString().contains("CHESTPLATE")) {
                return item;
            }
        }
        return null;
    }

    public ItemStack getLeggings() {
        for (ItemStack item : armor) {
            if (item.getType().toString().contains("LEGGINGS")) {
                return item;
            }
        }
        return null;
    }

    public ItemStack getBoots() {
        for (ItemStack item : armor) {
            if (item.getType().toString().contains("BOOT")) {
                return item;
            }
        }
        return null;
    }
}
