package org.denizenmc.menus.guis.actions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.denizenmc.menus.Menus;
import org.denizenmc.menus.MenusUtils;
import org.denizenmc.menus.components.Element;
import org.denizenmc.menus.components.Menu;
import org.denizenmc.menus.components.Session;
import org.denizenmc.menus.components.actions.Action;
import org.denizenmc.menus.guis.MenusContextKeys;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class SelectElementActionAction extends Action {
    @Override
    public boolean isHidden() {
        return true;
    }

    @Override
    public String getName() {
        return "menus-add-new-action";
    }

    @Override
    public List<String> getDescription() {
        return new ArrayList<>(Arrays.asList("&fSelection to add a new Action to an Element."));
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
        return new SelectElementActionAction();
    }

    @Override
    public boolean isDynamicIcon() {
        return true;
    }

    @Nullable
    @Override
    public ItemStack getDynamicIcon(Session session, int count) {
        if (session.getContext().getValue(MenusContextKeys.LOADED_ACTIONS, Menus.getInstance()) instanceof List) {
            List list = (List) session.getContext().getValue(MenusContextKeys.LOADED_ACTIONS, Menus.getInstance());
            List<Action> actions = MenusUtils.getActionsFromList(list);
            int paginatedCount = (session.getPage()-1)*session.getMenu().getTotal(this)+count;

            if (paginatedCount <= actions.size() && !actions.isEmpty()) {
                return getActionIcon(actions.get(paginatedCount-1));
            }
        }
        return null;
    }

    @Override
    public void onBuild(Session session, int count) {
        if (count == 1) {
            if (!(session.getContext().getValue(MenusContextKeys.MENU_TO_EDIT, Menus.getInstance()) instanceof Menu)) return;
            Menu menu = (Menu) session.getContext().getValue(MenusContextKeys.MENU_TO_EDIT, Menus.getInstance());
            if (menu == null) return;
            if (!(session.getContext().getValue(MenusContextKeys.ELEMENT_TO_EDIT, Menus.getInstance()) instanceof Element)) return;
            Element element = (Element) session.getContext().getValue(MenusContextKeys.ELEMENT_TO_EDIT, Menus.getInstance());
            if (element == null) return;
            List<Action> actions = new ArrayList<>();
            if (session.getContext().getValue("menus-text-input", Menus.getInstance()) instanceof String) {
                String filter = (String) session.getContext().getValue("menus-text-input", Menus.getInstance());
                if (filter != null) {
                    actions.addAll(Menus.getInstance().getRegisteredActions(filter));
                } else {
                    actions.addAll(Menus.getInstance().getRegisteredActions(null));
                }
            } else { actions.addAll(Menus.getInstance().getRegisteredActions(null)); }
            session.getContext().setValue(MenusContextKeys.LOADED_ACTIONS, Menus.getInstance(), actions);
            session.getContext().remove("menus-text-input", Menus.getInstance());
        }
    }

    @Override
    public void onClick(Session session, int count, InventoryClickEvent event) {
        event.setCancelled(true);
        if (session.getContext().getValue(MenusContextKeys.LOADED_ACTIONS, Menus.getInstance()) instanceof List) {
            List list = (List) session.getContext().getValue(MenusContextKeys.LOADED_ACTIONS, Menus.getInstance());
            List<Action> actions = MenusUtils.getActionsFromList(list);
            int paginatedCount = (session.getPage()-1)*session.getMenu().getTotal(this)+count;
            if (paginatedCount <= actions.size() && !actions.isEmpty()) {
                session.getContext().setValue(MenusContextKeys.ELEMENT_ACTION_TO_ADD, Menus.getInstance(), actions.get(paginatedCount-1).copy());
                session.getContext().remove(MenusContextKeys.LOADED_ACTIONS, Menus.getInstance());
                session.pop();
                session.open();
            }
        }
    }

    private ItemStack getActionIcon(Action a) {
        ItemStack item = new ItemStack(a.getIcon());
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;
        String plugin = Menus.getInstance().getPluginFromAction(a);
        List<String> lore = item.getItemMeta().getLore() == null ? new ArrayList<>() : item.getItemMeta().getLore();
        lore.addAll(Arrays.asList(ChatColor.GRAY + "-------------------------------",
                ChatColor.YELLOW + "Click to Select", "", ChatColor.WHITE + "Plugin: " + ChatColor.GRAY +
                        (plugin == null ? "None" : plugin)));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}
