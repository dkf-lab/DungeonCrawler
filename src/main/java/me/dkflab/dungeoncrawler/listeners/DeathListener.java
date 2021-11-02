package me.dkflab.dungeoncrawler.listeners;

import me.dkflab.dungeoncrawler.DungeonCrawler;
import me.dkflab.dungeoncrawler.Utils;
import me.dkflab.dungeoncrawler.objects.Dungeon;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class DeathListener implements Listener {

    private DungeonCrawler main;
    public DeathListener(DungeonCrawler main) {
        this.main = main;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        if (main.getMM().isPlayerInArena(p)) {
            e.setCancelled(true);
            p.getInventory().clear();
            Dungeon d = main.getMM().getDungeonOfPlayer(p);
            main.getMM().removePlayerFromArena(p);
            p.setHealth(p.getMaxHealth());
            p.sendTitle(Utils.color("&cYOU DIED!"), Utils.color("&7All loot you have found has been lost."),20,60,20);
            p.teleport(main.getConfig().getLocation("spawn"));
            for (PotionEffect pe : p.getActivePotionEffects()) {
                p.removePotionEffect(pe.getType());
            }
            p.removePotionEffect(PotionEffectType.WITHER);
            p.setFoodLevel(20);
            Bukkit.getLogger().severe(main.getConfig().getLocation("spawn").toString());
            if (main.getMM().getPlayersInDungeon(d) == null) {
                main.getMM().resetDungeon(main.getMM().getDungeonOfPlayer(p));
            }
            if (main.getMM().getPlayersInDungeon(d).isEmpty()) {
                main.getMM().resetDungeon(main.getMM().getDungeonOfPlayer(p));
            }
        }
    }
}
