package me.dkflab.dungeoncrawler.managers;

import me.dkflab.dungeoncrawler.DungeonCrawler;
import me.dkflab.dungeoncrawler.objects.Dungeon;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.HashMap;

public class DungeonManager {

    HashMap<Block, Material> blocksToReset = new HashMap<>();

    private DungeonCrawler main;
    public DungeonManager(DungeonCrawler main) {
        this.main = main;
        init();
    }

    public Dungeon one;

    private void init() {
        //main.getConfig().getConfigurationSection("dungeons").getKeys(false);
        // TODO: properly
        one = new Dungeon(main.getConfig().getConfigurationSection("dungeons.example"));
    }

    public void addItemToReset(Block block, Material original) {
        blocksToReset.put(block, original);
    }

    public void resetBlocks() {
        for (Block b : blocksToReset.keySet()) {
            b.setType(blocksToReset.get(b));
        }
        blocksToReset.clear();
    }

}
