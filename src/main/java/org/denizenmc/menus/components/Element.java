package org.denizenmc.menus.components;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.denizenmc.menus.components.Session;
import org.denizenmc.menus.components.actions.Action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Element {
    private List<Action> actions;
    private ItemStack item;
    private String name;
    private List<String> description;

    public Element(ItemStack item) {
        actions = new ArrayList<>();
        this.item = item;
        name = "&b ";
        description = new ArrayList<>();
    }

    public Element(ItemStack item, String name) {
        actions = new ArrayList<>();
        this.item = item;
        this.name = name;
        description = new ArrayList<>();
    }

    public Element(ItemStack item, String name, List<String> description) {
        actions = new ArrayList<>();
        this.item = item;
        this.name = name;
        this.description = description;
    }

    public Element(List<Action> actions, ItemStack item, String name, List<String> description) {
        this.actions = actions;
        this.item = item;
        this.name = name;
        this.description = description;
    }

    public Element addAction(Action a) { actions.add(a); return this; }
    public Element setItem(ItemStack item) { this.item = item; return this; }
    public Element setName(String name) { this.name = name; return this; }

    public List<Action> getActions() { return actions; }
    public ItemStack getItem() { return item; }
    public String getName() { return name; }
    public List<String> getDescription() { return description; }

    public Element copy() {
        return new Element(actions, item, name, description);
    }

    public ItemStack build(Session session, Map<String, Integer> counts) {
        if (!actions.isEmpty()) {
            ItemStack dynamicIcon = null;
            boolean isDynamic = false;
            for (Action a : new ArrayList<>(actions)) {
                if (a != null) {
                    a.onBuild(session, counts.getOrDefault(a.getName(), 1));
                    if (a.isDynamicIcon() && !isDynamic) {
                        dynamicIcon = a.getDynamicIcon(session, counts.getOrDefault(a.getName(), 1));
                        isDynamic = true;
                    }
                }
            }
            if (isDynamic) {
                return dynamicIcon;
            }
        }
        ItemStack i = new ItemStack(item);
        ItemMeta meta = i.getItemMeta();
        if (meta == null) return i;
        name = PlaceholderAPI.setPlaceholders(session.getPlayer(), name);
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        List<String> lore = i.hasItemMeta() && i.getItemMeta().getLore() != null ?
                new ArrayList<>(i.getItemMeta().getLore()) : new ArrayList<>();
        for (String line : description) {
            line = PlaceholderAPI.setPlaceholders(session.getPlayer(), line);
            lore.add(ChatColor.translateAlternateColorCodes('&', line));
        }
        meta.setLore(lore);
        i.setItemMeta(meta);
        return i;
    }
}
