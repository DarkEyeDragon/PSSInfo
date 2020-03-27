package me.darkeyedragon.pssinfo.listener;

import com.robomwm.prettysimpleshop.event.ShopOpenCloseEvent;
import com.robomwm.prettysimpleshop.shop.ShopAPI;
import me.darkeyedragon.pssinfo.PssInfo;
import me.darkeyedragon.pssinfo.shop.ShopItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Container;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ShopOpenCloseListener implements Listener {

    private final ShopAPI shopAPI;
    private final PssInfo plugin;

    public ShopOpenCloseListener(PssInfo plugin) {
        this.shopAPI = plugin.getShopAPI();
        this.plugin = plugin;
    }

    @EventHandler
    public void onShopOpenEvent(ShopOpenCloseEvent event) {
        if (event.isOpen() || event.getShopInfo().getItem() == null || event.getShopInfo().getItem().getType() == Material.AIR)
            return;
        Container container = shopAPI.getContainer(event.getShopInfo().getLocation());
        Location location = shopAPI.getLocation(container);
        ShopItem newShopItem = new ShopItem(location, shopAPI.getItemStack(container), shopAPI.getPrice(container));
        plugin.getShopItemMap().put(newShopItem.getLocation(), newShopItem);
    }

}
