package me.darkeyedragon.pssinfo.listener;

import com.robomwm.prettysimpleshop.event.ShopBreakEvent;
import me.darkeyedragon.pssinfo.PssInfo;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ShopDestroyListener implements Listener {

    private final PssInfo plugin;

    public ShopDestroyListener(PssInfo plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onShowDestroy(ShopBreakEvent event) {
        Location loc = event.getShopInfo().getLocation();
        plugin.removeShopItem(loc);
        plugin.getLogger().info("Removed X:" + loc.getBlockX() + " Y:" + loc.getBlockX() + " Z:" + loc.getBlockX());
    }
}
