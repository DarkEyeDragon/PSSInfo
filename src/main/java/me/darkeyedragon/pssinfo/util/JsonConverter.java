package me.darkeyedragon.pssinfo.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import me.darkeyedragon.pssinfo.shop.ShopItem;
import me.darkeyedragon.pssinfo.typeadaptor.JsonItemStackBase64Adapter;
import me.darkeyedragon.pssinfo.typeadaptor.ShopItemTypeAdapter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Stream;

public class JsonConverter {

    private static Gson gson;

    static {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(ShopItem.class, new ShopItemTypeAdapter())
                .registerTypeHierarchyAdapter(ItemStack.class, new JsonItemStackBase64Adapter())
                .create();
    }

    public static void writeShopItemToJson(List<ShopItem> collection, Plugin plugin) throws IOException {
        File file = new File(plugin.getDataFolder(), "shops.json");
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(collection, writer);
        }
    }

    public static List<ShopItem> jsonToShopItem(Plugin plugin) {
        Type setType = new TypeToken<List<ShopItem>>() {
        }.getType();
        StringBuilder contentBuilder = new StringBuilder();
        File file = new File(plugin.getDataFolder(), "shops.json");
        try (Stream<String> stream = Files.lines(file.toPath(), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gson.fromJson(contentBuilder.toString(), setType);
    }
}