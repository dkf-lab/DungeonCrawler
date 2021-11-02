package me.dkflab.dungeoncrawler.objects;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static me.dkflab.dungeoncrawler.Utils.createItem;

public class Kit {

    private String name;
    private List<ItemStack> armor = new ArrayList<>();
    private ItemStack pickaxe, weapon;
    private Ability ability;

    public Kit(String name, ItemStack weapon, List<ItemStack> armor, Ability ability) {
        this.name = name;
        for (ItemStack i : armor) {
            if (i != null) {
                this.armor.add(addFlags(i));
            }
        }
        this.armor = armor;
        this.ability = ability;
        this.weapon = addFlags(weapon);

        List<String> lore = new ArrayList<>();
        lore.add("&7A portable drill,");
        lore.add("&7made in China.");

        HashMap<Enchantment,Integer> enchants = new HashMap<>();
        enchants.put(Enchantment.DIG_SPEED, 2);
        List<ItemFlag> flags = new ArrayList<>();
        flags.add(ItemFlag.HIDE_UNBREAKABLE);
        flags.add(ItemFlag.HIDE_ENCHANTS);
        this.pickaxe = createItem(Material.GOLDEN_PICKAXE,1,"&9&lDrill", lore,enchants,flags,true);
    }

    private ItemStack addFlags(ItemStack i) {
        if (i == null) {
            return null;
        }
        if (i.getType() == Material.AIR) {
            return i;
        }
        i.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_DYE, ItemFlag.HIDE_UNBREAKABLE);
        return i;
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

    public Ability getAbility() {
        return this.ability;
    }

    public ItemStack getPickaxe() {
        return this.pickaxe;
    }

    public void giveToPlayer(Player p) {
        PlayerInventory inv = p.getInventory();
        inv.addItem(this.weapon);
        inv.addItem(this.ability.getItem());
        inv.addItem(this.pickaxe);
        inv.setHelmet(getHelmet());
        inv.setChestplate(getChestplate());
        inv.setLeggings(getLeggings());
        inv.setBoots(getBoots());
    }

    public ItemStack getHelmet() {
        for (ItemStack item : armor) {
            if (item != null) {
                if (item.getType().toString().contains("HELMET")) {
                    return item;
                }
            }
        }
        return null;
    }

    public ItemStack getChestplate() {
        for (ItemStack item : armor) {
            if (item != null) {
                if (item.getType().toString().contains("CHESTPLATE")) {
                    return item;
                }
            }

        }
        return null;
    }

    public ItemStack getLeggings() {
        for (ItemStack item : armor) {
            if (item != null) {
                if (item.getType().toString().contains("LEGGINGS")) {
                    return item;
                }
            }
        }
        return null;
    }

    public ItemStack getBoots() {
        for (ItemStack item : armor) {
            if (item != null) {
                if (item.getType().toString().contains("BOOT")) {
                    return item;
                }
            }
        }
        return null;
    }
}
