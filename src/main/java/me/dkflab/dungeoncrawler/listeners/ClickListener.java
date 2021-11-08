package me.dkflab.dungeoncrawler.listeners;

import me.dkflab.dungeoncrawler.DungeonCrawler;
import org.bukkit.Material;
import org.bukkit.block.Barrel;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.PlayerInventory;

public class ClickListener implements Listener {

    private DungeonCrawler main;
    public ClickListener(DungeonCrawler main) {
        this.main = main;
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)||e.getAction().equals(Action.RIGHT_CLICK_AIR)||e.getAction().equals(Action.LEFT_CLICK_AIR)||e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            Player p = e.getPlayer();
            PlayerInventory inv = p.getInventory();
            // Ability check
            main.abilityManager.checkForAbility(p,inv.getItemInMainHand());
            if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (e.getClickedBlock() == null) {
                    return;
                }
                if (e.getClickedBlock().getType().equals(Material.BARREL)) {
                    main.barrelManager.generateLootForBarrel((Barrel)e.getClickedBlock().getState());
                }
            }
        }
    }
}
