package org.denizenmc.menus.guis.actions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.denizenmc.menus.Menus;
import org.denizenmc.menus.components.Element;
import org.denizenmc.menus.components.Menu;
import org.denizenmc.menus.components.Session;
import org.denizenmc.menus.components.actions.Action;
import org.denizenmc.menus.components.actions.TextInputAction;
import org.denizenmc.menus.guis.MenusContextKeys;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class EditElementDescriptionLineAction extends Action {

    @Override
    public String getName() {
        return "menus-edit-element-description-line";
    }

    @Override
    public List<String> getDescription() {
        return new ArrayList<>(Arrays.asList("&fEdit an Element's Description Line","", "&7* Dynamic"));
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
        return new EditElementDescriptionLineAction();
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

        int paginatedCount = (session.getPage()-1)*session.getMenu().getTotal(this)+count;
        if (paginatedCount <= element.getDescription().size()) {
            return getLineItem(paginatedCount-1, element.getDescription());
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

            if (!(session.getContext().getValue(MenusContextKeys.ELEMENT_DESCRIPTION_LINE_TO_EDIT, Menus.getInstance()) instanceof Integer)) return;
            Integer line = (Integer) session.getContext().getValue(MenusContextKeys.ELEMENT_DESCRIPTION_LINE_TO_EDIT, Menus.getInstance());

            if (!(session.getContext().getValue("menus-text-input", Menus.getInstance()) instanceof String)) return;
            String text = (String) session.getContext().getValue("menus-text-input", Menus.getInstance());

            if (line < element.getDescription().size()) {
                element.getDescription().set(line, text);
                Menus.getAPI().updateMenu(menu);
            }
            session.getContext().remove(MenusContextKeys.ELEMENT_DESCRIPTION_LINE_TO_EDIT, Menus.getInstance());
            session.getContext().remove("menus-text-input", Menus.getInstance());
        }
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

        int paginatedCount = (session.getPage()-1)*session.getMenu().getTotal(this)+count;
        if (paginatedCount <= element.getDescription().size()) {
            if (event.getClick().equals(ClickType.LEFT)) {
                session.getContext().setValue(MenusContextKeys.ELEMENT_DESCRIPTION_LINE_TO_EDIT, Menus.getInstance(), paginatedCount-1);
                TextInputAction input = (TextInputAction) new TextInputAction()
                        .setProperty("placeholder-text",
                                element.getDescription().get(paginatedCount-1))
                        .setProperty("title-text", "Edit Description Line")
                        .setProperty("item-material", "PAPER")
                        .setProperty("item-display-name", "&eDescription Line")
                        .setProperty("item-description", "&7Edit");
                input.openEditor(session);
            } else if (event.getClick().equals(ClickType.SHIFT_LEFT)) {
                if (paginatedCount-1 > 0) {
                    String priorLine = element.getDescription().get(paginatedCount-2);
                    element.getDescription().set(paginatedCount-2, element.getDescription().get(paginatedCount-1));
                    element.getDescription().set(paginatedCount-1, priorLine);
                    Menus.getAPI().updateMenu(menu);
                }
                session.refresh();
            } else if (event.getClick().equals(ClickType.RIGHT)) {
                if (paginatedCount < element.getDescription().size()) {
                    String nextLine = element.getDescription().get(paginatedCount);
                    element.getDescription().set(paginatedCount, element.getDescription().get(paginatedCount-1));
                    element.getDescription().set(paginatedCount-1, nextLine);
                    Menus.getAPI().updateMenu(menu);
                }
                session.refresh();
            } else if (event.getClick().equals(ClickType.SHIFT_RIGHT)) {
                element.getDescription().remove(paginatedCount-1);
                Menus.getAPI().updateMenu(menu);
                session.refresh();
            }
        }
    }

    private ItemStack getLineItem(int line, List<String> description) {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "Line #"+line);
        List<String> lore = new ArrayList<>(Arrays.asList(ChatColor.translateAlternateColorCodes('&', description.get(line)),
                ChatColor.GRAY + "-------------------------------", ChatColor.YELLOW + "Left-Click: " +
                        ChatColor.WHITE + "Edit Line", ChatColor.YELLOW +
                        "Shift-Left-Click: " + ChatColor.WHITE + "Move Line Up",
                        ChatColor.YELLOW + "Right-Click: " + ChatColor.WHITE + "Move Line Down",
                        ChatColor.YELLOW + "Shift-Right-Click: " + ChatColor.RED + "Remove Line"));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}
