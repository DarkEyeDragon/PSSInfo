package me.darkeyedragon.pssinfo.shop;

import me.darkeyedragon.pssinfo.PssInfo;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class ShopItem {
    private final ItemStack itemStack;
    private final double price;
    private final Location location;
    private final UUID uuid;

    public ShopItem(Location location, ItemStack itemStack, double price) {
        this.itemStack = itemStack;
        this.price = price;
        this.location = location;
        this.uuid = UUID.randomUUID();
        //addPersistentData();
    }

    public static ShopItem getShopItem(ItemStack itemStack, PssInfo plugin) {
        NamespacedKey key = new NamespacedKey(plugin, "uuid");
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) return null;
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        if (container.has(key, PersistentDataType.DOUBLE)) {

            long[] foundValue = container.get(key, PersistentDataType.LONG_ARRAY);
            if (foundValue != null && foundValue.length == 2) {
                UUID result = new UUID(foundValue[0], foundValue[1]);
                return plugin.getShopItemMap().get(result);
            }
        }
        return null;
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

    public UUID getUuid() {
        return uuid;
    }

    private void addPersistentData() {
        NamespacedKey key = new NamespacedKey(Bukkit.getPluginManager().getPlugin("PssInfo"), "uuid");
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) throw new NullPointerException("Item meta is null");
        long uuid1 = uuid.getMostSignificantBits();
        long uuid2 = uuid.getLeastSignificantBits();
        long[] uuid = {uuid1, uuid2};
        itemMeta.getPersistentDataContainer().set(key, PersistentDataType.LONG_ARRAY, uuid);
        itemStack.setItemMeta(itemMeta);
    }
}
