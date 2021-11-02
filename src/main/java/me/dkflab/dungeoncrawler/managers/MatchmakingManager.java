package me.dkflab.dungeoncrawler.managers;

import me.dkflab.dungeoncrawler.DungeonCrawler;
import me.dkflab.dungeoncrawler.Utils;
import me.dkflab.dungeoncrawler.objects.Dungeon;
import me.dkflab.dungeoncrawler.objects.Kit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MatchmakingManager {

    private DungeonCrawler main;
    public MatchmakingManager (DungeonCrawler m) {
        main = m;
    }

    HashMap<Dungeon, List<Player>> players = new HashMap<>();
    List<Player> temp = new ArrayList<>();
    HashMap<Player, Kit> classes = new HashMap<>();

    public void addPlayerToDungeon(Player p, Dungeon dungeon) {
        if (!hasGameStarted(dungeon)) {
            main.dungeonManager.spawnMobs(dungeon);
        }
        temp.clear();
        players.putIfAbsent(dungeon, temp);
        temp = players.get(dungeon);
        temp.add(p);
        players.put(dungeon,temp);
        //
        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE,255,false,false,false));
        p.teleport(dungeon.getSpawn());
        // open class selection gui
        p.openInventory(main.getGUI().classSelect.getInventory());
        // todo: immunity, blindness
        p.sendMessage(Utils.color("&a&lGood luck! &7Remember to stick with your team."));
    }

    public boolean isPlayerInArena(Player p) {
        for (Dungeon d : players.keySet()) {
            if (players.get(d).contains(p)) {
                return true;
            }
        }
        return false;
    }

    public void setClassOfPlayer(Player p, Kit kit) {
        classes.put(p, kit);
        p.getInventory().clear();
        p.getInventory().setHelmet(null);
        p.getInventory().setChestplate(null);
        p.getInventory().setLeggings(null);
        p.getInventory().setBoots(null);
        kit.giveToPlayer(p);
        p.removePotionEffect(PotionEffectType.BLINDNESS);
    }

    public List<Player> getPlayersInDungeon(Dungeon d) {
        return players.get(d);
    }

    public Dungeon getDungeonOfPlayer(Player p) {
        for (Dungeon d : players.keySet()) {
            if (players.get(d).contains(p)) {
                return d;
            }
        }
        return null;
    }

    public boolean inArena(Player p) {
        for (Dungeon d : players.keySet()) {
            if (players.get(d).contains(p)) {
                return true;
            }
        }
        return false;
    }

    public void removePlayerFromArena(Player p) {
        if (inArena(p)) {
            Dungeon d = getDungeonOfPlayer(p);
            temp.clear();
            temp = players.get(d);
            temp.remove(p);
            players.put(d,temp);
            if (temp.size() <= 1) {
                resetDungeon(d);
            }
            p.teleport(main.getConfig().getLocation("spawn"));
        }
    }

    public Kit getClass(Player p) {
        return classes.get(p);
    }

    public void resetDungeon(Dungeon dungeon) {
        // Teleport players out, reset blocks
        for (Player p : players.get(dungeon)) {
            p.teleport(main.getConfig().getLocation("spawn"));
            p.getInventory().clear();
        }
        players.remove(dungeon);
        main.dungeonManager.resetBlocks(dungeon);
        main.dungeonManager.resetMobs(dungeon);
    }

    public boolean hasGameStarted(Dungeon dungeon) {
        if (players.get(dungeon) != null) {
            if (!players.get(dungeon).isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
