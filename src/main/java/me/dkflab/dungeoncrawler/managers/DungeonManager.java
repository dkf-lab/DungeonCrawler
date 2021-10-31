package me.dkflab.dungeoncrawler.managers;

import me.dkflab.dungeoncrawler.DungeonCrawler;
import me.dkflab.dungeoncrawler.Utils;
import me.dkflab.dungeoncrawler.objects.Dungeon;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.*;

import java.util.HashMap;

public class DungeonManager {

    HashMap<Dungeon, HashMap<Block,Material>> blocksToReset = new HashMap<>();
    HashMap<Block, Material> temp = new HashMap<>();

    private DungeonCrawler main;
    public DungeonManager(DungeonCrawler main) {
        this.main = main;
        init();
    }

    public Dungeon one;

    private void init() {
        //main.getConfig().getConfigurationSection("dungeons").getKeys(false);
        // TODO: properly
        one = new Dungeon(main.getConfig().getConfigurationSection("dungeons.one"));
    }

    public void addItemToReset(Dungeon d, Block block, Material original) {
        temp.clear();
        blocksToReset.putIfAbsent(d,temp);
        temp = blocksToReset.get(d);
        temp.put(block, original);
        blocksToReset.put(d,temp);
    }

    public void resetBlocks(Dungeon dungeon) {
        for (Block b : blocksToReset.get(dungeon).keySet()) {
            b.setType(blocksToReset.get(dungeon).get(b));
        }
        temp.clear();
        blocksToReset.put(dungeon,temp);
    }

    public void spawnMobs(Dungeon dungeon) {
        for (Location loc : dungeon.getMobSpawns()) {
            int roll = Utils.randomNumber(0,100);
            // Zombie 20%
            Bukkit.getLogger().info("Roll: " + roll);
            if (roll <= 20) {
                Entity e = dungeon.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
                e.setCustomName(Utils.color("&7Zombie &8[&a20&7/&a20&8]"));
                e.setCustomNameVisible(true);
                continue;
            }
            // Skeleton 20%
            if (roll <= 40) {
                Entity e = dungeon.getWorld().spawnEntity(loc, EntityType.SKELETON);
                e.setCustomName(Utils.color("&7Skeleton &8[&a20&7/&a20&8]"));
                e.setCustomNameVisible(true);
                continue;
            }
            // Spider 20%
            if (roll <= 60) {
                Entity e = dungeon.getWorld().spawnEntity(loc, EntityType.SPIDER);
                e.setCustomName(Utils.color("&7Spider &8[&a20&7/&a20&8]"));
                e.setCustomNameVisible(true);
                continue;
            }
            // Cave Spider 15%
            if (roll <= 75) {
                Entity e = dungeon.getWorld().spawnEntity(loc, EntityType.CAVE_SPIDER);
                e.setCustomName(Utils.color("&7Spider &8[&a20&7/&a20&8]"));
                e.setCustomNameVisible(true);
                continue;
            }
            // Husk 10%
            if (roll <= 85) {
                Entity e = dungeon.getWorld().spawnEntity(loc, EntityType.HUSK);
                e.setCustomName(Utils.color("&7Spider &8[&a20&7/&a20&8]"));
                e.setCustomNameVisible(true);
                continue;
            }
            // Stray 10%
            if (roll <= 95) {
                Entity e = dungeon.getWorld().spawnEntity(loc, EntityType.STRAY);
                e.setCustomName(Utils.color("&7Spider &8[&a20&7/&a20&8]"));
                e.setCustomNameVisible(true);
                continue;
            }
            // Wither Skeleton 5%
            if (roll <= 100) {
                Entity e = dungeon.getWorld().spawnEntity(loc, EntityType.WITHER_SKELETON);
                e.setCustomName(Utils.color("&7Spider &8[&a20&7/&a20&8]"));
                e.setCustomNameVisible(true);
            }
        }
        // TODO: Boss spawn
        WitherSkeleton en = (WitherSkeleton) dungeon.getWorld().spawnEntity(dungeon.getBossSpawn(),EntityType.WITHER_SKELETON);
        en.setMaxHealth(100);
        en.setHealth(en.getMaxHealth());
        en.setCustomName(Utils.color("&7Boss &8"));
        en.setCustomNameVisible(true);
        en.damage(1);
    }
}
