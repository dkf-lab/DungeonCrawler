package me.dkflab.dungeoncrawler.commands;

import me.dkflab.dungeoncrawler.DungeonCrawler;
import me.dkflab.dungeoncrawler.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class UpgradeCommand implements CommandExecutor {

    private DungeonCrawler main;
    public UpgradeCommand(DungeonCrawler main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("upgrade")) {
            if (!(sender instanceof Player)) {
                Utils.notPlayer(sender);
                return true;
            }
            Player p = (Player)sender;
            p.openInventory(main.gui.upgradeScreen.getInventory());
        }
        return true;
    }
}
