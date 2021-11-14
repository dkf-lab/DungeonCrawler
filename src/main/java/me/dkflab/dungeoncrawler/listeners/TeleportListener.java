package me.dkflab.dungeoncrawler.listeners;

import me.dkflab.dungeoncrawler.DungeonCrawler;
import me.dkflab.dungeoncrawler.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TeleportListener implements Listener {

    private DungeonCrawler main;
    public TeleportListener(DungeonCrawler main) {
        this.main = main;
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        Player p = e.getPlayer();
        if (main.getMM().isPlayerInArena(p)) {
            e.setCancelled(true);
            p.sendMessage(Utils.color("&cHey! &7You can't teleport out of an dungeon."));
            p.sendMessage(Utils.color("&7Leave the dungeon by running &e/dungeon leave"));
        }
    }
}
