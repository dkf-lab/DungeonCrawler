package me.dkflab.dungeoncrawler.commands;

import me.dkflab.dungeoncrawler.DungeonCrawler;
import me.dkflab.dungeoncrawler.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ClassCommand implements CommandExecutor {

    // TODO: CHANGE TO GUI
    private DungeonCrawler plugin;
    public ClassCommand(DungeonCrawler main) {
        plugin = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("class")) {
            if (!(sender instanceof Player)) {
                Utils.notPlayer(sender);
                return true;
            }
            Player p = (Player)sender;
            if (args[0].equals("healer")) {
                plugin.kits.getHealer().giveToPlayer(p);
            }
            if (args[0].equals("tank")) {
                plugin.kits.getTank().giveToPlayer(p);
            }
        }
        return true;
    }
}
