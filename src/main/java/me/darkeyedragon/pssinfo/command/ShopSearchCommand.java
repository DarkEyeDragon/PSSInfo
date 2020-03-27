package me.darkeyedragon.pssinfo.command;

import me.darkeyedragon.pssinfo.PssInfo;
import me.darkeyedragon.pssinfo.enums.Sort;
import me.darkeyedragon.pssinfo.gui.GuiManager;
import me.darkeyedragon.pssinfo.shop.ShopItem;
import me.darkeyedragon.pssinfo.util.MaterialUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ShopSearchCommand implements CommandExecutor {

    private PssInfo plugin;

    public ShopSearchCommand(PssInfo plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.hasPermission("pssinfo.search")) return true;
        //Check if an actual player
        if (!(sender instanceof Player)) return false;
        List<ShopItem> result;
        Sort sorter;
        if (args.length > 0) {
            //Check if the material exists
            if (MaterialUtil.isMaterial(args[0])) {
                Material searchMaterial = Material.matchMaterial(args[0].toUpperCase());

                //Filter out the materials that match the search
                result = plugin.getShopItemMap().values().stream().filter(shopItem1 -> shopItem1.getItemStack().getType() == searchMaterial).collect(Collectors.toList());
                if (args.length > 1) {
                    //Sort the list based on the sorter, if it exists
                    try {
                        sorter = Sort.valueOf(args[1].toUpperCase());
                        result = sort(result, sorter);
                    } catch (IllegalArgumentException ex) {
                        sender.sendMessage(ChatColor.RED + "Please provide a valid sorting argument");
                        return true;
                    }
                } else {
                    result = sort(result, Sort.PRICE);
                }
            } else {
                //Show all items
                result = new ArrayList<>(plugin.getShopItemMap().values());
                //Sort the list if there is a valid sorter, otherwise invalid command
                try {
                    sorter = Sort.valueOf(args[0].toUpperCase());
                    result = sort(result, sorter);
                } catch (IllegalArgumentException ignored) {
                    return false;
                }
            }
        } else {
            result = new ArrayList<>(plugin.getShopItemMap().values());
            result = sort(result, Sort.NAME);
        }
        if (result != null) {
            GuiManager guiManager = new GuiManager(plugin);
            guiManager.populate(result);
            guiManager.show((Player) sender, 0);
            return true;
        }
        return false;
    }

    private List<ShopItem> sort(List<ShopItem> shopItems, Sort sort) {
        switch (sort) {
            case NAME:
                return shopItems.stream().sorted(Comparator.comparing(shopItem -> shopItem.getItemStack().getType().name())).collect(Collectors.toList());
            case PRICE:
                return shopItems.stream().sorted(Comparator.comparing(ShopItem::getPrice)).collect(Collectors.toList());
            case WORLD:
                return shopItems.stream().sorted(Comparator.comparing(shopItem -> shopItem.getLocation().getWorld().getName())).collect(Collectors.toList());
            default:
                return null;
        }
    }
}
