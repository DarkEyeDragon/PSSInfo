package me.darkeyedragon.pssinfo.gui;

import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import me.darkeyedragon.pssinfo.PssInfo;
import me.darkeyedragon.pssinfo.config.ConfigHandler;
import me.darkeyedragon.pssinfo.shop.ShopItem;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.HumanEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GuiManager {
    private final int HEIGHT = 6;
    private final int LENGTH = 9;
    private ConfigHandler configHandler;
    private PssInfo plugin;
    private PaginatedPane paginatedPane;

    public GuiManager(PssInfo plugin) {
        this.configHandler = plugin.getConfigHandler();
        this.plugin = plugin;
    }

    public void populate(Collection<ShopItem> shopItems) {
        List<GuiItem> guiItems = new ArrayList<>(shopItems.size());

        shopItems.forEach(shopItem -> guiItems.add(new GuiItem(shopItem.getItemStack(), inventoryClickEvent -> {
            try {
                plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), configHandler.getCommandToExecute(inventoryClickEvent.getWhoClicked().getName(), shopItem.getLocation()));
            } catch (InvalidConfigurationException e) {
                e.printStackTrace();
            }
            inventoryClickEvent.setCancelled(true);

        })));
        paginatedPane = new PaginatedPane(0, 0, LENGTH, HEIGHT);
        paginatedPane.populateWithGuiItems(guiItems);
    }

    public void show(HumanEntity humanEntity) {
        Gui gui = new Gui(plugin, 6, "Shop Information");
        gui.addPane(paginatedPane);
        gui.show(humanEntity);
    }
}
