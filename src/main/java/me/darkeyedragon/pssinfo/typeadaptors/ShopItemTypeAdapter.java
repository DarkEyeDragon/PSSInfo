package me.darkeyedragon.pssinfo.typeadaptors;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import me.darkeyedragon.pssinfo.shop.ShopItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public class ShopItemTypeAdapter extends TypeAdapter<ShopItem> {
    @Override
    public void write(JsonWriter writer, ShopItem value) throws IOException {
        writer.beginObject();
        writer.name("price");
        writer.value(value.getPrice());
        writer.name("amount");
        writer.value(value.getItemStack().getAmount());
        writer.name("location");
        writer.beginObject();
        writer.name("world");
        writer.value(value.getLocation().getWorld().getName());
        writer.name("x");
        writer.value(value.getLocation().getBlockX());
        writer.name("y");
        writer.value(value.getLocation().getBlockY());
        writer.name("z");
        writer.value(value.getLocation().getBlockZ());
        writer.endObject();
        writer.endObject();
    }

    @Override
    public ShopItem read(JsonReader reader) throws IOException {
        reader.beginObject();

        String fieldname = null;

        //Location data
        double x = 0;
        double y = 0;
        double z = 0;
        String worldName = "";

        //Shop item data
        double price = 0;
        int amount = 0;
        Material material = Material.AIR;

        while (reader.hasNext()) {
            JsonToken token = reader.peek();

            if (token.equals(JsonToken.NAME)) {
                //get the current token
                fieldname = reader.nextName();
            }

            /*if ("price".equals(fieldname)) {
                //move to next token
                token = reader.peek();
                amount = reader.nextInt();
            }

            if ("amount".equals(fieldname)) {
                //move to next token
                token = reader.peek();
                amount = reader.nextInt();
            }
            if ("material".equals(fieldname)) {
                //move to next token
                token = reader.peek();
                material = Material.getMaterial(reader.nextString(), false);
            }*/

            if ("x".equals(fieldname)) {
                //move to next token
                token = reader.peek();
                x = reader.nextDouble();
            }

            if ("y".equals(fieldname)) {
                //move to next token
                token = reader.peek();
                y = reader.nextDouble();
            }
            if ("z".equals(fieldname)) {
                //move to next token
                token = reader.peek();
                z = reader.nextDouble();
            }
            if ("name".equals(fieldname)) {
                //move to next token
                token = reader.peek();

                worldName = reader.nextString();
            }
        }

        reader.endObject();
        Location location = new Location(Bukkit.getWorld(worldName), x, y, z);
        return new ShopItem(location, new ItemStack(material, amount), price);
    }
}
