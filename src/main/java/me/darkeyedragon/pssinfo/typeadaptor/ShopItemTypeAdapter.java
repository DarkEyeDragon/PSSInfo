package me.darkeyedragon.pssinfo.typeadaptor;

import com.google.gson.*;
import me.darkeyedragon.pssinfo.shop.ShopItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Type;

public class ShopItemTypeAdapter implements JsonSerializer<ShopItem>, JsonDeserializer<ShopItem> {

    @Override
    public ShopItem deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        ItemStack itemStack = context.deserialize(object.get("item"), ItemStack.class);
        double price = object.get("price").getAsDouble();
        int itemAmount = -1;
        try {
            itemAmount = object.get("item_amount").getAsInt();
        } catch (NullPointerException ex) {
            Bukkit.getLogger().warning("Unable to get Item Amount of shop item.");
        }
        JsonObject location = object.get("location").getAsJsonObject();
        double x = location.get("x").getAsDouble();
        double y = location.get("y").getAsDouble();
        double z = location.get("z").getAsDouble();
        String world = location.get("world").getAsString();

        return new ShopItem(new Location(Bukkit.getWorld(world), x, y, z), itemStack, price, itemAmount);
    }

    @Override
    public JsonElement serialize(ShopItem src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        JsonObject location = new JsonObject();
        ItemStack itemStack = src.getPlaceholderStack();
        double price = src.getPrice();
        object.addProperty("price", price);
        object.addProperty("item_amount", src.getItemAmount());
        location.addProperty("world", src.getLocation().getWorld().getName());
        location.addProperty("x", src.getLocation().getBlockX());
        location.addProperty("y", src.getLocation().getBlockY());
        location.addProperty("z", src.getLocation().getBlockZ());
        object.add("location", location);
        object.add("item", context.serialize(itemStack));
        return object;
    }
}
