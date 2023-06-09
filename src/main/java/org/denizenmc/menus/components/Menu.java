package org.denizenmc.menus.components;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.denizenmc.menus.components.actions.Action;

import java.util.*;

public class Menu implements InventoryHolder, ISerializable, Comparable<Menu> {
    private UUID id;
    private int refreshRateSeconds;
    private int rows;
    private String name;
    private final Map<Integer, Element> content;

    public Menu() {
        id = UUID.randomUUID();
        name = "Default Menu";
        refreshRateSeconds = 1;
        rows = 4;
        content = new HashMap<>();
    }

    public Menu(UUID id, int rows, int refreshRateSeconds, String name, Map<Integer, Element> content) {
        this.id = id;
        this.rows = rows;
        this.refreshRateSeconds = refreshRateSeconds;
        this.name = name;
        this.content = content;
    }

    public UUID getId() { return id; }

    public int getRows() {
        return rows;
    }
    public void setRows(int rows) { this.rows = rows; }

    public String getName() {
        return name;
    }
    public void setName(String name) { this.name = name; }

    public Map<Integer, Element> getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Menu) {
            return ((Menu) o).getId().equals(id);
        } else { return false; }
    }

    @Override
    public Inventory getInventory() {
        return null;
    }

    @Override
    public List<String> getPath() {
        return Arrays.asList("menus", id.toString()+".yml");
    }

    @Override
    public String getDirectoryName() {
        return "menus";
    }

    @Override
    public int compareTo(Menu o) {
        return name.compareTo(o.getName());
    }

    public int getCount(int slot, Action a) {
        int count = 1;
        if (!content.containsKey(slot)) return count;
        Element e = content.get(slot);
        if (e.getActions().isEmpty()) return count;
        List<Integer> slots = new ArrayList<>(content.keySet());
        Collections.sort(slots);
        for (Integer i : slots) {
            if (i == slot) return count;
            if (content.get(i) != null &&
                    !content.get(i).getActions().isEmpty()) {
                for (Action action : new ArrayList<>(content.get(i).getActions())) {
                    if (action.getName().equalsIgnoreCase(a.getName())) {
                        count++;
                        break;
                    }
                }
            }
        }
        return count;
    }

    public int getRefreshRateSeconds() {
        return refreshRateSeconds;
    }

    public void setRefreshRateSeconds(int refreshRateSeconds) {
        this.refreshRateSeconds = refreshRateSeconds;
    }
}
