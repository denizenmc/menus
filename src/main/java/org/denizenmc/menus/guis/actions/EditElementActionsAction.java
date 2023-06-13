package org.denizenmc.menus.guis.actions;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.denizenmc.menus.Menus;
import org.denizenmc.menus.MenusConfiguration;
import org.denizenmc.menus.components.Element;
import org.denizenmc.menus.components.Menu;
import org.denizenmc.menus.components.Session;
import org.denizenmc.menus.components.actions.Action;
import org.denizenmc.menus.components.actions.ChangeMenuAction;
import org.denizenmc.menus.guis.MenusContextKeys;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class EditElementActionsAction extends Action {
    @Override
    public String getName() {
        return "menus-edit-element-action";
    }

    @Override
    public List<String> getDescription() {
        return new ArrayList<>(Arrays.asList("&fEdit an Element's Action","", "&7* Dynamic"));
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
        return new EditElementActionsAction();
    }

    @Override
    public boolean isDynamicIcon() {
        return true;
    }

    @Nullable
    @Override
    public ItemStack getDynamicIcon(Session session, int count) {
        if (!(session.getContext().getValue(MenusContextKeys.MENU_TO_EDIT, Menus.getInstance()) instanceof Menu)) return null;
        Menu menu = (Menu) session.getContext().getValue(MenusContextKeys.MENU_TO_EDIT, Menus.getInstance());
        if (menu == null) return null;

        if (!(session.getContext().getValue(MenusContextKeys.ELEMENT_TO_EDIT, Menus.getInstance()) instanceof Element)) return null;
        Element element = (Element) session.getContext().getValue(MenusContextKeys.ELEMENT_TO_EDIT, Menus.getInstance());
        if (element == null || element.getActions().isEmpty()) return null;

        int paginatedCount = (session.getPage()-1)*session.getMenu().getTotal(this)+count;
        if (paginatedCount <= element.getActions().size()) {
            return getActionEditIcon(element.getActions().get(paginatedCount-1));
        }
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
        if (element == null || element.getActions().isEmpty()) return;

        int paginatedCount = (session.getPage()-1)*session.getMenu().getTotal(this)+count;
        if (paginatedCount <= element.getActions().size()) {
            if (event.getClick().equals(ClickType.LEFT)) {
                // add action to context
                session.getContext().setValue(MenusContextKeys.ELEMENT_ACTION_TO_EDIT, Menus.getInstance(), element.getActions().get(paginatedCount-1));
                new ChangeMenuAction().setProperty("menu-name", MenusConfiguration.ELEMENT_ACTION_PROPERTY_EDIT_MENU)
                        .onClick(session, count, event);
            } else if (event.getClick().equals(ClickType.SHIFT_LEFT)) {

            } else if (event.getClick().equals(ClickType.SHIFT_RIGHT)) {
                element.getActions().remove(paginatedCount-1);
                Menus.getAPI().updateMenu(menu);
                session.refresh();
            }
        }
    }

    private ItemStack getActionEditIcon(Action a) {
        ItemStack icon = new ItemStack(a.getIcon());
        if (icon.getItemMeta() != null) {
            ItemMeta meta = icon.getItemMeta();
            List<String> lore = meta.getLore() != null ? new ArrayList<>(meta.getLore()) : new ArrayList<>();
            lore.addAll(Arrays.asList("", "&7-------------------------------",
                    "&eLeft-Click: &fEdit Properties",
                    "&eShift-Left-Click: &fEdit Clicks",
                    "&eShift-Right-Click: &cRemove Action"));
            meta.setLore(lore);
            icon.setItemMeta(meta);
        }
        return icon;
    }
}
