package org.denizenmc.menus.components;
import org.denizenmc.menus.elements.Element;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Menu {
    private UUID id;
    private int size;
    private String name;
    private final Map<Integer, Element> content;

    public Menu(String name) {
        id = UUID.randomUUID();
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
}
