package me.dkflab.dungeoncrawler.objects;

import me.dkflab.dungeoncrawler.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class Dungeon {

    private String name,world,fancyName;
    private List<Location> mobSpawns = new ArrayList<>();
    private Location playerSpawn,bossSpawn;
    private ConfigurationSection configurationSection;
    private int slot;
    private Material material;

    public Dungeon(final ConfigurationSection section) {
        if (section.getString("material") != null) {
            material = Material.getMaterial(section.getString("material").toUpperCase());
        }
        this.configurationSection = section;
        this.name = section.getName();
        this.fancyName = section.getString("name");
        this.world = section.getString("world");
        this.slot = section.getInt("slot");
        // player spawn point
        ConfigurationSection spawn = section.getConfigurationSection("spawn");
        this.playerSpawn = new Location(getWorld(), spawn.getInt("x"), spawn.getInt("y"), spawn.getInt("z"), spawn.getInt("yaw"),spawn.getInt("pitch"));
        // boss spawn
        spawn = section.getConfigurationSection("bossSpawn");
        this.bossSpawn = new Location(getWorld(), spawn.getInt("x"),spawn.getInt("y"),spawn.getInt("z"));
        // mob spawns
        for (String s : section.getConfigurationSection("mobSpawns").getKeys(false)) {
            ConfigurationSection cs = section.getConfigurationSection("mobSpawns").getConfigurationSection(s);
            this.mobSpawns.add(new Location(getWorld(), cs.getInt("x"),cs.getInt("y"),cs.getInt("z")));
        }
    }

    public String getFancyName() {
        return Utils.color(this.fancyName);
    }

    public String getName() {
        return this.name;
    }

    public World getWorld() {
        return Bukkit.getWorld(this.world);
    }

    public Location getSpawn() {
        return this.playerSpawn;
    }

    public Location getBossSpawn() {
        return this.bossSpawn;
    }

    public int getSlot() {
        return this.slot;
    }

    public Material getMaterial() {
        if (this.material == null) {
            this.material = Material.BARRIER;
            Bukkit.getLogger().severe("Set a MATERIAL for " + getFancyName());
        }
        return this.material;
    }

    public List<Location> getMobSpawns() {
        return this.mobSpawns;
    }

    public ConfigurationSection getConfigurationSection() {
        return this.configurationSection;
    }
}
