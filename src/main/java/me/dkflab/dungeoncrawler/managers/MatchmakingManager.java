package me.dkflab.dungeoncrawler.managers;

import me.dkflab.dungeoncrawler.DungeonCrawler;
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

    List<Player> dungeonOne = new ArrayList<>();
    HashMap<Player, Kit> classes = new HashMap<>();

    public void addPlayerToDungeonOne(Player p) {
        dungeonOne.add(p);
        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE,255,false,false,false));
        p.teleport(main.dungeonManager.one.getSpawn());
        // open class selection gui
        p.openInventory(main.getGUI().classSelect.getInventory());
        // todo: immunity
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

    public Dungeon getDungeonOfPlayer(Player p) {
        if (dungeonOne.contains(p)) {
            return main.dungeonManager.one;
        }
        return null;
    }

    public boolean inArena(Player p) {
        if (dungeonOne.contains(p)) {
            return true;
        }
        return false;
    }

    public void removePlayerFromArena(Player p) {
        if (inArena(p)) {
            dungeonOne.remove(p);
        }
    }

    public Kit getClass(Player p) {
        return classes.get(p);
    }
}
