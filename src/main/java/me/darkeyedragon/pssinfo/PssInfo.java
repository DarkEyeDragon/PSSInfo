package me.darkeyedragon.pssinfo;

import com.robomwm.prettysimpleshop.PrettySimpleShop;
import com.robomwm.prettysimpleshop.shop.ShopAPI;
import me.darkeyedragon.pssinfo.command.ShopSearchCommand;
import me.darkeyedragon.pssinfo.command.ShopSearchTabCompleter;
import me.darkeyedragon.pssinfo.config.ConfigHandler;
import me.darkeyedragon.pssinfo.listener.ShopDestroyListener;
import me.darkeyedragon.pssinfo.listener.ShopOpenCloseListener;
import me.darkeyedragon.pssinfo.shop.ShopItem;
import me.darkeyedragon.pssinfo.util.JsonConverter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class PssInfo extends JavaPlugin {

    private ShopAPI shopAPI;
    private Map<Location, ShopItem> shopItemMap;
    private ConfigHandler configHandler;

    @Override
    public void onEnable() {
        // Plugin startup logic

        //Get the api class
        Plugin prettySimpleShopInstance = Bukkit.getPluginManager().getPlugin("PrettySimpleShop");
        if (prettySimpleShopInstance instanceof PrettySimpleShop) {
            shopAPI = ((PrettySimpleShop) prettySimpleShopInstance).getShopAPI();
        }
        shopItemMap = new HashMap<>();
        saveDefaultConfig();
        File file = new File(this.getDataFolder(), "shops.json");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        getCommand("shopsearch").setExecutor(new ShopSearchCommand(this));
        getCommand("shopsearch").setTabCompleter(new ShopSearchTabCompleter());

        configHandler = new ConfigHandler(this);

        getServer().getPluginManager().registerEvents(new ShopOpenCloseListener(this), this);
        getServer().getPluginManager().registerEvents(new ShopDestroyListener(this), this);

        List<ShopItem> collection = JsonConverter.jsonToShopItem(this);
        collection.forEach(shopItem -> shopItemMap.put(shopItem.getLocation(), shopItem));

        //Schedule a save every 3 minutes (3*60*20) after 3 minutes
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, () -> {
            try {
                getLogger().info(ChatColor.GRAY + "Saving shops.json...");
                JsonConverter.writeShopItemToJson(new ArrayList<>(shopItemMap.values()), this);
                getLogger().info(ChatColor.GRAY + "Successfully saved!");
            } catch (IOException e) {
                e.printStackTrace();
                getLogger().warning("Could not save to shops.json");
            }
        }, getConfigHandler().getSaveInterval(), getConfigHandler().getSaveInterval());
    }

    public ShopAPI getShopAPI() {
        return shopAPI;
    }

    @Override
    public void onDisable() {
        try {
            JsonConverter.writeShopItemToJson(new ArrayList<>(shopItemMap.values()), this);
        } catch (IOException e) {
            e.printStackTrace();
            getLogger().severe("Unable to save shop information!!");
        }
    }

    public Map<Location, ShopItem> getShopItemMap() {
        return shopItemMap;
    }

    public ConfigHandler getConfigHandler() {
        return configHandler;
    }
}
