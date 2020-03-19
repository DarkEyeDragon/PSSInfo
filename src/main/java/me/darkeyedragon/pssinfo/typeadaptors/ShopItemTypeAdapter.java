package me.darkeyedragon.pssinfo.typeadaptors;

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
        JsonObject location = object.get("location").getAsJsonObject();
        double x = location.get("x").getAsDouble();
        double y = location.get("y").getAsDouble();
        double z = location.get("z").getAsDouble();
        String world = location.get("world").getAsString();

        return new ShopItem(new Location(Bukkit.getWorld(world), x, y, z), itemStack, price);
    }

    @Override
    public JsonElement serialize(ShopItem src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        JsonObject location = new JsonObject();
        ItemStack itemStack = src.getItemStack();
        double price = src.getPrice();
        object.addProperty("price", price);
        location.addProperty("world", src.getLocation().getWorld().getName());
        location.addProperty("x", src.getLocation().getBlockX());
        location.addProperty("y", src.getLocation().getBlockY());
        location.addProperty("z", src.getLocation().getBlockZ());
        object.add("location", location);
        object.add("item", context.serialize(itemStack));
        return object;
    }
}