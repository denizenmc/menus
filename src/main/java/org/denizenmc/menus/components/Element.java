package org.denizenmc.menus.components;

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

    public Element() {
        action = null;
        item = new ItemStack(Material.BARRIER);
        name = "&bDefault Element";
        description = new ArrayList<>(Arrays.asList("&fThis is a default", "&fdescription."));
    }

    public Element(Action action, ItemStack item, String name, List<String> description) {
        this.action = action;
        this.item = item;
        this.name = name;
        this.description = description;
    }

    public Action getAction() { return action; }
    public ItemStack getItem() { return item; }
    public String getName() { return name; }
    public List<String> getDescription() { return description; }

    public ItemStack build(Session session, int count) {
        if (action != null && action.isDynamicIcon()) {
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
