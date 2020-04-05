package me.darkeyedragon.pssinfo.command;

import me.darkeyedragon.pssinfo.PssInfo;
import me.darkeyedragon.pssinfo.util.Updater;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class UpdateCommand implements CommandExecutor {

    private final PssInfo plugin;

    public UpdateCommand(PssInfo plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.hasPermission("pssinfo.update")) {
            sender.sendMessage(ChatColor.GREEN + "Starting conversion...");
            Updater updater = new Updater(plugin);
            try {
                plugin.populateShopItems(updater.execute(plugin.getShopItemMap().values()));
                sender.sendMessage(ChatColor.GREEN + "Finished conversion successfully!");
            } catch (IOException e) {
                e.printStackTrace();
                sender.sendMessage("Unable to convert file! Check the console for errors!");
            }
        }
        return true;
    }
}
