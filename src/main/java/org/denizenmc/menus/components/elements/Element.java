package org.denizenmc.menus.components.elements;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.denizenmc.menus.components.Session;
import org.denizenmc.menus.components.actions.Action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Element {
    private Action action;
    private ItemStack item;
    private String name;
    private List<String> description;
    private int refreshRateTicks;

    public Element(int refreshRateTicks) {
        this.refreshRateTicks = refreshRateTicks;
        action = null;
        item = new ItemStack(Material.BARRIER);
        name = "&bDefault Element";
        description = new ArrayList<>(Arrays.asList("&fThis is a default", "&fdescription."));
    }

    public Action getAction() { return action; }
    public int getRefreshRateTicks() { return refreshRateTicks; }
    public ItemStack build(Session session, int count) {
        if (action != null && action.getDynamicIcon(session, count) != null) {
            return action.getDynamicIcon(session, count);
        }
        ItemStack i = new ItemStack(item);
        ItemMeta meta = i.getItemMeta();
        if (meta == null) return i;
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        List<String> lore = i.hasItemMeta() && i.getItemMeta().getLore() != null ?
                new ArrayList<>(i.getItemMeta().getLore()) : new ArrayList<>();
        for (String line : description) lore.add(ChatColor.translateAlternateColorCodes('&', line));
        meta.setLore(lore);
        i.setItemMeta(meta);
        return i;
    }
}
