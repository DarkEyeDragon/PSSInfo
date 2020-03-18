package me.darkeyedragon.pssinfo;

import com.robomwm.prettysimpleshop.PrettySimpleShop;
import com.robomwm.prettysimpleshop.shop.ShopAPI;
import me.darkeyedragon.pssinfo.command.ShopSearchCommand;
import me.darkeyedragon.pssinfo.command.ShopSearchTabCompleter;
import me.darkeyedragon.pssinfo.config.ConfigHandler;
import me.darkeyedragon.pssinfo.listener.ShopOpenCloseListener;
import me.darkeyedragon.pssinfo.listener.Test;
import me.darkeyedragon.pssinfo.shop.ShopItem;
import me.darkeyedragon.pssinfo.util.JsonConverter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
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
        getServer().getPluginManager().registerEvents(new Test(this), this);
        /*Collection<ShopItem> collection = JsonConverter.jsonToShopItem(this);
        collection.forEach(shopItem -> shopItemMap.put(shopItem.getUuid(), shopItem));

        GuiManager guiManager = new GuiManager(this);
        guiManager.populate(getShopItemMap().values());*/

    }

    public ShopAPI getShopAPI() {
        return shopAPI;
    }

    @Override
    public void onDisable() {
        try {
            JsonConverter.writeShopItemToJson(shopItemMap.values(), this);
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
