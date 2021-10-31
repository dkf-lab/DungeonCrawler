package me.dkflab.dungeoncrawler.listeners;

import me.dkflab.dungeoncrawler.DungeonCrawler;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class BlockBreak implements Listener {

    private DungeonCrawler main;
    public BlockBreak(DungeonCrawler main) {
        this.main = main;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (main.getMM().inArena(p)) {
            e.setCancelled(true);
            Material m = e.getBlock().getType();
            Block b = e.getBlock();
            if (m.equals(Material.DIAMOND_ORE)) {
                method(p, b,Material.DIAMOND);
            }
            if (m.equals(Material.EMERALD_ORE)) {
                method(p,b,Material.EMERALD);
            }
            if (m.equals(Material.IRON_ORE)) {
                method(p,b,Material.IRON_INGOT);
            }
            if (m.equals(Material.GOLD_ORE)) {
                method(p,b,Material.GOLD_INGOT);
            }
            if (m.equals(Material.COAL_ORE)) {
                method(p,b,Material.COAL);
            }
        }
    }

    private void method(Player p, Block block, Material m) {
        main.dungeonManager.addItemToReset(main.getMM().getDungeonOfPlayer(p),block,block.getType());
        p.getInventory().addItem(new ItemStack(m, 1));
        block.setType(Material.BEDROCK);
    }
}
