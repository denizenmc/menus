package org.denizenmc.menus.components;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.denizenmc.menus.components.actions.Action;

import java.util.*;

public class Menu implements InventoryHolder, ISerializable, Comparable<Menu> {
    private UUID id;
    private int refreshRateSeconds, rows;
    private boolean canOpenDirectly, isHidden;
    private String title, playerHeadNameIcon, collection, permission, name;
    private final Map<Integer, Element> content;

    public Menu(String name) {
        id = UUID.randomUUID();
        this.name = name;
        playerHeadNameIcon = "Hack";
        collection = "Menus";
        title = "New Menu";
        permission = null;
        refreshRateSeconds = 1;
        rows = 4;
        canOpenDirectly = true;
        isHidden = false;
        content = new HashMap<>();
    }

    public Menu(UUID id, int rows, int refreshRateSeconds, String name, String title, String permission, String playerHeadNameIcon,
                String collection, boolean canOpenDirectly, boolean isHidden, Map<Integer, Element> content) {
        this.id = id;
        this.rows = rows;
        this.refreshRateSeconds = refreshRateSeconds;
        this.name = name;
        this.title = title;
        this.permission = permission;
        this.playerHeadNameIcon = playerHeadNameIcon;
        this.collection = collection;
        this.canOpenDirectly = canOpenDirectly;
        this.isHidden = isHidden;
        this.content = content;
    }

    public UUID getId() { return id; }

    public int getRows() {
        return rows;
    }
    public Menu setRows(int rows) {
        this.rows = rows;
        return this;
    }

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

    public int getTotal(Action a) {
        int count = 0;
        for (Integer i : content.keySet()) {
            if (content.get(i) != null && !content.get(i).getActions().isEmpty()) {
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

    public Menu setRefreshRateSeconds(int refreshRateSeconds) {
        this.refreshRateSeconds = refreshRateSeconds;
        return this;
    }

    public Menu copy() {
        return new Menu(UUID.randomUUID(), rows, refreshRateSeconds,
                name, title, permission, playerHeadNameIcon, collection, canOpenDirectly, isHidden, content);
    }

    public String getTitle() {
        return title;
    }

    public Menu setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getPlayerHeadNameIcon() { return playerHeadNameIcon; }
    public void setPlayerHeadNameIcon(String playerHeadNameIcon) { this.playerHeadNameIcon = playerHeadNameIcon; }

    public String getCollection() {
        return collection;
    }

    public Menu setCollection(String collection) {
        this.collection = collection;
        return this;
    }

    public String getPermission() {
        return permission;
    }

    public Menu setPermission(String permission) {
        this.permission = permission;
        return this;
    }

    public boolean isCanOpenDirectly() {
        return canOpenDirectly;
    }

    public void setCanOpenDirectly(boolean canOpenDirectly) {
        this.canOpenDirectly = canOpenDirectly;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }
}
