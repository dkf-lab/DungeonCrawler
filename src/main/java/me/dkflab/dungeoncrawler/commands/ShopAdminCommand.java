package me.dkflab.dungeoncrawler.commands;

import me.dkflab.dungeoncrawler.DungeonCrawler;
import me.dkflab.dungeoncrawler.Utils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static me.dkflab.dungeoncrawler.Utils.*;

public class ShopAdminCommand implements CommandExecutor, TabCompleter {

    private DungeonCrawler main;
    public ShopAdminCommand(DungeonCrawler main) {
        this.main = main;
        main.getCommand("shopadmin").setTabCompleter(this);
        main.getCommand("shopadmin").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            notPlayer(sender);
            return true;
        }
        Player p = (Player)sender;
        if (args.length == 0) {
            help(sender);
            return true;
        }
        // parse args
        if (args[0].equalsIgnoreCase("add")) {
            if (args.length != 5) {
                help(sender);
                return true;
            }
            String itemId = args[1];
            String store = args[2];
            int slot;
            try {
                slot = Integer.parseInt(args[3]);
            } catch (NumberFormatException e) {
                error(sender, "&e" + args[3] + "&7 is not an integer!");
                return true;
            }
            int price;
            try {
                price = Integer.parseInt(args[4]);
            } catch (NumberFormatException e) {
                error(sender, "&e" + args[4] + "&7 is not an integer!");
                return true;
            }
            ItemStack item = p.getInventory().getItemInMainHand();
            if (item.getType().equals(Material.AIR)) {
                error(sender, "You need to be holding an item!");
                return true;
            }
            // all args parsed
            main.getShopManager().addItemToShop(item,itemId,store,slot,price);
            sendMessage(sender, "&a&lSuccess! &7Item &e" + itemId + "&7 has been added to store &e" + store + "&7 at slot &e" + slot + "&7 at price &e" + price + "&7!");
            return true;
        }
        if (args[0].equalsIgnoreCase("addtomenu")) {
            if (args.length != 4) {
                help(sender);
                return true;
            }
            ItemStack item = p.getInventory().getItemInMainHand();
            if (item.getType().equals(Material.AIR)) {
                error(sender, "You need to be holding an item!");
                return true;
            }
            String itemId = args[1];
            String store = args[2];
            int slot;
            try {
                slot = Integer.parseInt(args[3]);
            } catch (NumberFormatException e) {
                error(sender, "&e" + args[3] + "&7 is not an integer!");
                return true;
            }
            main.getShopManager().addItemToMenu(item,itemId,slot,store);
            sendMessage(sender, "&a&lSuccess! &7Item &e" + itemId + "&7 has been added to the menu, linking to store &e" + store + "&7 at slot &e" + slot + "&7!");
            return true;
        }
        help(sender);
        return true;
    }

    private void help(CommandSender s) {
        sendMessage(s, "&a&lShop Admin Help");
        sendMessage(s, "&8/shopadmin &eadd <itemId> <store> <slot> <price>");
        sendMessage(s, "&8/shopadmin &eaddtomenu <itemID> <store> <slot>");
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> result = new ArrayList<>();
        if (args.length == 1) {
            result.add("add");
            result.add("addtomenu");
        }
        if (args.length == 2) {
            result.add("<itemID>");
        }
        if (args.length == 3) {
            result.add("<store>");
        }
        if (args.length == 4) {
            result.add("<slot>");
        }
        if (args.length == 5) {
            result.add("<price>");
        }
        return result;
    }
}
