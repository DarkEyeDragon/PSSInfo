package me.darkeyedragon.pssinfo.config;

import me.darkeyedragon.pssinfo.PssInfo;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;

public class ConfigHandler {

    private final PssInfo plugin;

    public ConfigHandler(PssInfo plugin) {
        this.plugin = plugin;
    }

    public String getCommandToExecute(String playerName, Location location) throws InvalidConfigurationException {
        String message = plugin.getConfig().getString("inventory.click_command");
        if (message != null) {
            message = ChatColor.translateAlternateColorCodes('&', message);
            message = message.replace("%player%", playerName);
            message = message.replace("%shoplocation%", location.getBlockX() + " " + location.getBlockY() + " " + location.getBlockZ());
            return message;
        } else {
            throw new InvalidConfigurationException();
        }
    }

    public String getDiffWorldTeleportMessage() throws InvalidConfigurationException {
        String message = plugin.getConfig().getString("message.different_world");
        if (message != null) {
            message = ChatColor.translateAlternateColorCodes('&', message);
            return message;
        } else {
            throw new InvalidConfigurationException();
        }
    }

    public long getSaveInterval() {
        return plugin.getConfig().getLong("save-interval");
    }
}
