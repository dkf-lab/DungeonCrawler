package me.dkflab.dungeoncrawler.managers;

import me.dkflab.dungeoncrawler.DungeonCrawler;
import me.dkflab.dungeoncrawler.Utils;
import me.dkflab.dungeoncrawler.objects.Dungeon;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class DungeonManager {

    HashMap<Dungeon, HashMap<Block,Material>> blocksToReset = new HashMap<>();
    List<Location> mobSpawns = new ArrayList<>();
    HashMap<Dungeon, List<LivingEntity>> liveMobs = new HashMap<>();

    private DungeonCrawler main;
    public DungeonManager(DungeonCrawler main) {
        this.main = main;
        init();
    }

    public Dungeon one;

    List<Dungeon> activeDungeons = new ArrayList<>();

    private void init() {
        for (String s : main.getConfig().getConfigurationSection("dungeons").getKeys(false)) {
            activeDungeons.add(new Dungeon(main.getConfig().getConfigurationSection("dungeons").getConfigurationSection(s)));
        }
    }

    public void addItemToReset(Dungeon d, Block block, Material original) {
        HashMap<Block, Material> temp = new HashMap<>();
        if (blocksToReset.get(d) != null) {
            temp = blocksToReset.get(d);
        }
        temp.put(block, original);
        blocksToReset.put(d,temp);
    }

    public void addMobToReset(Dungeon d, LivingEntity mob) {
        List<LivingEntity> list = liveMobs.get(d);
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(mob);
        liveMobs.put(d,list);
    }

    public void resetBlocks(Dungeon dungeon) {
        mobSpawns.clear();
        if (blocksToReset.get(dungeon) == null) {
            return;
        }
        for (Block b : blocksToReset.get(dungeon).keySet()) {
            b.setType(blocksToReset.get(dungeon).get(b));
        }
        blocksToReset.remove(dungeon);
    }

    public void resetMobs(Dungeon dungeon) {
        for (LivingEntity en : liveMobs.get(dungeon)) {
            en.remove();
        }
    }

    public List<Dungeon> getActiveDungeons() {
        return activeDungeons;
    }

    public void spawnMobs(Dungeon dungeon) {
        // mob spawning handled by loop()
        WitherSkeleton en = (WitherSkeleton) dungeon.getWorld().spawnEntity(dungeon.getBossSpawn(),EntityType.WITHER_SKELETON);
        en.setMaxHealth(300);
        en.setHealth(en.getMaxHealth());
        en.setCustomName(Utils.color("&7Boss &8"));
        en.setCustomNameVisible(true);
        en.damage(1);
        addMobToReset(dungeon,en);
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
                if (mobSpawns.contains(loc)) {
                    return;
                }
                // Zombie 20%
                Bukkit.getLogger().info("Roll: " + roll);
                p.playSound(loc, Sound.ENTITY_PHANTOM_HURT, 3.0F,0.5F);
                if (roll <= 20) {
                    LivingEntity e = (LivingEntity) dungeon.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
                    ((LivingEntity)e).setMaxHealth(100);
                    ((LivingEntity)e).setHealth(((LivingEntity)e).getMaxHealth());
                    e.setCustomName(Utils.color("&7Zombie &8[&a100&7/&a100&8]"));
                    e.setCustomNameVisible(true);
                    mobSpawns.add(loc);
                    addMobToReset(dungeon,e);
                    return;
                }
                // Skeleton 20%
                if (roll <= 40) {
                    LivingEntity e = (LivingEntity) dungeon.getWorld().spawnEntity(loc, EntityType.SKELETON);
                    e.setMaxHealth(100);
                    e.setHealth(e.getMaxHealth());
                    e.setCustomName(Utils.color("&7Skeleton &8[&a100&7/&a100&8]"));
                    e.setCustomNameVisible(true);
                    mobSpawns.add(loc);
                    addMobToReset(dungeon,e);
                    return;
                }
                // Spider 20%
                if (roll <= 60) {
                    LivingEntity e = (LivingEntity) dungeon.getWorld().spawnEntity(loc, EntityType.SPIDER);
                    ((LivingEntity)e).setMaxHealth(100);
                    ((LivingEntity)e).setHealth(((LivingEntity)e).getMaxHealth());
                    e.setCustomName(Utils.color("&7Spider &8[&a100&7/&a100&8]"));
                    e.setCustomNameVisible(true);
                    mobSpawns.add(loc);
                    addMobToReset(dungeon,e);
                    return;
                }
                // Cave Spider 15%
                if (roll <= 75) {
                    LivingEntity e = (LivingEntity) dungeon.getWorld().spawnEntity(loc, EntityType.CAVE_SPIDER);
                    ((LivingEntity)e).setMaxHealth(100);
                    ((LivingEntity)e).setHealth(((LivingEntity)e).getMaxHealth());
                    e.setCustomName(Utils.color("&7Cave Spider &8[&a100&7/&a100&8]"));
                    e.setCustomNameVisible(true);
                    mobSpawns.add(loc);
                    addMobToReset(dungeon,e);
                    return;
                }
                // Husk 10%
                if (roll <= 85) {
                    LivingEntity e = (LivingEntity) dungeon.getWorld().spawnEntity(loc, EntityType.HUSK);
                    ((LivingEntity)e).setMaxHealth(100);
                    ((LivingEntity)e).setHealth(((LivingEntity)e).getMaxHealth());
                    e.setCustomName(Utils.color("&7Husk &8[&a100&7/&a100&8]"));
                    e.setCustomNameVisible(true);
                    mobSpawns.add(loc);
                    addMobToReset(dungeon,e);
                    return;
                }
                // Stray 10%
                if (roll <= 95) {
                    LivingEntity e = (LivingEntity) dungeon.getWorld().spawnEntity(loc, EntityType.STRAY);
                    ((LivingEntity)e).setMaxHealth(100);
                    ((LivingEntity)e).setHealth(((LivingEntity)e).getMaxHealth());
                    e.setCustomName(Utils.color("&7Stray &8[&a100&7/&a100&8]"));
                    e.setCustomNameVisible(true);
                    mobSpawns.add(loc);
                    addMobToReset(dungeon,e);
                    return;
                }
                // Wither Skeleton 5%
                if (roll <= 100) {
                    LivingEntity e = (LivingEntity) dungeon.getWorld().spawnEntity(loc, EntityType.WITHER_SKELETON);
                    ((LivingEntity)e).setMaxHealth(100);
                    ((LivingEntity)e).setHealth(((LivingEntity)e).getMaxHealth());
                    e.setCustomName(Utils.color("&7Wither Skeleton &8[&a100&7/&a100&8]"));
                    e.setCustomNameVisible(true);
                    mobSpawns.add(loc);
                    addMobToReset(dungeon,e);
                }
            }
        }
    }
}
