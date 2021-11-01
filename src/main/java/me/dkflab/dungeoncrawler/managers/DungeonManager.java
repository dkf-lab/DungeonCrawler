package me.dkflab.dungeoncrawler.managers;

import me.dkflab.dungeoncrawler.DungeonCrawler;
import me.dkflab.dungeoncrawler.Utils;
import me.dkflab.dungeoncrawler.objects.Dungeon;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class DungeonManager {

    HashMap<Dungeon, HashMap<Block,Material>> blocksToReset = new HashMap<>();
    HashMap<Block, Material> temp = new HashMap<>();
    List<Location> spawns = new ArrayList<>();

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
        spawns.clear();
        if (blocksToReset.get(dungeon) == null) {
            return;
        }
        for (Block b : blocksToReset.get(dungeon).keySet()) {
            b.setType(blocksToReset.get(dungeon).get(b));
        }
        temp.clear();
        blocksToReset.put(dungeon,temp);
    }

    public List<Dungeon> getActiveDungeons() {
        // todo: properly
        if (main.getMM().hasGameStarted(one)) {
            return Collections.singletonList(one);
        }
        return null;
    }

    public void spawnMobs(Dungeon dungeon) {
        // mob spawning handled by loop()
        WitherSkeleton en = (WitherSkeleton) dungeon.getWorld().spawnEntity(dungeon.getBossSpawn(),EntityType.WITHER_SKELETON);
        en.setMaxHealth(300);
        en.setHealth(en.getMaxHealth());
        en.setCustomName(Utils.color("&7Boss &8"));
        en.setCustomNameVisible(true);
        en.damage(1);
    }

    public void loop(Dungeon dungeon) {
        // run once per second
        for (Location loc : dungeon.getMobSpawns()) {
            spawnMob(loc,dungeon);
        }
    }

    private void spawnMob(Location loc, Dungeon dungeon) {
        int roll = Utils.randomNumber(0,100);
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (loc.distanceSquared(p.getLocation()) <= 10) {
                if (spawns.contains(loc)) {
                    return;
                }
                // Zombie 20%
                Bukkit.getLogger().info("Roll: " + roll);
                if (roll <= 20) {
                    Entity e = dungeon.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
                    ((LivingEntity)e).setMaxHealth(100);
                    ((LivingEntity)e).setHealth(((LivingEntity)e).getMaxHealth());
                    e.setCustomName(Utils.color("&7Zombie &8[&a100&7/&a100&8]"));
                    e.setCustomNameVisible(true);
                    spawns.add(loc);
                    return;
                }
                // Skeleton 20%
                if (roll <= 40) {
                    Entity e = dungeon.getWorld().spawnEntity(loc, EntityType.SKELETON);
                    ((LivingEntity)e).setMaxHealth(100);
                    ((LivingEntity)e).setHealth(((LivingEntity)e).getMaxHealth());
                    e.setCustomName(Utils.color("&7Skeleton &8[&a100&7/&a100&8]"));
                    e.setCustomNameVisible(true);
                    spawns.add(loc);
                    return;
                }
                // Spider 20%
                if (roll <= 60) {
                    Entity e = dungeon.getWorld().spawnEntity(loc, EntityType.SPIDER);
                    ((LivingEntity)e).setMaxHealth(100);
                    ((LivingEntity)e).setHealth(((LivingEntity)e).getMaxHealth());
                    e.setCustomName(Utils.color("&7Spider &8[&a100&7/&a100&8]"));
                    e.setCustomNameVisible(true);
                    spawns.add(loc);
                    return;
                }
                // Cave Spider 15%
                if (roll <= 75) {
                    Entity e = dungeon.getWorld().spawnEntity(loc, EntityType.CAVE_SPIDER);
                    ((LivingEntity)e).setMaxHealth(100);
                    ((LivingEntity)e).setHealth(((LivingEntity)e).getMaxHealth());
                    e.setCustomName(Utils.color("&7Cave Spider &8[&a100&7/&a100&8]"));
                    e.setCustomNameVisible(true);
                    spawns.add(loc);
                    return;
                }
                // Husk 10%
                if (roll <= 85) {
                    Entity e = dungeon.getWorld().spawnEntity(loc, EntityType.HUSK);
                    ((LivingEntity)e).setMaxHealth(100);
                    ((LivingEntity)e).setHealth(((LivingEntity)e).getMaxHealth());
                    e.setCustomName(Utils.color("&7Husk &8[&a100&7/&a100&8]"));
                    e.setCustomNameVisible(true);
                    spawns.add(loc);
                    return;
                }
                // Stray 10%
                if (roll <= 95) {
                    Entity e = dungeon.getWorld().spawnEntity(loc, EntityType.STRAY);
                    ((LivingEntity)e).setMaxHealth(100);
                    ((LivingEntity)e).setHealth(((LivingEntity)e).getMaxHealth());
                    e.setCustomName(Utils.color("&7Stray &8[&a100&7/&a100&8]"));
                    e.setCustomNameVisible(true);
                    spawns.add(loc);
                    return;
                }
                // Wither Skeleton 5%
                if (roll <= 100) {
                    Entity e = dungeon.getWorld().spawnEntity(loc, EntityType.WITHER_SKELETON);
                    ((LivingEntity)e).setMaxHealth(100);
                    ((LivingEntity)e).setHealth(((LivingEntity)e).getMaxHealth());
                    e.setCustomName(Utils.color("&7Wither Skeleton &8[&a100&7/&a100&8]"));
                    e.setCustomNameVisible(true);
                    spawns.add(loc);
                }
            }
        }
    }
}
