package org.denizenmc.menus.components;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.*;

public class Menu implements InventoryHolder, ISerializable, Comparable<Menu> {
    private UUID id;
    private int size;
    private String name;
    private final Map<Integer, Element> content;

    public Menu() {
        id = UUID.randomUUID();
        name = "Default Menu";
        size = 36;
        content = new HashMap<>();
    }

    public Menu(UUID id, int size, String name, Map<Integer, Element> content) {
        this.id = id;
        this.size = size;
        this.name = name;
        this.content = content;
    }

    public UUID getId() { return id; }

    public int getSize() {
        return size;
    }
    public void setSize(int size) { this.size = size; }

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
}
