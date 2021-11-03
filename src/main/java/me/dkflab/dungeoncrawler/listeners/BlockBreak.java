package me.dkflab.dungeoncrawler.listeners;

import me.dkflab.dungeoncrawler.DungeonCrawler;
import me.dkflab.dungeoncrawler.Utils;
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
            if (m.equals(Material.EMERALD_ORE)) {
                method(p, b, Material.EMERALD);
            }
        }
    }

    private void method(Player p, Block block, Material m) {
        main.dungeonManager.addItemToReset(main.getMM().getDungeonOfPlayer(p),block,block.getType());
        main.currencyManager.addEmeralds(p.getUniqueId(), 1);
        p.sendMessage(Utils.color("&7You now have &a" + main.currencyManager.getEmeralds(p.getUniqueId()) + " emeralds&7."));
        block.setType(Material.BEDROCK);
    }
}
