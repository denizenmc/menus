package org.denizenmc.menus.guis.actions;

import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.denizenmc.menus.Menus;
import org.denizenmc.menus.components.Element;
import org.denizenmc.menus.components.Menu;
import org.denizenmc.menus.components.Session;
import org.denizenmc.menus.components.actions.Action;
import org.denizenmc.menus.guis.MenusContextKeys;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class SelectElementActionAction extends Action {
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
        if (!(session.getContext().getValue(MenusContextKeys.MENU_TO_EDIT, Menus.getInstance()) instanceof Menu)) return null;
        Menu menu = (Menu) session.getContext().getValue(MenusContextKeys.MENU_TO_EDIT, Menus.getInstance());
        if (menu == null) return null;

        if (!(session.getContext().getValue(MenusContextKeys.ELEMENT_TO_EDIT, Menus.getInstance()) instanceof Element)) return null;
        Element element = (Element) session.getContext().getValue(MenusContextKeys.ELEMENT_TO_EDIT, Menus.getInstance());
        if (element == null) return null;

        List<Action> actions = Menus.getInstance().getRegisteredActions();
        int paginatedCount = (session.getPage()-1)*session.getMenu().getTotal(this)+count;


        // change to Action PLUGIN

        if (paginatedCount <= actions.size() && !actions.isEmpty()) {
            return getActionIcon(actions.get(paginatedCount-1));
        }
        return null;
    }

    @Override
    public void onBuild(Session session, int count) {

    }

    @Override
    public void onClick(Session session, int count, InventoryClickEvent event) {

    }

    private ItemStack getActionIcon(Action a) {
        ItemStack item = new ItemStack(a.getIcon());
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;
        List<String> lore = item.getItemMeta().getLore() == null ? new ArrayList<>() : item.getItemMeta().getLore();
        lore.addAll(Arrays.asList(ChatColor.GRAY + "-------------------------------",
                ChatColor.YELLOW + "Click to Select"));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}
