package me.dkflab.dungeoncrawler.commands;

import me.dkflab.dungeoncrawler.DungeonCrawler;
import me.dkflab.dungeoncrawler.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BalanceCommand implements CommandExecutor {

    private DungeonCrawler main;
    public BalanceCommand (DungeonCrawler main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("balance")) {
            if (!(sender instanceof Player)) {
                Utils.notPlayer(sender);
                return true;
            }
            sender.sendMessage(Utils.color("&7You currently have &a" + main.currencyManager.getEmeralds(((Player)sender).getUniqueId()) + " emeralds&7."));
        }
        return true;
    }
}
