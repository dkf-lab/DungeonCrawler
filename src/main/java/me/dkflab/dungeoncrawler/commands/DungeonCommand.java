package me.dkflab.dungeoncrawler.commands;

import me.dkflab.dungeoncrawler.DungeonCrawler;
import me.dkflab.dungeoncrawler.Utils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class DungeonCommand implements CommandExecutor {

    private DungeonCrawler main;
    public DungeonCommand(DungeonCrawler main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("dungeon")) {
            if (!(sender instanceof Player)) {
                Utils.notPlayer(sender);
                return true;
            }
            Player p = (Player)sender;
            if (args.length == 0) {
                p.openInventory(main.gui.dungeonSelect.getInventory());
                return true;
            }
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("leave")) {
                    main.getMM().removePlayerFromArena(p);
                    p.getInventory().clear();
                    p.sendMessage(Utils.color("&cYou have left the dungeon."));
                    p.teleport(main.getConfig().getLocation("spawn"));
                    return true;
                }
                if (args[0].equalsIgnoreCase("help")) {
                    if (sender.hasPermission("dungeon.admin")) {
                        sender.sendMessage(Utils.color("&b&lDungeon Help"));
                        sender.sendMessage(Utils.color("&e/dungeon config <dungeon> <boss/spawn/mobs>"));
                        sender.sendMessage(Utils.color("&e/dungeon config <lobby>"));
                    }
                    p.openInventory(main.gui.dungeonSelect.getInventory());
                    return true;
                }
                if (args[0].equalsIgnoreCase("config")) {
                    if (sender.hasPermission("dungeon.admin")) {
                        sender.sendMessage(Utils.color("&c/dungeon config <dungeon> <boss/spawn/mobs>"));
                        sender.sendMessage(Utils.color("&c/dungeon config <lobby>"));
                    }
                    p.openInventory(main.gui.dungeonSelect.getInventory());
                    return true;
                }
            }
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("config")) {
                    if (!p.hasPermission("dungeon.admin")) {
                        Utils.noPerms(p);
                        return true;
                    }
                    if (args[1].equalsIgnoreCase("lobby")) {
                        if (!p.hasPermission("dungeon.admin"))  {
                            Utils.noPerms(sender);
                            return true;
                        }
                        main.getConfig().set("spawn", p.getLocation());
                        main.saveConfig();
                        p.sendMessage(Utils.color("&aLobby set to your location."));
                        return true;
                    }
                }
                sender.sendMessage(Utils.color("&c/dungeon config <lobby>"));
                return true;
            }
            if (args.length == 3) {
                if (args[0].equalsIgnoreCase("config")) {
                    if (!p.hasPermission("dungeon.admin")) {
                        Utils.noPerms(p);
                        return true;
                    }
                    ConfigurationSection section = null;
                    for (String s : main.getConfig().getConfigurationSection("dungeons").getKeys(false)) {
                        if (s.equalsIgnoreCase(args[1])) {
                            section = main.getConfig().getConfigurationSection("dungeons").getConfigurationSection(s);
                        }
                    }
                    if (section == null) {
                        sender.sendMessage(Utils.color("&c" + args[1] +" is not recognised as a dungeon name."));
                        return true;
                    }
                    Location loc = p.getLocation();
                    // config <dungeon> spawn
                    if (args[2].equalsIgnoreCase("spawn")) {
                        ConfigurationSection s = section.getConfigurationSection("spawn");
                        s.set("x", loc.getX());
                        s.set("y",loc.getY());
                        s.set("z",loc.getZ());
                        s.set("yaw",loc.getYaw());
                        s.set("pitch",loc.getPitch());
                        sender.sendMessage(Utils.color("&aSuccessfully set the spawn to your location"));
                        main.saveConfig();
                        return true;
                    }
                    // config <dungeon> boss
                    if (args[2].equalsIgnoreCase("boss")) {
                        ConfigurationSection s = section.getConfigurationSection("bossSpawn");
                        s.set("x",loc.getX());
                        s.set("y",loc.getY());
                        s.set("z",loc.getZ());
                        sender.sendMessage(Utils.color("&aSuccessfully set boss spawn to your location"));
                        main.saveConfig();
                        return true;
                    }
                    // config <dungeon> mobs
                    if (args[2].equalsIgnoreCase("mobs")) {
                        int size = section.getConfigurationSection("mobSpawns").getKeys(false).size()+1;
                        ConfigurationSection s = section.getConfigurationSection("mobSpawns").createSection("loc" + size);
                        s.set("x",loc.getX());
                        s.set("y",loc.getY());
                        s.set("z",loc.getZ());
                        sender.sendMessage(Utils.color("&aSuccessfully added your location to mob spawns."));
                        main.saveConfig();
                        return true;
                    }
                    sender.sendMessage(Utils.color("&cUnrecognised command. Check your args."));
                }
            }
        }
        return true;
    }
}
