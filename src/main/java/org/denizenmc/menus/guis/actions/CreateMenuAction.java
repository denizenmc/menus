package org.denizenmc.menus.guis.actions;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.denizenmc.menus.Menus;
import org.denizenmc.menus.components.Menu;
import org.denizenmc.menus.components.Session;
import org.denizenmc.menus.components.actions.Action;
import org.denizenmc.menus.guis.MenusContextKeys;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CreateMenuAction extends Action {
    @Override
    public boolean isHidden() {
        return true;
    }

    @Override
    public String getName() {
        return "menus-create-menu";
    }

    @Override
    public List<String> getDescription() {
        return new ArrayList<>(Arrays.asList("&fCreate a Menu"));
    }

    @Override
    public String getIconPlayerHeadName() {
        return "Hack";
    }

    @Override
    public Map<String, String> getDefaultProperties() {
        return null;
    }

    @Override
    public Action copy() {
        return new CreateMenuAction();
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
        session.getContext().setValue(MenusContextKeys.MENU_TO_CREATE, Menus.getInstance(), true);
    }
}
