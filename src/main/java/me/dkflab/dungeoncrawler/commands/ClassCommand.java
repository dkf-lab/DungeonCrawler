package me.dkflab.dungeoncrawler.commands;

import me.dkflab.dungeoncrawler.DungeonCrawler;
import me.dkflab.dungeoncrawler.Utils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

import static me.dkflab.dungeoncrawler.Utils.color;

public class ClassCommand implements CommandExecutor {

    private DungeonCrawler main;
    public ClassCommand(DungeonCrawler main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("class")) {
            if (!(sender instanceof Player)) {
                Utils.notPlayer(sender);
                return true;
            }
            Player p = (Player)sender;
            if (p.hasPermission("dungeon.admin")) {
                if (args.length == 1) {
                    main.classManager.getKitByName(args[0]).giveToPlayer(p,main.upgradeManager);
                }
            } else {
                p.sendMessage(color("&7Use &c/dungeon &7to select a class."));
            }
        }
        if (command.getName().equalsIgnoreCase("createclass")) {
            if (!(sender instanceof Player)) {
                Utils.notPlayer(sender);
                return true;
            }
            Player p = (Player)sender;
            if (args.length == 1) {
                p.sendMessage(color("&bCreate Class Help"));
                p.sendMessage(color("&e/createclass <name> <ability> &7- Run this command to create an entry under the config with an ability."));
                p.sendMessage(color("&e/createclass <name> weapon &7- Sets weapon to item in your hand"));
                p.sendMessage(color("&e/createclass <name> armor &7- Sets armor to whatever you are wearing"));
                p.sendMessage(color("&eYou must configure your ability in abilities.yml!!"));
                return true;
            }
            if (args.length != 2) {
                p.sendMessage(color("&c/createclass help"));
                return true;
            }
            ConfigurationSection config = main.classManager.getConfig().getConfigurationSection("classes");
            if (args[1].equalsIgnoreCase("weapon")) {
                if (parseName(p,args[0])) {
                    ItemStack item = p.getInventory().getItemInMainHand();
                    if (item.getType().equals(Material.AIR)) {
                        p.sendMessage(Utils.color("&7You need to &chold an item&7!"));
                        return true;
                    }
                    config.getConfigurationSection(args[0]).set("weapon",item);
                    main.classManager.saveConfig();
                    p.sendMessage(color("&a&lSuccess!"));
                }
                return true;
            }
            if (args[1].equalsIgnoreCase("armor")) {
                if (parseName(p,args[0])) {
                    PlayerInventory inv = p.getInventory();
                    ItemStack helmet,chestplate,leggings,boots;
                    helmet = inv.getHelmet();
                    chestplate = inv.getChestplate();
                    leggings = inv.getLeggings();
                    boots = inv.getBoots();
                    if (helmet != null) {
                        config.getConfigurationSection(args[0]).set("helmet",helmet);
                    }
                    if (chestplate != null) {
                        config.getConfigurationSection(args[0]).set("chestplate",chestplate);
                    }
                    if (leggings != null) {
                        config.getConfigurationSection(args[0]).set("leggings",leggings);
                    }
                    if (boots != null) {
                        config.getConfigurationSection(args[0]).set("boots",boots);
                    }
                    main.classManager.saveConfig();
                    p.sendMessage(color("&a&lSuccess!"));
                }
                return true;
            }
            // <name> <ability>
            if (main.abilityManager.getAbilityFromName(args[1]) == null) {
                p.sendMessage(color("&c" + args[1] + "&7 is not recognised as an ability. Check your &cabilities.yml &7and try again."));
                p.sendMessage(color("&8&oRemember to restart your server between changes!"));
                return true;
            }
            config.set(args[0] + ".name", args[0]);
            config.set(args[0] + ".ability", args[1]);
            main.classManager.saveConfig();
            p.sendMessage(color("&a&lSuccess!&7 A class has been created under name &a" + args[0]));
            p.sendMessage(color("&8&oRemember to set the rest of the items! Run &c/createclass help"));
        }
        return true;
    }

    private boolean parseName(Player p, String name) {
        if (main.classManager.getConfig().getConfigurationSection("classes").isConfigurationSection(name)) {
            return true;
        }
        p.sendMessage(color("&c" + name + " &7is an unrecognised class!"));
        return false;
    }
}
