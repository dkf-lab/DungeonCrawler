package me.dkflab.dungeoncrawler.managers;

import me.dkflab.dungeoncrawler.DungeonCrawler;
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

public class KitsManager {

    private DungeonCrawler main;
    public KitsManager(DungeonCrawler m) {
        main = m;
        init();
    }

    public Kit healer;
    public Kit tank;

    public void init() {
        saveDefaultConfig();
        // depreciated
        createHealer();
        createTank();
    }

    private void createHealer() {
        List<ItemStack> armor = new ArrayList<>();
        List<String> lore = new ArrayList<>();
        List<ItemFlag> flags = new ArrayList<>();
        HashMap<Enchantment, Integer> enchants = new HashMap<>();

        // Rod
        lore.add("&7Shiny but causes");
        lore.add("&7low damage.");
        enchants.put(Enchantment.DAMAGE_ALL, 10);
        flags.add(ItemFlag.HIDE_UNBREAKABLE);
        flags.add(ItemFlag.HIDE_ENCHANTS);
        ItemStack weapon = (createItem(Material.BLAZE_ROD, 1, "&6&lStaff of The Order", lore, enchants, flags, true));

        // Book
        lore.clear();
        lore.add("&7Heals allies in a radius of");
        lore.add("&73 blocks for 2 hearts.");
        lore.add("&r");
        lore.add("&87 second cooldown.");
        ItemStack ability = createItem(Material.ENCHANTED_BOOK, 1, "&dHealing Tier 1", lore, null, flags,false);

        // Armor
        flags.add(ItemFlag.HIDE_DYE);
        armor.add(setArmorColor(createItem(Material.LEATHER_CHESTPLATE, 1, "&fChest of The Scholar", null, null, flags, true), Color.WHITE));
        armor.add(setArmorColor(createItem(Material.LEATHER_LEGGINGS, 1, "&fLeggings of The Scholar", null, null, flags, true), Color.WHITE));
        armor.add(setArmorColor(createItem(Material.LEATHER_BOOTS, 1, "&fBoots of The Scholar", null, null, flags, true), Color.WHITE));

        // Pickaxe
        enchants.clear();
        enchants.put(Enchantment.DIG_SPEED, 3);
        lore.clear();
        lore.add("&7A portable drill,");
        lore.add("&7made in China.");
        ItemStack pic = createItem(Material.GOLDEN_PICKAXE, 1, "&9&lDrill", lore, enchants, flags, true);

        this.healer = new Kit("healer", weapon, armor, ability,pic);
    }

    public Kit getHealer() {
        if (healer == null) {
            createHealer();
        }
        return this.healer;
    }

    private void createTank() {
        List<ItemStack> armor = new ArrayList<>();
        List<String> lore = new ArrayList<>();
        List<ItemFlag> flags = new ArrayList<>();
        HashMap<Enchantment, Integer> enchants = new HashMap<>();

        flags.add(ItemFlag.HIDE_UNBREAKABLE);
        flags.add(ItemFlag.HIDE_ENCHANTS);
        // Sword
        enchants.put(Enchantment.DAMAGE_ALL, 3);
        lore.add("&7A powerful sword with");
        lore.add("&7mysterious markings.");
        ItemStack weapon = (createItem(Material.STONE_SWORD, 1, "&c&lSword of Power", lore, enchants, flags,true));

        // Ability
        lore.clear();
        lore.add("&7Give yourself strength");
        lore.add("&7for 10 seconds.");
        lore.add("&r");
        lore.add("&830 second cooldown.");
        ItemStack ability = createItem(Material.ENCHANTED_BOOK, 1, "&dFury Tier 1", lore, null, flags, false);

        // Armor
        armor.add(createItem(Material.CHAINMAIL_CHESTPLATE, 1, "&7Chest of Valor", null, null, flags, true));
        armor.add(createItem(Material.CHAINMAIL_LEGGINGS, 1, "&7Leggings of Valor", null, null, flags, true));
        armor.add(createItem(Material.CHAINMAIL_BOOTS, 1, "&7Boots of Valor", null, null, flags, true));

        // Pickaxe
        enchants.clear();
        enchants.put(Enchantment.DIG_SPEED, 3);
        lore.clear();
        lore.add("&7A portable drill,");
        lore.add("&7made in China.");
        ItemStack pic = createItem(Material.GOLDEN_PICKAXE, 1, "&9&lDrill", lore, enchants, flags, true);
        this.tank = new Kit("tank", weapon, armor, ability, pic);
    }

    public Kit getTank() {
        if (tank == null) {
            createTank();
        }
        return this.tank;
    }

    // REWRITE

    public Kit getKitByName(String name) {
        return null;
    }

    private void scanForKits() {
        for (String s : getConfig().getConfigurationSection("classes").getKeys(false)) {
            ConfigurationSection sec = getConfig().getConfigurationSection("classes").getConfigurationSection(s);
            ItemStack helmet,chestplate,leggings,boots,weapon,pickaxe;
            String name = sec.getString("name");
            helmet = sec.getItemStack("helmet");
            chestplate = sec.getItemStack("chestplate");
            leggings = sec.getItemStack("leggings");
            boots = sec.getItemStack("boots");
            weapon = sec.getItemStack("weapon");
            pickaxe = sec.getItemStack("pickaxe");
            String ability = sec.getString("ability");
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
