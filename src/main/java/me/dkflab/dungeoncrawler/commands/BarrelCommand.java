package me.dkflab.dungeoncrawler.commands;

import me.dkflab.dungeoncrawler.DungeonCrawler;
import me.dkflab.dungeoncrawler.Utils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import static me.dkflab.dungeoncrawler.Utils.*;

public class BarrelCommand implements CommandExecutor {

    private DungeonCrawler main;
    public BarrelCommand(DungeonCrawler main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("barrel")) {
            if (!(sender instanceof Player)) {
                notPlayer(sender);
                return true;
            }
            Player p = (Player)sender;
            ItemStack i = p.getInventory().getItemInMainHand();
            if (i.getType().equals(Material.AIR)) {
                p.sendMessage(color("&7You need to &chold an item &7in your hand!"));
                return true;
            }
            if (args.length != 1) {
                p.sendMessage(color("&7Usage: &c/barrel <name>"));
                return true;
            }
            main.barrelManager.addItemToConfig(args[0], i);
            p.sendMessage(color("&aSuccess! &7Item added to loot.yml"));
        }
        return true;
    }
}
