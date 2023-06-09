package org.denizenmc.menus.components.actions;

import org.bukkit.inventory.ItemStack;
import org.denizenmc.menus.components.Session;

import javax.annotation.Nullable;
import java.util.*;

public class ChangeMenuAction extends Action {

    @Override
    public String getName() {
        return "change-menu";
    }

    @Override
    public List<String> getDescription() {
        return new ArrayList<>(Arrays.asList("&fChange to another menu.",
                "", "&eInstructions", "&7>> &fEnter the menu name", "&7>> without color codes"));
    }

    @Override
    public String getIconPlayerHeadName() {
        return null;
    }

    @Override
    public Map<String, String> getDefaultProperties() {
        Map<String, String> properties = new HashMap<>();
        properties.put("menu-name", "none");
        return properties;
    }

    @Override
    public Action copy() {
        return new ChangeMenuAction();
    }

    @Nullable
    @Override
    public ItemStack getDynamicIcon(Session session, int count) {
        return null;
    }

    @Override
    public boolean isDynamicIcon() {
        return false;
    }

    @Override
    public void onLeftClick(Session session, int count) {

    }

    @Override
    public void onRightClick(Session session, int count) {

    }

    @Override
    public void onShiftLeftClick(Session session, int count) {

    }

    @Override
    public void onShiftRightClick(Session session, int count) {

    }
}
