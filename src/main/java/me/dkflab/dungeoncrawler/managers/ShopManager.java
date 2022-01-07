package me.dkflab.dungeoncrawler.managers;

import me.dkflab.dungeoncrawler.DungeonCrawler;
import me.dkflab.dungeoncrawler.objects.ShopMenuObject;
import me.dkflab.dungeoncrawler.objects.ShopObject;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static me.dkflab.dungeoncrawler.Utils.color;

public class ShopManager {

    private DungeonCrawler main;
    public ShopManager(DungeonCrawler main) {
        this.main = main;
        saveDefaultConfig();
        scan();
    }

    List<ShopObject> shops = new ArrayList<>();
    List<ShopMenuObject> menuObjects = new ArrayList<>();

    public void listener(InventoryClickEvent e) {
        e.setCancelled(true);
        Player p = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();
        item.setLore(null);
        for (ShopObject shop : shops) {
            for (ItemStack i : shop.getListOfItems()) {
                i.setLore(null);
                if (i.isSimilar(item)) {
                    if (main.currencyManager.purchase(p.getUniqueId(),shop.getItemPrice(item))) {
                        p.closeInventory();
                        p.getInventory().addItem(item);
                        p.sendMessage(color("&a&lPurchase success!"));
                    } else {
                        p.sendMessage(color("&c&l[!] &7Insufficient funds!"));
                    }
                }
            }
        }
    }

    public List<ShopObject> getShops() {
        return shops;
    }

    public List<ShopMenuObject> getMenuObjects() {
        return this.menuObjects;
    }

    public void openShop(String shopID, Player player) {
        for (ShopObject so : shops) {
            if (so.getID().equalsIgnoreCase(shopID)) {
                player.openInventory(so.getInventory());
            }
        }
    }

    private void scan() {
        // Main Menu
        ConfigurationSection main = getConfig().getConfigurationSection("main");
        if (main != null) {
            for (String id : main.getKeys(false)) {
                ConfigurationSection s = main.getConfigurationSection(id);
                int slot = s.getInt("slot");
                String shop = s.getString("shop");
                ItemStack item = s.getItemStack("item");
                if (item != null && shop != null) {
                    menuObjects.add(new ShopMenuObject(item,shop,slot));
                }
            }
        }
        // Shops
        for (String key : getConfig().getKeys(false)) {
            if (key != "main") {
                shops.add(new ShopObject(getConfig().getConfigurationSection(key)));
            }
        }
    }

    public void addItemToMenu(ItemStack item, String id, int slot, String shop) {
        getConfig().set("main." + id + ".slot", slot);
        getConfig().set("main." + id + ".shop", shop);
        getConfig().set("main." + id + ".item", item);
        saveConfig();
    }

    public void addItemToShop(ItemStack item, String id, String store, int slot, int price) {
        getConfig().set(store+"."+id+".item",item);
        getConfig().set(store+"."+id+".slot",slot);
        getConfig().set(store+"."+id+".price",price);
        saveConfig();
    }

    // Data Management
    private FileConfiguration dataConfig = null;
    private File configFile = null;
    public void reloadConfig() {
        // creates config, checks for yml issues
        if (this.configFile == null) {
            configFile = new File(this.main.getDataFolder(), "shops.yml");
        }
        dataConfig = YamlConfiguration.loadConfiguration(configFile);
        InputStream defaultStream = this.main.getResource("shops.yml");
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            this.dataConfig.setDefaults(defaultConfig);
        }
    }

    public FileConfiguration getConfig() {
        if (dataConfig == null) {
            reloadConfig();
        }
        return dataConfig;
    }

    public void saveConfig() {
        // save config after changing data
        if (dataConfig == null||configFile==null) {
            return;
        }
        try {
            getConfig().save(this.configFile);
        } catch (IOException e) {
            main.getLogger().log(Level.SEVERE, "Could not save config to " + this.configFile, e);
        }
    }

    public void saveDefaultConfig() {
        if (this.configFile == null) {
            configFile = new File(this.main.getDataFolder(), "shops.yml");
        }
        if (!configFile.exists()) {
            main.saveResource("shops.yml", false);
        }
    }
    
}
