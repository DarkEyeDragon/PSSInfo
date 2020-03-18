package me.darkeyedragon.pssinfo.command;

import me.darkeyedragon.pssinfo.PssInfo;
import me.darkeyedragon.pssinfo.gui.GuiManager;
import me.darkeyedragon.pssinfo.shop.ShopItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class ShopSearchCommand implements CommandExecutor {

    private PssInfo plugin;

    public ShopSearchCommand(PssInfo plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) return false;
        if (args.length != 1) return false;
        Material searchMaterial = Material.matchMaterial(args[0]);
        if (searchMaterial == null) {
            sender.sendMessage(ChatColor.RED + "Not a valid item!");
            return true;
        }

        List<ShopItem> result = plugin.getShopItemMap().values().stream().filter(shopItem1 -> shopItem1.getItemStack().getType() == searchMaterial).collect(Collectors.toList());
        GuiManager guiManager = new GuiManager(plugin);
        guiManager.populate(result);
        guiManager.show((Player) sender);

        return true;
    }
}
