package me.dkflab.dungeoncrawler;

import me.dkflab.dungeoncrawler.commands.*;
import me.dkflab.dungeoncrawler.listeners.*;
import me.dkflab.dungeoncrawler.managers.*;
import me.dkflab.dungeoncrawler.objects.Dungeon;
import org.bukkit.block.Barrel;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class DungeonCrawler extends JavaPlugin {

    public ClassManager classManager;
    public AbilityManager abilityManager;
    public GUIManager gui;
    public MatchmakingManager mm;
    public DungeonManager dungeonManager;
    public CurrencyManager currencyManager;
    public UpgradeManager upgradeManager;
    public BarrelManager barrelManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        registerListeners();
        registerCommands();
        abilityManager = new AbilityManager(this);
        classManager = new ClassManager(this);
        mm = new MatchmakingManager(this);
        dungeonManager = new DungeonManager(this);
        currencyManager = new CurrencyManager(this);
        upgradeManager = new UpgradeManager(this);
        gui = new GUIManager(this);
        barrelManager = new BarrelManager(this);
        RecipeManager.init();

        BukkitRunnable run = new BukkitRunnable() {
            @Override
            public void run() {
                if (dungeonManager.getActiveDungeons() == null) {
                    return;
                }
                for (Dungeon d : dungeonManager.getActiveDungeons()) {
                    dungeonManager.loop(d);
                }
            }
        };
        run.runTaskTimer(this,20,20);
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new ClickListener(this), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(this),this);
        getServer().getPluginManager().registerEvents(new BlockBreak(this),this);
        getServer().getPluginManager().registerEvents(new EntityDamage(this),this);
        getServer().getPluginManager().registerEvents(new DeathListener(this),this);
        getServer().getPluginManager().registerEvents(new PlayerLeave(this),this);
    }

    private void registerCommands() {
        ClassCommand cc = new ClassCommand(this);
        getCommand("class").setExecutor(cc);
        getCommand("createclass").setExecutor(cc);
        getCommand("dungeon").setExecutor(new DungeonCommand(this));
        getCommand("upgrade").setExecutor(new UpgradeCommand(this));
        getCommand("shop").setExecutor(new ShopCommand(this));
        getCommand("balance").setExecutor(new BalanceCommand(this));
        getCommand("barrel").setExecutor(new BarrelCommand(this));
    }

    public GUIManager getGUI() {
        return this.gui;
    }

    public MatchmakingManager getMM() {
        return mm;
    }
}
