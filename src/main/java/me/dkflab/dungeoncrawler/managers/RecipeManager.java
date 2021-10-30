package me.dkflab.dungeoncrawler.managers;

import me.dkflab.dungeoncrawler.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.Collections;

public class RecipeManager {

    public static void init() {
        swordRecipe();
    }

    public static void swordRecipe() {
        ItemStack item = Utils.createItem(Material.IRON_SWORD, 1, "&f&lIron Sword",Collections.singletonList("&7Affordable yet strong."),null, Collections.singletonList(ItemFlag.HIDE_UNBREAKABLE),true);

        ShapedRecipe sr = new ShapedRecipe(NamespacedKey.minecraft("sword"), item);
        sr.shape("  i", "sis", "t  ");
        sr.setIngredient('i',Material.IRON_INGOT);
        sr.setIngredient('s', Material.STRING);
        sr.setIngredient('t',Material.STICK);
        Bukkit.getServer().addRecipe(sr);
    }

}
