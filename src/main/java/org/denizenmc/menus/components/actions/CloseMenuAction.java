package org.denizenmc.menus.components.actions;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.denizenmc.menus.components.Session;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class CloseMenuAction extends Action {

    @Override
    public boolean isHidden() {
        return false;
    }

    @Override
    public String getName() {
        return "menus-close-menu";
    }

    @Override
    public List<String> getDescription() {
        return new ArrayList<>(Arrays.asList("&fClose the current menu."));
    }

    @Override
    public String getIconPlayerHeadName() {
        return "Zealock";
    }

    @Override
    public Map<String, String> getDefaultProperties() {
        return new HashMap<>();
    }

    @Override
    public Action copy() {
        return new CloseMenuAction();
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
        session.getPlayer().closeInventory();
    }
}
