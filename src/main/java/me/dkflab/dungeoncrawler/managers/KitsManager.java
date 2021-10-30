package me.dkflab.dungeoncrawler.managers;

import me.dkflab.dungeoncrawler.DungeonCrawler;
import me.dkflab.dungeoncrawler.objects.Kit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static me.dkflab.dungeoncrawler.Utils.createItem;
import static me.dkflab.dungeoncrawler.Utils.setArmorColor;

public class KitsManager {

    private DungeonCrawler main;
    public KitsManager(DungeonCrawler m) {
        main = m;
        init();
    }

    public Kit healer;
    public Kit tank;

    public void init() {
        createHealer();
        createTank();
    }

    private void createHealer() {
        List<ItemStack> items = new ArrayList<>();
        List<ItemStack> armor = new ArrayList<>();
        List<String> lore = new ArrayList<>();
        List<ItemFlag> flags = new ArrayList<>();
        HashMap<Enchantment, Integer> enchants = new HashMap<>();

        // Rod
        lore.add("&7Shiny but causes");
        lore.add("&7low damage.");
        enchants.put(Enchantment.DAMAGE_ALL, 10);
        flags.add(ItemFlag.HIDE_UNBREAKABLE);
        flags.add(ItemFlag.HIDE_ENCHANTS);
        items.add(createItem(Material.BLAZE_ROD, 1, "&6&lStaff of The Order", lore, enchants, flags, true));

        // Book
        lore.clear();
        lore.add("&7Heals allies in a radius of");
        lore.add("&73 blocks for 2 hearts.");
        lore.add("&r");
        lore.add("&87 second cooldown.");
        ItemStack ability = createItem(Material.ENCHANTED_BOOK, 1, "&dHealing Tier 1", lore, null, flags,false);

        // Armor
        flags.add(ItemFlag.HIDE_DYE);
        armor.add(setArmorColor(createItem(Material.LEATHER_CHESTPLATE, 1, "&fChest of The Scholar", null, null, flags, true), Color.WHITE));
        armor.add(setArmorColor(createItem(Material.LEATHER_LEGGINGS, 1, "&fLeggings of The Scholar", null, null, flags, true), Color.WHITE));
        armor.add(setArmorColor(createItem(Material.LEATHER_BOOTS, 1, "&fBoots of The Scholar", null, null, flags, true), Color.WHITE));

        this.healer = new Kit("healer", items, armor, ability);
    }

    public Kit getHealer() {
        if (healer == null) {
            createHealer();
        }
        return this.healer;
    }

    private void createTank() {
        List<ItemStack> items = new ArrayList<>();
        List<ItemStack> armor = new ArrayList<>();
        List<String> lore = new ArrayList<>();
        List<ItemFlag> flags = new ArrayList<>();
        HashMap<Enchantment, Integer> enchants = new HashMap<>();

        flags.add(ItemFlag.HIDE_UNBREAKABLE);
        flags.add(ItemFlag.HIDE_ENCHANTS);
        // Sword
        enchants.put(Enchantment.DAMAGE_ALL, 3);
        lore.add("&7A powerful sword with");
        lore.add("&7mysterious markings.");
        items.add(createItem(Material.STONE_SWORD, 1, "&c&lSword of Power", lore, enchants, flags,true));

        // Ability
        lore.clear();
        lore.add("&7Give yourself strength");
        lore.add("&7for 10 seconds.");
        lore.add("&r");
        lore.add("&830 second cooldown.");
        ItemStack ability = createItem(Material.ENCHANTED_BOOK, 1, "&dFury Tier 1", lore, null, flags, false);

        // Armor
        armor.add(createItem(Material.CHAINMAIL_CHESTPLATE, 1, "&7Chest of Valor", null, null, flags, true));
        armor.add(createItem(Material.CHAINMAIL_LEGGINGS, 1, "&7Leggings of Valor", null, null, flags, true));
        armor.add(createItem(Material.CHAINMAIL_BOOTS, 1, "&7Boots of Valor", null, null, flags, true));

        this.tank = new Kit("tank", items,armor,ability);
    }

    public Kit getTank() {
        if (tank == null) {
            createTank();
        }
        return this.tank;
    }

}
