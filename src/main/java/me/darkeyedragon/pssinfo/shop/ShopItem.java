package me.darkeyedragon.pssinfo.shop;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class ShopItem {
    private final ItemStack placeholderStack;
    private final double price;
    private final Location location;
    private final int itemAmount;

    public ShopItem(Location location, ItemStack placeholderStack, double price, int itemAmount) {
        this.placeholderStack = placeholderStack;
        this.price = price;
        this.location = location;
        this.itemAmount = itemAmount;
    }

    public ItemStack getPlaceholderStack() {
        return placeholderStack;
    }

    public double getPrice() {
        return price;
    }

    public Location getLocation() {
        return location;
    }

    public int getItemAmount() {
        return itemAmount;
    }
}
