package org.denizenmc.menus.components;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.denizenmc.menus.components.elements.Element;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Menu implements InventoryHolder {
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

    public static Menu fromFile(File file) {
        return null;
    }

    @Override
    public Inventory getInventory() {
        return null;
    }
}
