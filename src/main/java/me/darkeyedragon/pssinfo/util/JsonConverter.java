package me.darkeyedragon.pssinfo.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import me.darkeyedragon.pssinfo.shop.ShopItem;
import me.darkeyedragon.pssinfo.typeadaptors.JsonItemStackBase64Adapter;
import me.darkeyedragon.pssinfo.typeadaptors.ShopItemTypeAdapter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;

public class JsonConverter {

    private static Gson gson;

    static {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(ShopItem.class, new ShopItemTypeAdapter())
                .registerTypeAdapter(ItemStack.class, new JsonItemStackBase64Adapter())
                .create();
    }

    public static void writeShopItemToJson(Collection<ShopItem> collection, Plugin plugin) throws IOException {
        File file = new File(plugin.getDataFolder(), "shops.json");
        System.out.println(file.getAbsolutePath());
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(collection, writer);
        }
    }

    public static Collection<ShopItem> jsonToShopItem(Plugin plugin) {
        Type setType = new TypeToken<Collection<ShopItem>>() {
        }.getType();
        return gson.fromJson(new File(plugin.getDataFolder(), "shops.json").getPath(), setType);
    }
}