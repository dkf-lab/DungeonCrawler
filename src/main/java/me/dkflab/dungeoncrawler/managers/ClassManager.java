package me.dkflab.dungeoncrawler.managers;

import me.dkflab.dungeoncrawler.DungeonCrawler;
import me.dkflab.dungeoncrawler.objects.Ability;
import me.dkflab.dungeoncrawler.objects.Kit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import static me.dkflab.dungeoncrawler.Utils.createItem;
import static me.dkflab.dungeoncrawler.Utils.setArmorColor;

public class ClassManager {

    private DungeonCrawler main;
    public ClassManager(DungeonCrawler m) {
        main = m;
        init();
    }

    public void init() {
        saveDefaultConfig();
        scanForKits();
    }

    public Kit getKitByName(String name) {
        for (Kit k : loadedKits) {
            if (k.getName().equalsIgnoreCase(name)) {
                return k;
            }
        }
        return null;
    }

    public List<Kit> loadedKits = new ArrayList<>();
    private void scanForKits() {
        for (String s : getConfig().getConfigurationSection("classes").getKeys(false)) {
            ConfigurationSection sec = getConfig().getConfigurationSection("classes").getConfigurationSection(s);
            ItemStack weapon;
            String name = sec.getString("name");
            List<ItemStack> armor = new ArrayList<>();
            armor.add(sec.getItemStack("helmet"));
            armor.add(sec.getItemStack("chestplate"));
            armor.add(sec.getItemStack("leggings"));
            armor.add(sec.getItemStack("boots"));
            weapon = sec.getItemStack("weapon");
            loadedKits.add(new Kit(name,weapon,armor,main.abilityManager.getAbilityFromName(sec.getString("ability"))));
        }
    }

    private FileConfiguration dataConfig = null;
    private File configFile = null;

    public void reloadConfig() {
        // creates config, checks for yml issues
        if (this.configFile == null) {
            configFile = new File(this.main.getDataFolder(), "classes.yml");
        }
        dataConfig = YamlConfiguration.loadConfiguration(configFile);
        InputStream defaultStream = this.main.getResource("classes.yml");
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
            configFile = new File(this.main.getDataFolder(), "classes.yml");
        }
        if (!configFile.exists()) {
            main.saveResource("classes.yml", false);
        }
    }

}
