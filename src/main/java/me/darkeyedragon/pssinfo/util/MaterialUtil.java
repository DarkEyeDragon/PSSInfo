package me.darkeyedragon.pssinfo.util;

import org.bukkit.Material;

public class MaterialUtil {

    public static boolean isMaterial(String materialName) {
        return Material.matchMaterial(materialName) != null;
    }
}
