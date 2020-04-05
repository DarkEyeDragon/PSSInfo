package me.darkeyedragon.pssinfo.util;

import com.robomwm.prettysimpleshop.shop.ShopAPI;
import me.darkeyedragon.pssinfo.PssInfo;
import me.darkeyedragon.pssinfo.shop.ShopItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Container;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class Updater implements Update {

    private ShopAPI shopAPI;
    private PssInfo pssInfo;

    public Updater(PssInfo pssInfo) {
        this.pssInfo = pssInfo;
        this.shopAPI = pssInfo.getShopAPI();

    }

    @Override
    public Map<Location, ShopItem> execute(Collection<ShopItem> shopItems) throws IOException {
        Map<Location, ShopItem> shopItemMap = shopItems.stream().map(shopItem -> {
            Container container = shopAPI.getContainer(shopItem.getLocation());
            int totalAmount = 0;
            ItemStack[] itemStacks = container.getInventory().getContents();
            for (ItemStack itemStack : itemStacks) {
                if (itemStack != null && itemStack.getType() != Material.AIR) {
                    totalAmount += itemStack.getAmount();
                }
            }
            return new ShopItem(shopItem.getLocation(), shopItem.getPlaceholderStack(), shopItem.getPrice(), totalAmount);
        }).collect(Collectors.toMap(ShopItem::getLocation, shopItem -> shopItem));

        JsonConverter.writeShopItemToJson(new ArrayList<>(shopItemMap.values()), pssInfo);
        return shopItemMap;
    }
}
