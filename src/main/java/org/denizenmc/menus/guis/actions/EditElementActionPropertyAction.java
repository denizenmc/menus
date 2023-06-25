package org.denizenmc.menus.guis.actions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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

public class EditElementActionPropertyAction extends Action {
    @Override
    public boolean isHidden() {
        return true;
    }

    @Override
    public String getName() {
        return "menus-edit-action-property";
    }

    @Override
    public List<String> getDescription() {
        return new ArrayList<>(Arrays.asList("&fEdit an Action's Properties","", "&7* Dynamic"));
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
        return new EditElementActionPropertyAction();
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
        if (action == null || action.getProperties().isEmpty()) return null;

        List<String> properties = new ArrayList<>(action.getProperties().keySet());
        Collections.sort(properties);
        int paginatedCount = (session.getPage()-1)*session.getMenu().getTotal(this)+count;
        if (paginatedCount <= properties.size()) {
            return getActionPropertyIcon(properties.get(paginatedCount-1), action.getProperties().get(properties.get(paginatedCount-1)));
        }
        return null;
    }

    @Override
    public void onBuild(Session session, int count) {
        if (count == 1) {
            if (!(session.getContext().getValue("menus-text-input", Menus.getInstance()) instanceof String)) return;
            String text = (String) session.getContext().getValue("menus-text-input", Menus.getInstance());
            if (!(session.getContext().getValue(MenusContextKeys.MENU_TO_EDIT, Menus.getInstance()) instanceof Menu)) return;
            Menu menu = (Menu) session.getContext().getValue(MenusContextKeys.MENU_TO_EDIT, Menus.getInstance());
            if (menu == null) return;
            if (!(session.getContext().getValue(MenusContextKeys.ELEMENT_ACTION_TO_EDIT, Menus.getInstance()) instanceof Action)) return;
            Action action = (Action) session.getContext().getValue(MenusContextKeys.ELEMENT_ACTION_TO_EDIT, Menus.getInstance());
            if (action == null || action.getProperties().isEmpty()) return;
            if (!(session.getContext().getValue(MenusContextKeys.ELEMENT_ACTION_PROPERTY_TO_EDIT, Menus.getInstance()) instanceof Integer)) return;
            Integer index = (Integer) session.getContext().getValue(MenusContextKeys.ELEMENT_ACTION_PROPERTY_TO_EDIT, Menus.getInstance());
            List<String> properties = new ArrayList<>(action.getProperties().keySet());
            Collections.sort(properties);
            if (index >= properties.size()) return;
            action.setProperty(properties.get(index), text);
            Menus.getAPI().updateMenu(menu);
            session.getContext().remove("menus-text-input", Menus.getInstance());
            session.getContext().remove(MenusContextKeys.ELEMENT_ACTION_PROPERTY_TO_EDIT, Menus.getInstance());
        }
    }

    @Override
    public void onClick(Session session, int count, InventoryClickEvent event) {
        event.setCancelled(true);
        if (!(session.getContext().getValue(MenusContextKeys.ELEMENT_ACTION_TO_EDIT, Menus.getInstance()) instanceof Action)) return;
        Action action = (Action) session.getContext().getValue(MenusContextKeys.ELEMENT_ACTION_TO_EDIT, Menus.getInstance());
        if (action == null || action.getProperties().isEmpty()) return;

        List<String> properties = new ArrayList<>(action.getProperties().keySet());
        Collections.sort(properties);

        int paginatedCount = (session.getPage()-1)*session.getMenu().getTotal(this)+count;
        if (paginatedCount <= properties.size()) {
            session.getContext().setValue(MenusContextKeys.ELEMENT_ACTION_PROPERTY_TO_EDIT, Menus.getInstance(), paginatedCount-1);
            TextInputAction input = (TextInputAction) new TextInputAction()
                    .setProperty("placeholder-text", action.getProperties().get(properties.get(paginatedCount-1)))
                    .setProperty("title-text", "Edit Action Property")
                    .setProperty("item-material", "PAPER")
                    .setProperty("item-display-name", "&eAction Property")
                    .setProperty("item-description", "&7Edit");
            input.openEditor(session);
        }
    }

    private ItemStack getActionPropertyIcon(String property, String value) {
        ItemStack icon = new ItemStack(Material.PAPER);
        if (icon.getItemMeta() != null) {
            ItemMeta meta = icon.getItemMeta();
            meta.setDisplayName(ChatColor.AQUA + property);
            List<String> lore = new ArrayList<>();
            lore.addAll(Arrays.asList(ChatColor.WHITE + value,
                    ChatColor.GRAY + "-------------------------------",
                    ChatColor.YELLOW + "Click: " + ChatColor.WHITE + "Edit"));
            meta.setLore(lore);
            icon.setItemMeta(meta);
        }
        return icon;
    }
}
