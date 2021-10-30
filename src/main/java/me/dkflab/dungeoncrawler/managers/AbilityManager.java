package me.dkflab.dungeoncrawler.managers;

import me.dkflab.dungeoncrawler.DungeonCrawler;
import me.dkflab.dungeoncrawler.Utils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AbilityManager {

    private DungeonCrawler main;
    public AbilityManager(DungeonCrawler p) {
        this.main = p;
    }

    public void checkForAbility(Player p, ItemStack item) {
        if (item.isSimilar(main.kits.getHealer().getAbilityItem())) {
            healerAbility(p);
        }
        if (item.isSimilar(main.kits.getTank().getAbilityItem())) {
            tankAbility(p);
        }
    }

    private void tankAbility(Player p) {
        if (p.getCooldown(Material.ENCHANTED_BOOK) != 0) {
            p.sendMessage(Utils.color("&cYour ability is on cooldown!"));
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS,1,1);
            return;
        }
        p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,200,1,false,false));
        p.sendMessage(Utils.color("&aYour ability was successful!"));
        p.setCooldown(Material.ENCHANTED_BOOK, 30*20);
    }

    private void healerAbility(Player p) {
        if (p.getCooldown(Material.ENCHANTED_BOOK) != 0) {
            p.sendMessage(Utils.color("&cYour ability is on cooldown!"));
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS,1,1);
            return;
        }
        for (Player player : Utils.getNearbyPlayers(p,20)) {
            if(player.getHealth()+4 >= player.getMaxHealth()) {
                player.setHealth(player.getMaxHealth());
            } else {
                player.setHealth(player.getHealth()+4);
            }
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,1,1);
            player.sendMessage(Utils.color("&aYou've been healed!"));
        }
        if(p.getHealth()+4 >= p.getMaxHealth()) {
            p.setHealth(p.getMaxHealth());
        } else {
            p.setHealth(p.getHealth()+4);
        }
        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,1,1);
        p.sendMessage(Utils.color("&aYour ability was successful!"));
        p.setCooldown(Material.ENCHANTED_BOOK, 7*20);
    }
}
