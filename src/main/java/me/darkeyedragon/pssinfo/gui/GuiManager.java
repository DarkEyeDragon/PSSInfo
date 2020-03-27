package me.darkeyedragon.pssinfo.gui;

import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.MasonryPane;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import me.darkeyedragon.pssinfo.PssInfo;
import me.darkeyedragon.pssinfo.config.ConfigHandler;
import me.darkeyedragon.pssinfo.shop.ShopItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GuiManager {
    private final int MAX_HEIGHT = 6;
    private final int MAX_LENGTH = 9;
    private ConfigHandler configHandler;
    private PssInfo plugin;
    private PaginatedPane paginatedPane;
    private StaticPane navPane;
    private MasonryPane masonryPane;


    public GuiManager(PssInfo plugin) {
        this.configHandler = plugin.getConfigHandler();
        this.plugin = plugin;
    }

    public void populate(Collection<ShopItem> shopItems) {
        List<GuiItem> guiItems = new ArrayList<>(shopItems.size());

        for (ShopItem shopItem : shopItems) {
            ItemStack itemStack = shopItem.getItemStack().clone();
            ItemMeta itemMeta = itemStack.getItemMeta();
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GOLD + "Price: " + shopItem.getPrice());
            lore.add(ChatColor.GOLD + "World: " + shopItem.getLocation().getWorld().getName());
            lore.add(ChatColor.GOLD + "Location: X:" + shopItem.getLocation().getBlockX() + " Y:" + shopItem.getLocation().getBlockY() + " Z:" + shopItem.getLocation().getBlockZ());
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
            GuiItem guiItem = new GuiItem(itemStack, inventoryClickEvent -> {
                try {
                    Player player = (Player) inventoryClickEvent.getWhoClicked();
                    if (shopItem.getLocation().getWorld().equals(player.getWorld())) {
                        plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), configHandler.getCommandToExecute(inventoryClickEvent.getWhoClicked().getName(), shopItem.getLocation()));
                    } else {
                        player.sendMessage(configHandler.getDiffWorldTeleportMessage());
                    }
                } catch (InvalidConfigurationException e) {
                    e.printStackTrace();
                }
                inventoryClickEvent.setCancelled(true);
            });
            guiItems.add(guiItem);
        }

        navPane = new StaticPane(0, 6, 9, 1);
        ItemStack next = new ItemStack(Material.GREEN_WOOL);
        ItemMeta nextItemMeta = next.getItemMeta();
        nextItemMeta.setDisplayName(ChatColor.GREEN + "Next page >");
        next.setItemMeta(nextItemMeta);
        ItemStack previous = new ItemStack(Material.RED_WOOL);
        ItemMeta previousItemMeta = next.getItemMeta();
        previousItemMeta.setDisplayName(ChatColor.RED + "< Previous page");
        previous.setItemMeta(previousItemMeta);
        navPane.addItem(new GuiItem(next, inventoryClickEvent -> {
            if (paginatedPane.getPage() + 1 < paginatedPane.getPages()) {
                show(inventoryClickEvent.getWhoClicked(), paginatedPane.getPage() + 1);
            }
            inventoryClickEvent.setCancelled(true);
        }), 8, 0);
        navPane.addItem(new GuiItem(previous, inventoryClickEvent -> {
            if (paginatedPane.getPage() > 0) {
                show(inventoryClickEvent.getWhoClicked(), paginatedPane.getPage() - 1);
            }
            inventoryClickEvent.setCancelled(true);
        }), 0, 0);
        masonryPane = new MasonryPane(0, 0, MAX_LENGTH, MAX_HEIGHT);
        paginatedPane = new PaginatedPane(0, 0, MAX_LENGTH, MAX_HEIGHT - 1);
        paginatedPane.populateWithGuiItems(guiItems);
        masonryPane.addPane(paginatedPane);
        masonryPane.addPane(navPane);
    }

    public void show(HumanEntity humanEntity, int page) {
        Gui gui = new Gui(plugin, MAX_HEIGHT, "Shop Information");
        paginatedPane.setPage(page);
        gui.addPane(masonryPane);
        gui.show(humanEntity);
    }
}
