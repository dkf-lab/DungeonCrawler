package me.dkflab.dungeoncrawler.managers;

import me.dkflab.dungeoncrawler.DungeonCrawler;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;
import java.util.logging.Level;

public class CurrencyManager {

    private DungeonCrawler main;
    public CurrencyManager (DungeonCrawler main) {
        this.main = main;
        saveDefaultConfig();
    }

    public void addEmeralds(UUID uuid, int amount) {
        amount += getConfig().getInt(uuid.toString());
        setEmeralds(uuid, amount);
    }

    public void setEmeralds(UUID uuid, int amount) {
        getConfig().set(uuid.toString(),amount);
        saveConfig();
    }

    public boolean purchase(UUID uuid, int amount) {
        if (getEmeralds(uuid) - amount < 0) {
            return false;
        }
        setEmeralds(uuid, getEmeralds(uuid)-amount);
        return true;
    }

    public int getEmeralds(UUID uuid) {
        return getConfig().getInt(uuid.toString());
    }

    // Data Management
    private FileConfiguration dataConfig = null;
    private File configFile = null;
    public void reloadConfig() {
        // creates config, checks for yml issues
        if (this.configFile == null) {
            configFile = new File(this.main.getDataFolder(), "emeralds.yml");
        }
        dataConfig = YamlConfiguration.loadConfiguration(configFile);
        InputStream defaultStream = this.main.getResource("emeralds.yml");
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
            configFile = new File(this.main.getDataFolder(), "emeralds.yml");
        }
        if (!configFile.exists()) {
            main.saveResource("emeralds.yml", false);
        }
    }
}
