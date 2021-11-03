package me.dkflab.dungeoncrawler.listeners;

import me.dkflab.dungeoncrawler.DungeonCrawler;
import me.dkflab.dungeoncrawler.objects.Dungeon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeave implements Listener {

    private DungeonCrawler main;
    public PlayerLeave(DungeonCrawler main) {
        this.main = main;
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        if (main.getMM().isPlayerInArena(e.getPlayer())) {
            Dungeon d = main.getMM().getDungeonOfPlayer(e.getPlayer());
            main.getMM().removePlayerFromArena(e.getPlayer());
            if (main.getMM().getPlayersInDungeon(d) == null) {
                main.getMM().resetDungeon(d);
                return;
            }
            if (main.getMM().getPlayersInDungeon(d).isEmpty()) {
                main.getMM().resetDungeon(d);
            }
        }
    }
}
