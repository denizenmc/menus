package org.denizenmc.menus.components.actions;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.denizenmc.menus.Menus;
import org.denizenmc.menus.MenusConfiguration;
import org.denizenmc.menus.components.Menu;
import org.denizenmc.menus.components.Session;

import javax.annotation.Nullable;
import java.util.*;

/**
 * Properties: menu-name
 */
public class ChangeMenuAction extends Action {

    @Override
    public boolean isHidden() {
        return false;
    }

    @Override
    public String getName() {
        return "menus-change-menu";
    }

    @Override
    public List<String> getDescription() {
        return new ArrayList<>(Arrays.asList("&fChange to another menu.",
                "", "&eInstructions", "&7>> &fEnter the menu name"));
    }

    @Override
    public String getIconPlayerHeadName() {
        return MenusConfiguration.MENUS_PLAYER_HEAD;
    }

    @Override
    public Map<String, String> getDefaultProperties() {
        Map<String, String> properties = new HashMap<>();
        properties.put("menu-name", "");
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
    public void onBuild(Session session, int count) {

    }

    @Override
    public boolean isDynamicIcon() {
        return false;
    }

    @Override
    public void onClick(Session session, int count, InventoryClickEvent event) {
        event.setCancelled(true);
        if (getProperties().get("menu-name") == null) return;
        Menu menu = Menus.getAPI().getMenu(getProperties().get("menu-name"));
        if (menu == null) return;
        session.push(menu);
        session.open();
    }
}
