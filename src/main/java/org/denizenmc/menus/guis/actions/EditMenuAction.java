package org.denizenmc.menus.guis.actions;

import org.bukkit.ChatColor;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.denizenmc.menus.Menus;
import org.denizenmc.menus.MenusConfiguration;
import org.denizenmc.menus.MenusUtils;
import org.denizenmc.menus.components.Menu;
import org.denizenmc.menus.components.Query;
import org.denizenmc.menus.components.Session;
import org.denizenmc.menus.components.actions.Action;
import org.denizenmc.menus.guis.MenusContextKeys;
import org.denizenmc.menus.guis.menus.EditMenu;
import org.denizenmc.menus.io.EntityType;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class EditMenuAction extends Action {

    @Override
    public String getName() {
        return "menus-edit-menu (DEV)";
    }

    @Override
    public List<String> getDescription() {
        return new ArrayList<>(Arrays.asList("&fEdit Menu entity.","", "&7* Supports Pages","&7* Dynamic"));
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
        return new EditMenuAction();
    }

    @Override
    public boolean isDynamicIcon() {
        return true;
    }

    @Nullable
    @Override
    public ItemStack getDynamicIcon(Session session, int count) {
        if (session.getContext().getValue(MenusContextKeys.LOADED_MENUS, Menus.getInstance()) instanceof List) {
            List list = (List) session.getContext().getValue(MenusContextKeys.LOADED_MENUS, Menus.getInstance());
            List<Menu> menus = MenusUtils.getMenusFromList(list);
            int paginatedCount = (session.getPage()-1)*session.getMenu().getTotal(this)+count;
            if (!menus.isEmpty() && paginatedCount <= menus.size()) {
                Collections.sort(menus);
                Menu m = menus.get(paginatedCount-1);
                ItemStack icon = MenusUtils.getHead(m.getPlayerHeadNameIcon());
                ItemMeta meta = icon.getItemMeta();
                if (meta == null) return icon;
                meta.setDisplayName(ChatColor.AQUA + m.getName());
                List<String> lore = new ArrayList<>();
                lore.add(ChatColor.WHITE + "Collection: " + ChatColor.GRAY + m.getCollection());
                lore.add("");
                lore.add(ChatColor.YELLOW + "Title: " + ChatColor.translateAlternateColorCodes('&', m.getTitle()));
                lore.add(ChatColor.YELLOW + "Rows: " + ChatColor.WHITE + m.getRows());
                lore.add(ChatColor.YELLOW + "Refresh Rate: " + ChatColor.WHITE + m.getRefreshRateSeconds() + " second(s)");
                lore.add(ChatColor.YELLOW + "# of Elements: " + ChatColor.WHITE + m.getContent().size());
                lore.add("");
                lore.add(ChatColor.AQUA + "Left-Click: " + ChatColor.YELLOW + "Edit Content");
                lore.add(ChatColor.AQUA + "Shift-Left-Click: " + ChatColor.YELLOW + "Edit Properties");
                lore.add(ChatColor.AQUA + "Shift-Right-Click: " + ChatColor.YELLOW + "Remove");
                meta.setLore(lore);
                icon.setItemMeta(meta);
                return icon;
            }
        }
        return null;
    }

    @Override
    public void onBuild(Session session, int count) {
        if (count == 1) {
            List<Menu> menus = new ArrayList<>();
            if (session.getContext().getValue("menus-text-input", Menus.getInstance()) instanceof String) {
                String query = (String) session.getContext().getValue("menus-text-input", Menus.getInstance());
                if (query.contains("name=") && query.indexOf("name=")+5 < query.length()) {
                    String name = query.substring(query.indexOf("name=")+5);
                    menus = Menus.getInstance().getMenuManager().getList(new Query().setName(name).setEntity(EntityType.MENU));
                } else if (query.contains("collection=") && query.indexOf("collection=")+11 < query.length() ) {
                    String collection = query.substring(query.indexOf("collection=")+11);
                    menus = Menus.getInstance().getMenuManager().getList(new Query().setCollection(collection).setEntity(EntityType.MENU));
                }
            } else {
                menus = Menus.getInstance().getMenuManager().getList(new Query().setEntity(EntityType.MENU));
            }
            session.getContext().setValue(MenusContextKeys.LOADED_MENUS, Menus.getInstance(), menus);
        }
    }

    @Override
    public void onClick(Session session, int count, InventoryClickEvent event) {
        event.setCancelled(true);
        if (!(session.getContext().getValue(MenusContextKeys.LOADED_MENUS, Menus.getInstance()) instanceof List)) return;
        List list = (List) session.getContext().getValue(MenusContextKeys.LOADED_MENUS, Menus.getInstance());
        List<Menu> menus = MenusUtils.getMenusFromList(list);
        if (menus.isEmpty()) return;
        int paginatedCount = (session.getPage()-1)*session.getMenu().getTotal(this)+count;
        if (event.getClick().equals(ClickType.LEFT)) {
            if (menus.size() >= paginatedCount) {
                session.getContext().setValue(MenusContextKeys.MENU_TO_EDIT, Menus.getInstance(), menus.get(paginatedCount-1));
                session.push(new EditMenu().create(session));
                session.open();
                session.pause();
            }
        } else if (event.getClick().equals(ClickType.SHIFT_RIGHT)) {
            if (menus.size() >= paginatedCount) {
                if (menus.get(paginatedCount-1).getCollection().equalsIgnoreCase("Menus Dev")) {
                    session.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', MenusConfiguration.ERROR_CANNOT_DELETE_MENUS_DEV_MENU));
                } else {
                    Menus.getInstance().getMenuManager().remove(menus.get(paginatedCount-1));
                    session.refresh();
                }
            }
        } else if (event.getClick().equals(ClickType.SHIFT_LEFT)) {

        }
    }
}
