package me.darkeyedragon.pssinfo.command;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShopSearchTabCompleter implements TabCompleter {

    private static final List<String> Material_LIST;

    static {
        Material_LIST = new ArrayList<>();
        for (Material material : Material.values()) {
            Material_LIST.add(material.name().toLowerCase());
        }
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("shopsearch") && args.length > 0) {
            if (sender instanceof Player) {
                return Material_LIST.stream().filter(s -> s.contains(args[0])).collect(Collectors.toList());
            }
        }
        return null;
    }


}
