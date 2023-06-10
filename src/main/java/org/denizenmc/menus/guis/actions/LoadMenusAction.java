package org.denizenmc.menus.guis.actions;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.denizenmc.menus.Menus;
import org.denizenmc.menus.components.Query;
import org.denizenmc.menus.components.Session;
import org.denizenmc.menus.components.actions.Action;
import org.denizenmc.menus.guis.MenusContextKeys;
import org.denizenmc.menus.io.EntityType;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class LoadMenusAction extends Action {

    @Override
    public String getName() {
        return "menus-load-menus";
    }

    @Override
    public List<String> getDescription() {
        return new ArrayList<>(Arrays.asList("&fLoad Menus into context."));
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
        return new LoadMenusAction();
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
        session.getContext().put(MenusContextKeys.LOADED_MENUS, Menus.getInstance().getMenuManager().getList(new Query().setEntity(EntityType.MENU)));
    }
}
