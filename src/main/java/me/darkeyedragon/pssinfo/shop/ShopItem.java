package me.darkeyedragon.pssinfo.shop;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class ShopItem {
    private final ItemStack itemStack;
    private final double price;
    private final Location location;

    public ShopItem(Location location, ItemStack itemStack, double price) {
        this.itemStack = itemStack;
        this.price = price;
        this.location = location;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public double getPrice() {
        return price;
    }

    public Location getLocation() {
        return location;
    }
}
