package org.denizenmc.menus.guis.actions;

import org.bukkit.ChatColor;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.denizenmc.menus.Menus;
import org.denizenmc.menus.MenusConfiguration;
import org.denizenmc.menus.MenusUtils;
import org.denizenmc.menus.components.Element;
import org.denizenmc.menus.components.Menu;
import org.denizenmc.menus.components.Session;
import org.denizenmc.menus.components.actions.Action;
import org.denizenmc.menus.guis.MenusContextKeys;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class EditElementActionClickAction extends Action {
    @Override
    public boolean isHidden() {
        return true;
    }

    @Override
    public String getName() {
        return "menus-edit-action-click";
    }

    @Override
    public List<String> getDescription() {
        return new ArrayList<>(Arrays.asList("&fEdit an Action's Clicks","", "&7* Dynamic"));
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
        return new EditElementActionClickAction();
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

        if (!(session.getContext().getValue(MenusContextKeys.ELEMENT_ACTION_TO_EDIT, Menus.getInstance()) instanceof Action)) return null;
        Action action = (Action) session.getContext().getValue(MenusContextKeys.ELEMENT_ACTION_TO_EDIT, Menus.getInstance());
        if (action == null) return null;

        switch (count) {
            case 1:
                return getActionClickIcon(ClickType.LEFT, action);
            case 2:
                return getActionClickIcon(ClickType.SHIFT_LEFT, action);
            case 3:
                return getActionClickIcon(ClickType.RIGHT, action);
            case 4:
                return getActionClickIcon(ClickType.SHIFT_RIGHT, action);
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

        if (!(session.getContext().getValue(MenusContextKeys.ELEMENT_ACTION_TO_EDIT, Menus.getInstance()) instanceof Action)) return;
        Action action = (Action) session.getContext().getValue(MenusContextKeys.ELEMENT_ACTION_TO_EDIT, Menus.getInstance());
        if (action == null) return;

        switch (count) {
            case 1:
                if (action.getClicks().contains(ClickType.LEFT)) {
                    action.getClicks().remove(ClickType.LEFT);
                } else {
                    action.getClicks().add(ClickType.LEFT);
                }
                break;
            case 2:
                if (action.getClicks().contains(ClickType.SHIFT_LEFT)) {
                    action.getClicks().remove(ClickType.SHIFT_LEFT);
                } else {
                    action.getClicks().add(ClickType.SHIFT_LEFT);
                }
                break;
            case 3:
                if (action.getClicks().contains(ClickType.RIGHT)) {
                    action.getClicks().remove(ClickType.RIGHT);
                } else {
                    action.getClicks().add(ClickType.RIGHT);
                }
                break;
            case 4:
                if (action.getClicks().contains(ClickType.SHIFT_RIGHT)) {
                    action.getClicks().remove(ClickType.SHIFT_RIGHT);
                } else {
                    action.getClicks().add(ClickType.SHIFT_RIGHT);
                }
                break;
        }
        Menus.getAPI().updateMenu(menu);
        session.refresh();
    }

    private ItemStack getActionClickIcon(ClickType click, Action a) {
        ItemStack icon = a.getClicks().contains(click) ?
                MenusUtils.getHead(MenusConfiguration.CLICK_TOGGLED_ON_HEAD) :
                MenusUtils.getHead(MenusConfiguration.CLICK_TOGGLED_OFF_HEAD);
        if (icon.getItemMeta() != null) {
            ItemMeta meta = icon.getItemMeta();
            meta.setDisplayName(ChatColor.AQUA + click.name());
            List<String> lore = new ArrayList<>();
            lore.addAll(Arrays.asList(a.getClicks().contains(click) ?
                    ChatColor.GREEN + "ENABLED" : ChatColor.RED + "DISABLED",
                    "", ChatColor.YELLOW + "Click to Toggle"));
            meta.setLore(lore);
            icon.setItemMeta(meta);
        }
        return icon;
    }
}
