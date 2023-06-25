package org.denizenmc.menus.guis.actions;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.denizenmc.menus.Menus;
import org.denizenmc.menus.MenusConfiguration;
import org.denizenmc.menus.MenusUtils;
import org.denizenmc.menus.components.Menu;
import org.denizenmc.menus.components.MenuProperty;
import org.denizenmc.menus.components.Session;
import org.denizenmc.menus.components.actions.Action;
import org.denizenmc.menus.components.actions.TextInputAction;
import org.denizenmc.menus.guis.MenusContextKeys;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class EditMenuPropertyAction extends Action {
    @Override
    public boolean isHidden() {
        return true;
    }

    @Override
    public String getName() {
        return "menus-edit-menu-property";
    }

    @Override
    public List<String> getDescription() {
        return new ArrayList<>(Arrays.asList("&fEdit Menu property.","","&7* Dynamic"));
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
        return new EditMenuPropertyAction();
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
        ItemStack item = null;
        ItemMeta meta;
        List<String> lore = new ArrayList<>();
        switch (count) {
            case 1:
                item = new ItemStack(Material.OAK_FENCE);
                meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.AQUA + "Rows");
                lore.add(ChatColor.WHITE + "Current: " + ChatColor.GOLD + menu.getRows());
                lore.add("");
                lore.add(ChatColor.YELLOW + "Left-Click: " + ChatColor.GRAY + "+ 1");
                lore.add(ChatColor.YELLOW + "Right-Click: " + ChatColor.GRAY + "- 1");
                meta.setLore(lore);
                item.setItemMeta(meta);
                break;
            case 2:
                item = new ItemStack(Material.CLOCK);
                meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.AQUA + "Refresh Rate");
                lore.add(ChatColor.WHITE + "Current: " + ChatColor.GOLD + menu.getRefreshRateSeconds() + " Seconds");
                lore.add("");
                lore.add(ChatColor.YELLOW + "Left-Click: " + ChatColor.GRAY + "+ 1");
                lore.add(ChatColor.YELLOW + "Shift-Left-Click: " + ChatColor.GRAY + "+ 10");
                lore.add(ChatColor.YELLOW + "Right-Click: " + ChatColor.GRAY + "- 1");
                lore.add(ChatColor.YELLOW + "Shift-Right-Click: " + ChatColor.GRAY + "- 10");
                meta.setLore(lore);
                item.setItemMeta(meta);
                break;
            case 3:
                item = new ItemStack(Material.PAPER);
                meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.AQUA + "Permission");
                lore.add(ChatColor.WHITE + "Current: " + ChatColor.GOLD + (menu.getPermission() == null ? "None" : menu.getPermission()));
                lore.add("");
                lore.add(ChatColor.YELLOW + "Click: " + ChatColor.GRAY + "Edit");
                meta.setLore(lore);
                item.setItemMeta(meta);
                break;
            case 4:
                item = new ItemStack(Material.FEATHER);
                meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.AQUA + "Title");
                lore.add(ChatColor.WHITE + "Current: " + ChatColor.translateAlternateColorCodes('&', menu.getTitle()));
                lore.add("");
                lore.add(ChatColor.YELLOW + "Click: " + ChatColor.GRAY + "Edit");
                meta.setLore(lore);
                item.setItemMeta(meta);
                break;
            case 5:
                item = new ItemStack(Material.NAME_TAG);
                meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.AQUA + "Name");
                lore.add(ChatColor.WHITE + "Current: " + ChatColor.GOLD + menu.getName());
                lore.add("");
                lore.add(ChatColor.YELLOW + "Click: " + ChatColor.GRAY + "Edit");
                meta.setLore(lore);
                item.setItemMeta(meta);
                break;
            case 6:
                item = menu.isCanOpenDirectly() ? MenusUtils.getHead(MenusConfiguration.CLICK_TOGGLED_ON_HEAD) :
                        MenusUtils.getHead(MenusConfiguration.CLICK_TOGGLED_OFF_HEAD);
                meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.AQUA + "Can Open Directly");
                lore.add(ChatColor.WHITE + "Current: " + (menu.isCanOpenDirectly() ? ChatColor.GREEN + "YES" : ChatColor.RED + "NO"));
                lore.add("");
                lore.add(ChatColor.YELLOW + "Click: " + ChatColor.GRAY + "Toggle");
                meta.setLore(lore);
                item.setItemMeta(meta);
                break;
            case 7:
                item = MenusUtils.getHead(menu.getPlayerHeadNameIcon());
                meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.AQUA + "Player Head Icon");
                lore.add(ChatColor.WHITE + "Current: " + ChatColor.GOLD + menu.getPlayerHeadNameIcon());
                lore.add("");
                lore.add(ChatColor.YELLOW + "Click: " + ChatColor.GRAY + "Edit");
                meta.setLore(lore);
                item.setItemMeta(meta);
                break;
            case 8:
                item = new ItemStack(Material.CHEST_MINECART);
                meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.AQUA + "Collection");
                lore.add(ChatColor.WHITE + "Current: " + ChatColor.GOLD + menu.getCollection());
                lore.add("");
                lore.add(ChatColor.YELLOW + "Click: " + ChatColor.GRAY + "Edit");
                meta.setLore(lore);
                item.setItemMeta(meta);
        }
        return item;
    }

    @Override
    public void onBuild(Session session, int count) {
        if (count == 1) {
            if (!(session.getContext().getValue(MenusContextKeys.MENU_TO_EDIT, Menus.getInstance()) instanceof Menu)) return;
            Menu menu = (Menu) session.getContext().getValue(MenusContextKeys.MENU_TO_EDIT, Menus.getInstance());
            if (menu == null) return;
            if (session.getContext().getValue(MenusContextKeys.MENU_PROPERTY_TO_EDIT, Menus.getInstance()) instanceof MenuProperty) {
                MenuProperty property = (MenuProperty) session.getContext().getValue(MenusContextKeys.MENU_PROPERTY_TO_EDIT, Menus.getInstance());
                if (property == null) return;
                if (session.getContext().getValue("menus-text-input", Menus.getInstance()) instanceof String) {
                    String text = (String) session.getContext().getValue("menus-text-input", Menus.getInstance());
                    if (text != null) {
                        switch (property) {
                            case PERMISSION:
                                menu.setPermission(text);
                                break;
                            case TITLE:
                                menu.setTitle(text);
                                break;
                            case NAME:
                                menu.setName(text);
                                break;
                            case COLLECTION:
                                menu.setCollection(text);
                                break;
                            case PLAYER_HEAD_ICON:
                                menu.setPlayerHeadNameIcon(text);
                                break;
                        }
                        Menus.getAPI().updateMenu(menu);
                        session.getContext().remove(MenusContextKeys.MENU_PROPERTY_TO_EDIT, Menus.getInstance());
                        session.getContext().remove("menus-text-input", Menus.getInstance());
                    }
                }
            }
        }
    }

    @Override
    public void onClick(Session session, int count, InventoryClickEvent event) {
        event.setCancelled(true);
        if (!(session.getContext().getValue(MenusContextKeys.MENU_TO_EDIT, Menus.getInstance()) instanceof Menu)) return;
        Menu menu = (Menu) session.getContext().getValue(MenusContextKeys.MENU_TO_EDIT, Menus.getInstance());
        if (menu == null) return;
        switch (count) {
            case 1:
                switch (event.getClick()) {
                    case LEFT:
                        if (menu.getRows() < 6) {
                            menu.setRows(menu.getRows()+1);
                            Menus.getAPI().updateMenu(menu);
                        }
                        break;
                    case RIGHT:
                        if (menu.getRows() > 1) {
                            menu.setRows(menu.getRows()-1);
                            Menus.getAPI().updateMenu(menu);
                        }
                        break;
                }
                session.refresh();
                break;
            case 2:
                switch (event.getClick()) {
                    case LEFT:
                        menu.setRefreshRateSeconds(menu.getRefreshRateSeconds()+1);
                        break;
                    case SHIFT_LEFT:
                        menu.setRefreshRateSeconds(menu.getRefreshRateSeconds()+10);
                        break;
                    case RIGHT:
                        menu.setRefreshRateSeconds(menu.getRefreshRateSeconds()-1);
                        break;
                    case SHIFT_RIGHT:
                        menu.setRefreshRateSeconds(menu.getRefreshRateSeconds()-10);
                        break;
                }
                Menus.getAPI().updateMenu(menu);
                session.refresh();
                break;
            case 3:
                session.getContext().setValue(MenusContextKeys.MENU_PROPERTY_TO_EDIT, Menus.getInstance(), MenuProperty.PERMISSION);
                openTextEditor(session, menu, MenuProperty.PERMISSION);
                break;
            case 4:
                session.getContext().setValue(MenusContextKeys.MENU_PROPERTY_TO_EDIT, Menus.getInstance(), MenuProperty.TITLE);
                openTextEditor(session, menu, MenuProperty.TITLE);
                break;
            case 5:
                session.getContext().setValue(MenusContextKeys.MENU_PROPERTY_TO_EDIT, Menus.getInstance(), MenuProperty.NAME);
                openTextEditor(session, menu, MenuProperty.NAME);
                break;
            case 6:
                menu.setCanOpenDirectly(!menu.isCanOpenDirectly());
                Menus.getAPI().updateMenu(menu);
                session.refresh();
                break;
            case 7:
                session.getContext().setValue(MenusContextKeys.MENU_PROPERTY_TO_EDIT, Menus.getInstance(), MenuProperty.PLAYER_HEAD_ICON);
                openTextEditor(session, menu, MenuProperty.PLAYER_HEAD_ICON);
                break;
            case 8:
                session.getContext().setValue(MenusContextKeys.MENU_PROPERTY_TO_EDIT, Menus.getInstance(), MenuProperty.COLLECTION);
                openTextEditor(session, menu, MenuProperty.COLLECTION);
                break;
        }
    }

    private void openTextEditor(Session session, Menu menu, MenuProperty property) {
        TextInputAction input = (TextInputAction) new TextInputAction()
                .setProperty("placeholder-text",
                        getPlaceholderFromProperty(menu, property))
                .setProperty("title-text", "Edit " + property.name())
                .setProperty("item-material", "PAPER")
                .setProperty("item-display-name", "&b"+property.name())
                .setProperty("item-description", "&7Edit");
        input.openEditor(session);
    }

    private String getPlaceholderFromProperty(Menu menu, MenuProperty property) {
        switch (property) {
            case PERMISSION:
                return menu.getPermission() == null ? "none" : menu.getPermission();
            case TITLE:
                return menu.getTitle();
            case NAME:
                return menu.getName();
            case COLLECTION:
                return menu.getCollection();
            case PLAYER_HEAD_ICON:
                return menu.getPlayerHeadNameIcon();
        }
        return "none";
    }
}
