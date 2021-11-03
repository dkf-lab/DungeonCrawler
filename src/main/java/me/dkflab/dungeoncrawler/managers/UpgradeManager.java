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

public class UpgradeManager {

    private DungeonCrawler main;
    public UpgradeManager(DungeonCrawler main) {
        this.main = main;
        saveDefaultConfig();
    }

    public void addWeaponLevel(UUID uuid, int amount) {
        amount += getConfig().getInt("weapon." + uuid.toString());
        setWeaponLevel(uuid, amount);
    }

    public void addArmorLevel(UUID uuid, int amount) {
        amount += getConfig().getInt("armor." + uuid.toString());
        setArmorLevel(uuid, amount);
    }

    public void setArmorLevel(UUID uuid, int amount) {
        getConfig().set("armor." + uuid.toString(),amount);
        saveConfig();
    }

    public void setWeaponLevel(UUID uuid, int amount) {
        getConfig().set("weapon." + uuid.toString(),amount);
        saveConfig();
    }

    public int getArmorLevel(UUID uuid) {
        return getConfig().getInt("armor." + uuid.toString());
    }

    public int getWeaponLevel(UUID uuid) {
        return getConfig().getInt("weapon." + uuid.toString());
    }


    private FileConfiguration dataConfig = null;
    private File configFile = null;
    public void reloadConfig() {
        // creates config, checks for yml issues
        if (this.configFile == null) {
            configFile = new File(this.main.getDataFolder(), "playerUpgrades.yml");
        }
        dataConfig = YamlConfiguration.loadConfiguration(configFile);
        InputStream defaultStream = this.main.getResource("playerUpgrades.yml");
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
            configFile = new File(this.main.getDataFolder(), "playerUpgrades.yml");
        }
        if (!configFile.exists()) {
            main.saveResource("playerUpgrades.yml", false);
        }
    }
}
