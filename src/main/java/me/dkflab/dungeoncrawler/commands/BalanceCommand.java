package me.dkflab.dungeoncrawler.commands;

import me.dkflab.dungeoncrawler.DungeonCrawler;
import me.dkflab.dungeoncrawler.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import static me.dkflab.dungeoncrawler.Utils.*;

public class BalanceCommand implements CommandExecutor {

    private DungeonCrawler main;
    public BalanceCommand (DungeonCrawler main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("balance")) {
            // balance <set/give/take> <player> <value>
            if (sender.hasPermission("dungeon.admin")) {
                if (args.length == 0) {
                    if (!(sender instanceof Player)) {
                        notPlayer(sender);
                        return true;
                    }
                    sender.sendMessage(color("&7You currently have &a" + main.currencyManager.getEmeralds(((Player)sender).getUniqueId()) + " emeralds&7."));
                    return true;
                }
                if (args.length != 3) {
                    incorrectUsage(sender);
                    return true;
                }
                UUID uuid = null;
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.getName().equalsIgnoreCase(args[1])) {
                        uuid = p.getUniqueId();
                    }
                }
                if (uuid == null) {
                    sender.sendMessage(color("&c" + args[1] + "&7 is not a player name. Check spelling and try again."));
                    return true;
                }
                int amount;
                try {
                    amount = Integer.parseInt(args[2]);
                } catch (Exception e) {
                    sender.sendMessage(color("&c" + args[2] + "&7is not an integer!"));
                    return true;
                }
                switch (args[0]) {
                    case "set":
                        main.currencyManager.setEmeralds(uuid, amount);
                        sender.sendMessage(color("&aSuccess! &7Set &e" + args[1] + "'s&7 emeralds to &e" + amount));
                        return true;
                    case "give":
                        main.currencyManager.addEmeralds(uuid, amount);
                        sender.sendMessage(color("&aSuccess! &7Added &e" + amount + "&7 emeralds to &e" + args[1]));
                        return true;
                    case "take":
                        main.currencyManager.removeEmeralds(uuid, amount);
                        sender.sendMessage(color("&aSuccess! &7Removed &e" + amount + "&7 emeralds from &e" + args[1]));
                        return true;
                    default:
                        incorrectUsage(sender);
                        return true;
                }
            } else {
                if (args.length != 0) {
                    noPerms(sender);
                } else {
                    if (!(sender instanceof Player)) {
                        notPlayer(sender);
                        return true;
                    }
                    sender.sendMessage(color("&7You currently have &a" + main.currencyManager.getEmeralds(((Player)sender).getUniqueId()) + " emeralds&7."));
                }
            }
        }
        return true;
    }

    private void incorrectUsage(CommandSender sender) {
        sender.sendMessage(color("&cIncorrect Usage. &7/bal &e<set/give/take> <player> <amount>"));
    }
}
