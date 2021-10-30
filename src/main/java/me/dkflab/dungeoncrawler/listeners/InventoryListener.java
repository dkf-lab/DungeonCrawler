package me.dkflab.dungeoncrawler.listeners;

import me.dkflab.dungeoncrawler.DungeonCrawler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryListener implements Listener {

    private DungeonCrawler main;
    public InventoryListener(DungeonCrawler m) {
        main = m;
    }

    @EventHandler
    public void inventory(InventoryClickEvent e) {
        main.getGUI().event(e);
    }
}
