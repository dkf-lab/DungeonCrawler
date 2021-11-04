package me.dkflab.dungeoncrawler.managers;

import me.dkflab.dungeoncrawler.DungeonCrawler;
import me.dkflab.dungeoncrawler.Utils;
import me.dkflab.dungeoncrawler.objects.Ability;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

public class AbilityManager {

    List<Ability> loadedAbilities = new ArrayList<>();
    HashMap<Ability, String> legacyAbilites = new HashMap<>();

    private DungeonCrawler main;
    public AbilityManager(DungeonCrawler p) {
        this.main = p;
        saveDefaultConfig();
        scanConfig();
    }

    public List<Ability> getLoadedAbilities() {
        return this.loadedAbilities;
    }

    public void scanConfig() {
        ConfigurationSection section = getConfig().getConfigurationSection("abilities");
        for (String str : section.getKeys(false)) {
            Ability a = new Ability(section.getConfigurationSection(str));
            loadedAbilities.add(a);
            legacyAbilites.put(a,str);
        }
    }

    public Ability getAbilityFromName(String name) {
        for (Ability a : loadedAbilities) {
            if (a.getName().contains(name)) {
                return a;
            }
            if (legacyAbilites.get(a).toLowerCase().contains(name.toLowerCase())) {
                return a;
            }
        }
        return null;
    }

    public void checkForAbility(Player p, ItemStack item) {
        for (Ability a : loadedAbilities) {
            if (a.getItem().isSimilar(item)) {
                if (a.getPotionEffect() != null) {
                    potionAbility(a,p);
                    return;
                }
                if (a.getHealAmount() != 0) {
                    healAbility(a, p);
                    return;
                }
            }
        }
    }

    private void potionAbility(Ability a, Player p) {
        if (p.getCooldown(a.getItem().getType()) != 0) {
            p.sendMessage(Utils.color("&cYour ability is on cooldown!"));
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS,1,1);
            return;
        }
        p.addPotionEffect(a.getPotionEffect());
        Bukkit.getLogger().info("Potion Effect: " + a.getPotionEffect());
        p.sendMessage(Utils.color("&aYour ability was successful!"));
        p.setCooldown(a.getItem().getType(), a.getCooldown()*20);
    }

    private void healAbility(Ability a, Player p) {
        int amount = a.getHealAmount();
        if (p.getCooldown(Material.ENCHANTED_BOOK) != 0) {
            p.sendMessage(Utils.color("&cYour ability is on cooldown!"));
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS,1,1);
            return;
        }
        for (Player player : Utils.getNearbyPlayers(p,a.getHealRadius())) {
            if(player.getHealth()+amount >= player.getMaxHealth()) {
                player.setHealth(player.getMaxHealth());
            } else {
                player.setHealth(player.getHealth()+amount);
            }
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,1,1);
            player.sendMessage(Utils.color("&aYou've been healed!"));
        }
        if(p.getHealth()+amount >= p.getMaxHealth()) {
            p.setHealth(p.getMaxHealth());
        } else {
            p.setHealth(p.getHealth()+amount);
        }
        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,1,1);
        p.sendMessage(Utils.color("&aYour ability was successful!"));
        p.setCooldown(Material.ENCHANTED_BOOK, a.getCooldown()*20);
    }

    private FileConfiguration dataConfig = null;
    private File configFile = null;

    public void reloadConfig() {
        // creates config, checks for yml issues
        if (this.configFile == null) {
            configFile = new File(this.main.getDataFolder(), "abilities.yml");
        }
        dataConfig = YamlConfiguration.loadConfiguration(configFile);
        InputStream defaultStream = this.main.getResource("abilities.yml");
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
            configFile = new File(this.main.getDataFolder(), "abilities.yml");
        }
        if (!configFile.exists()) {
            main.saveResource("abilities.yml", false);
        }
    }
}
