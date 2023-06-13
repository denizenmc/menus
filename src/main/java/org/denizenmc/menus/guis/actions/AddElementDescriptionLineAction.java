package org.denizenmc.menus.guis.actions;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.denizenmc.menus.Menus;
import org.denizenmc.menus.components.Element;
import org.denizenmc.menus.components.Menu;
import org.denizenmc.menus.components.Session;
import org.denizenmc.menus.components.actions.Action;
import org.denizenmc.menus.guis.MenusContextKeys;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class AddElementDescriptionLineAction extends Action {
    @Override
    public String getName() {
        return "menus-add-element-description-line";
    }

    @Override
    public List<String> getDescription() {
        return new ArrayList<>(Arrays.asList("&fAdd an Element Description Line"));
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
        return new AddElementDescriptionLineAction();
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
        event.setCancelled(true);
        if (!(session.getContext().getValue(MenusContextKeys.MENU_TO_EDIT, Menus.getInstance()) instanceof Menu)) return;
        Menu menu = (Menu) session.getContext().getValue(MenusContextKeys.MENU_TO_EDIT, Menus.getInstance());
        if (menu == null) return;

        if (!(session.getContext().getValue(MenusContextKeys.ELEMENT_TO_EDIT, Menus.getInstance()) instanceof Element)) return;
        Element element = (Element) session.getContext().getValue(MenusContextKeys.ELEMENT_TO_EDIT, Menus.getInstance());
        if (element == null) return;

        element.getDescription().add("&7Edit me...");
        Menus.getAPI().updateMenu(menu);
        session.refresh();
    }
}
