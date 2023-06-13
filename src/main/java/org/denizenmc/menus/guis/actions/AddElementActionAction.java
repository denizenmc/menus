package org.denizenmc.menus.guis.actions;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.denizenmc.menus.components.Session;
import org.denizenmc.menus.components.actions.Action;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class AddElementActionAction extends Action {
    @Override
    public String getName() {
        return "menus-add-element-action";
    }

    @Override
    public List<String> getDescription() {
        return new ArrayList<>(Arrays.asList("&fAdd an Element Action"));
    }

    @Override
    public String getIconPlayerHeadName() {
        return "Hack";
    }

    @Override
    public Map<String, String> getDefaultProperties() {
        return new HashMap<>();
    }

    @Override
    public Action copy() {
        return new AddElementActionAction();
    }

    @Override
    public boolean isDynamicIcon() {
        return false;
    }

    @Nullable
    @Override
    public ItemStack getDynamicIcon(Session session, int count) {
        return null;
    }

    @Override
    public void onBuild(Session session, int count) {

    }

    @Override
    public void onClick(Session session, int count, InventoryClickEvent event) {

    }
}
