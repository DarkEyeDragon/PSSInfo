package me.darkeyedragon.pssinfo.config;

import me.darkeyedragon.pssinfo.PssInfo;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;

/**
 * The Config handler class that gets all the require values from config.yml
 */
public class ConfigHandler {

    private final PssInfo plugin;

    /**
     * @param plugin the {@link PssInfo} instance
     */
    public ConfigHandler(PssInfo plugin) {
        this.plugin = plugin;
    }

    /**
     * @param playerName The name of the player you want it to display
     * @param location   the {@link Location} you want to pass along. Required when teleporting the player for example
     * @return a command {@link String} to execute on the console when a {@link org.bukkit.entity.Player} clicks an item in the shop GUI
     * @throws InvalidConfigurationException when the "inventory.click_command" path can't be found
     */
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

    /**
     * @return the message that displays when the player tries to teleport to a different world
     * @throws InvalidConfigurationException when the "message.different_world" path can't be found
     */
    public String getDiffWorldTeleportMessage() throws InvalidConfigurationException {
        String message = plugin.getConfig().getString("message.different_world");
        if (message != null) {
            message = ChatColor.translateAlternateColorCodes('&', message);
            return message;
        } else {
            throw new InvalidConfigurationException();
        }
    }

    /**
     * @return the interval the shops.json gets saved by
     * @throws InvalidConfigurationException when "save-interval" path can't be found or when save-interval is -1
     */
    public long getSaveInterval() throws InvalidConfigurationException {
        long interval = plugin.getConfig().getLong("save-interval", -1);
        if (interval > 0) {
            return interval;
        } else {
            throw new InvalidConfigurationException();
        }
    }
}
