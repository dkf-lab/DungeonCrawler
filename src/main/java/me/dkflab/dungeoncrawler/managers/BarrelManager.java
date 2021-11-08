package me.dkflab.dungeoncrawler.managers;

import me.dkflab.dungeoncrawler.DungeonCrawler;
import me.dkflab.dungeoncrawler.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Barrel;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

public class BarrelManager {

    private DungeonCrawler main;
    public BarrelManager(DungeonCrawler main) {
        this.main = main;
        saveDefaultConfig();
        scanConfig();
    }

    List<Barrel> generatedBarrels = new ArrayList<>();
    HashMap<ItemStack, Integer> items = new HashMap<>();

    private void scanConfig() {
        ConfigurationSection per = getConfig().getConfigurationSection("percentages");
        if (per == null) {
            Bukkit.getLogger().severe("Cannot find 'percentages' list in loot.yml");
            return;
        }
        for (String s : getConfig().getKeys(false)) {
            if (!s.equals("percentages")) {
                items.put(getConfig().getItemStack(s), per.getInt(s));
            }
        }
    }

    public void generateLootForBarrel(Barrel barrel) {
        if (generatedBarrels.contains(barrel)) {
            return;
        }
        generatedBarrels.add(barrel);
        for (ItemStack item : items.keySet()) {
            if (Utils.randomNumber(0,100) <= items.get(item)) {
                barrel.getInventory().addItem(item);
            }
        }
    }

    public void resetMap() {
        generatedBarrels.clear();
    }

    public ItemStack getItemFromConfig(String s) {
        return getConfig().getItemStack(s);
    }

    public void addItemToConfig(String name, @NotNull ItemStack item) {
        getConfig().set(name, item);
        saveConfig();
    }

    private FileConfiguration dataConfig = null;
    private File configFile = null;

    public void reloadConfig() {
        // creates config, checks for yml issues
        if (this.configFile == null) {
            configFile = new File(this.main.getDataFolder(), "loot.yml");
        }
        dataConfig = YamlConfiguration.loadConfiguration(configFile);
        InputStream defaultStream = this.main.getResource("loot.yml");
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
            configFile = new File(this.main.getDataFolder(), "loot.yml");
        }
        if (!configFile.exists()) {
            main.saveResource("loot.yml", false);
        }
    }
}
