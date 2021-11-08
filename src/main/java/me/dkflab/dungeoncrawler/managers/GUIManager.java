package me.dkflab.dungeoncrawler.managers;

import me.dkflab.dungeoncrawler.DungeonCrawler;
import me.dkflab.dungeoncrawler.gui.ClassSelect;
import me.dkflab.dungeoncrawler.gui.DungeonSelect;
import me.dkflab.dungeoncrawler.gui.ShopScreen;
import me.dkflab.dungeoncrawler.gui.UpgradeScreen;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

public class GUIManager {

    private DungeonCrawler main;
    public GUIManager(DungeonCrawler main) {
        this.main = main;
        init();
    }

    public DungeonSelect dungeonSelect;
    public ClassSelect classSelect;
    public UpgradeScreen upgradeScreen;
    public ShopScreen shopScreen;

    private void init() {
        dungeonSelect = new DungeonSelect(main);
        classSelect = new ClassSelect(main);
        upgradeScreen = new UpgradeScreen(main);
        shopScreen = new ShopScreen(main);
    }

    public void event(InventoryClickEvent e) {
        if (e.getClickedInventory() != null) {
            InventoryHolder i = e.getClickedInventory().getHolder();
            if (i instanceof DungeonSelect) {
                e.setCancelled(true);
                dungeonSelect.listener(e);
            }
            if (i instanceof ClassSelect) {
                e.setCancelled(true);
                classSelect.listener(e);
            }
            if (i instanceof UpgradeScreen) {
                e.setCancelled(true);
                upgradeScreen.listener(e);
            }
            if (i instanceof ShopScreen) {
                e.setCancelled(true);
                shopScreen.listener(e);
            }
        }
    }
}
