package me.darkeyedragon.pssinfo.util;

import me.darkeyedragon.pssinfo.shop.ShopItem;
import org.bukkit.Location;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public interface Update {
    Map<Location, ShopItem> execute(Collection<ShopItem> shopItems) throws IOException;
}
