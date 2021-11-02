package me.dkflab.dungeoncrawler.objects;

import me.dkflab.dungeoncrawler.Utils;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Ability {

    String name;
    Material item;
    ItemStack finalItem;
    PotionEffectType potionEffect;
    List<String> description = new ArrayList<>();
    List<String> lore = new ArrayList<>();
    int cooldown,potDur,healRadius,healAmount;

    public Ability(final ConfigurationSection s) {
        this.name = Utils.color(s.getString("name"));
        this.item = Material.getMaterial(s.getString("material").toUpperCase());
        this.description = s.getStringList("description");
        for (String str : description) {
            this.lore.add(Utils.color("&7"+str));
        }
        this.cooldown = s.getInt("cooldown");
        // potion
        if (s.getString("potionType") != null) {
            this.potionEffect = PotionEffectType.getByName(s.getString("potionType"));
        }
        this.potDur = s.getInt("potionTime");
        // healing
        this.healRadius = s.getInt("healRadius");
        this.healAmount = s.getInt("healAmount");
    }

    public int getCooldown() {
        return this.cooldown;
    }

    public String getName() {
        return this.name;
    }

    public ItemStack getItem() {
        List<ItemFlag> flags = new ArrayList<>();
        HashMap<Enchantment, Integer> enchants = new HashMap<>();
        flags.add(ItemFlag.HIDE_UNBREAKABLE);
        flags.add(ItemFlag.HIDE_ENCHANTS);
        enchants.put(Enchantment.LUCK, 1);
        finalItem = Utils.createItem(item, 1,name,lore,enchants,flags,false);

        return this.finalItem;
    }

    public PotionEffect getPotionEffect() {
        if (this.potionEffect == null) {
            return null;
        }
        return new PotionEffect(potionEffect,potDur,1,false,false,false);
    }

    public int getHealRadius() {
        return this.healRadius;
    }

    public int getHealAmount() {
        return this.healAmount;
    }
}
