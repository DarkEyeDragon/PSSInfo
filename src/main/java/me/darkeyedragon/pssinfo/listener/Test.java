package me.darkeyedragon.pssinfo.listener;

import me.darkeyedragon.pssinfo.PssInfo;
import me.darkeyedragon.pssinfo.util.JsonConverter;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.io.IOException;

public class Test implements Listener {

    PssInfo plugin;

    public Test(PssInfo plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_AIR) {
            try {
                JsonConverter.writeShopItemToJson(plugin.getShopItemMap().values(), plugin);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
