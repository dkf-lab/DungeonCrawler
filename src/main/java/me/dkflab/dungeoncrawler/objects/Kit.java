package me.dkflab.dungeoncrawler.objects;

import me.dkflab.dungeoncrawler.Utils;
import me.dkflab.dungeoncrawler.managers.UpgradeManager;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static me.dkflab.dungeoncrawler.Utils.createItem;

public class Kit {

    private String name,fancyName;
    private List<ItemStack> armor = new ArrayList<>();
    private ItemStack pickaxe, weapon;
    private Ability ability;
    Material icon;
    int slot;

    public Kit(String name, String fancyName, ItemStack weapon, List<ItemStack> armor, Ability ability, Material icon, int slot) {
        this.name = name;
        this.fancyName = fancyName;
        this.slot = slot;
        for (ItemStack i : armor) {
            if (i != null) {
                this.armor.add(addFlags(i));
            }
        }
        this.armor = armor;
        this.ability = ability;
        this.weapon = addFlags(weapon);
        this.icon = icon;

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

    public Material getIconMaterial() {
        return this.icon;
    }

    public int getSlot() {
        return this.slot;
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

    public String getFancyName() {
        return Utils.color(this.fancyName);
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

    public void giveToPlayer(Player p, UpgradeManager m) {
        PlayerInventory inv = p.getInventory();
        ItemStack w = this.weapon;
        ItemMeta meta = w.getItemMeta();
        meta.addEnchant(Enchantment.DAMAGE_ALL, meta.getEnchantLevel(Enchantment.DAMAGE_ALL)+m.getArmorLevel(p.getUniqueId()), true);
        w.setItemMeta(meta);
        inv.addItem(w);
        inv.addItem(this.ability.getItem());
        inv.addItem(this.pickaxe);

        inv.setHelmet(addArmorLevels(getHelmet(),m,p));
        inv.setChestplate(addArmorLevels(getChestplate(),m,p));
        inv.setLeggings(addArmorLevels(getLeggings(),m,p));
        inv.setBoots(addArmorLevels(getBoots(),m,p));
    }

    private ItemStack addArmorLevels(ItemStack i, UpgradeManager m, Player p) {
        if (i == null) {
            return null;
        }
        ItemMeta meta = i.getItemMeta();
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, meta.getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL)+m.getArmorLevel(p.getUniqueId()),true);
        i.setItemMeta(meta);
        return i;
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
