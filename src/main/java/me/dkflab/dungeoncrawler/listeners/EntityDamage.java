package me.dkflab.dungeoncrawler.listeners;

import me.dkflab.dungeoncrawler.DungeonCrawler;
import me.dkflab.dungeoncrawler.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class EntityDamage implements Listener {

    private DungeonCrawler main;
    public EntityDamage(DungeonCrawler main) {
        this.main = main;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        LivingEntity en = (LivingEntity) e.getEntity();
        if (en.isCustomNameVisible()) {
            String[] output = en.getCustomName().split(Utils.color("&8"));
            for (String s : output) {
                en.setCustomName(s + Utils.color("&8[&a" + (int)en.getHealth() + "&7/&a" + (int)en.getMaxHealth() + "&8]"));
                return;
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        LivingEntity en = e.getEntity();
        if (en.getCustomName() != null) {
            if (en.getCustomName().contains("Boss")) {
                Player p = en.getKiller();
                p.getInventory().addItem(new ItemStack(Material.DIAMOND, 10));
                p.sendMessage(Utils.color("&aYou've defeated the boss and been given &c10x Diamonds&a!"));
                // finish arena
                e.getDrops().clear();
                main.getMM().resetDungeon(main.dungeonManager.one);
            } else {
                e.getDrops().clear();
            }
        }
    }
}
