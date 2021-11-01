package me.dkflab.dungeoncrawler.objects;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.List;

public class Kit {

    private String name;
    private List<ItemStack> armor;
    private ItemStack abilityItem, pickaxe, weapon;

    public Kit(String name, ItemStack weapon, List<ItemStack> armor, ItemStack abilityItem, ItemStack pickaxe) {
        this.name = name;
        this.weapon = weapon;
        this.armor = armor;
        this.abilityItem = abilityItem;
        this.pickaxe = pickaxe;
    }

    public String getName() {
        return this.name;
    }

    public ItemStack getWeapon() {
        return this.weapon;
    }

    public List<ItemStack> getArmor() {
        return this.armor;
    }

    public ItemStack getAbilityItem() {
        return this.abilityItem;
    }

    public ItemStack getPickaxe() {
        return this.pickaxe;
    }

    public void giveToPlayer(Player p) {
        PlayerInventory inv = p.getInventory();
        inv.addItem(this.weapon);
        inv.addItem(this.abilityItem);
        inv.addItem(this.pickaxe);
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
