package me.dkflab.dungeoncrawler.objects;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class Dungeon {

    private String name,world;
    private List<Location> mobSpawns = new ArrayList<>();
    private Location playerSpawn,bossSpawn;

    public Dungeon(final ConfigurationSection section) {
        this.name = section.getString("name");
        this.world = section.getString("world");
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

    public String getName() {
        return this.name;
    }

    public World getWorld() {
        return Bukkit.getWorld(this.world);
    }

    public Location getSpawn() {
        return this.playerSpawn;
    }

    public List<Location> getMobSpawns() {
        return this.mobSpawns;
    }
}
