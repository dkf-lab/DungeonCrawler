package me.dkflab.dungeoncrawler.gui.shops;

import me.dkflab.dungeoncrawler.DungeonCrawler;
import me.dkflab.dungeoncrawler.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.List;

import static me.dkflab.dungeoncrawler.Utils.*;

public class PotionScreen implements InventoryHolder {

    private final Inventory inv;

    private DungeonCrawler main;
    public PotionScreen(DungeonCrawler main) {
        this.main = main;
        inv = Bukkit.createInventory(this,45,"Potions");
        init();
    }

    private void init() {
        for (int i = 0; i < 45; i++) {
            inv.setItem(i, blankPane());
        }
        inv.setItem(13, createItem(PotionType.JUMP, "&dJump Boost"));
        inv.setItem(21, createItem(PotionType.REGEN, "&dRegeneration"));
        inv.setItem(22, createItem(PotionType.NIGHT_VISION, "&dNight Vision"));
        inv.setItem(23, createItem(PotionType.STRENGTH, "&dStrength"));
        inv.setItem(31, createItem(PotionType.SPEED, "&dSpeed"));
    }

    private ItemStack createItem(PotionType type, String name) {
        List<String> lore = new ArrayList<>();
        int price = 5;
        lore.add("&7Price: &a" + price + " Emeralds");
        ItemStack item = createPotionItem(type, 1, true, false);
        item = rename(item,color(name));
        return addLore(item, lore);
    }

    public void listener(InventoryClickEvent e) {
        if (e.getCurrentItem() == null) {
            return;
        }
        if (e.getCurrentItem().getType() == Material.POTION) {
            PotionMeta meta = (PotionMeta) e.getCurrentItem().getItemMeta();
            if (main.currencyManager.purchase(e.getWhoClicked().getUniqueId(), 5)) {
                e.getWhoClicked().getInventory().addItem(createPotionItem(meta.getBasePotionData().getType(),1,meta.getBasePotionData().isExtended(),meta.getBasePotionData().isUpgraded()));
                e.getWhoClicked().sendMessage(color("&a&lSuccess! &7Purchased item."));
            } else {
                e.getWhoClicked().sendMessage(color("&c&l[!] &7Insufficient funds."));
            }
        }
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }
}
