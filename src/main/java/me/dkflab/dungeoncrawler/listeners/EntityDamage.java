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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import static me.dkflab.dungeoncrawler.Utils.*;

public class EntityDamage implements Listener {

    private DungeonCrawler main;
    public EntityDamage(DungeonCrawler main) {
        this.main = main;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        LivingEntity en = (LivingEntity) e.getEntity();
        if (en.isCustomNameVisible()) {
            String[] output = en.getCustomName().split(color("&8"));
            for (String s : output) {
                en.setCustomName(s + color("&8[&a" + (int)en.getHealth() + "&7/&a" + (int)en.getMaxHealth() + "&8]"));
                return;
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        LivingEntity en = e.getEntity();
        if (en.getCustomName() != null) {
            Player p = en.getKiller();
            if (en.getCustomName().contains("Boss")) {
                main.currencyManager.addEmeralds(p.getUniqueId(), 15);
                p.sendMessage(color("&7You now have &a" + main.currencyManager.getEmeralds(p.getUniqueId()) + " emeralds&7."));
                p.sendTitle(color("&6&lYOU WON!"), color("&7Congratulations on surviving the dungeon!"), 20, 20*3, 20);
                p.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, Integer.MAX_VALUE, 255, false, false, false));
                p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 1, false,false,false));
                e.getDrops().clear();

                BukkitRunnable run = new BukkitRunnable() {
                    @Override
                    public void run() {
                        main.getMM().resetDungeon(main.dungeonManager.one);
                    }
                };

                run.runTaskLater(main, 20*5);
            } else {
                e.getDrops().clear();
                main.currencyManager.addEmeralds(p.getUniqueId(), 1);
                p.sendMessage(color("&7You now have &a" + main.currencyManager.getEmeralds(p.getUniqueId()) + " emeralds&7."));
            }
        }
    }
}
