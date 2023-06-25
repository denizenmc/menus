package org.denizenmc.menus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.denizenmc.menus.components.Element;
import org.denizenmc.menus.components.Menu;
import org.denizenmc.menus.components.Session;
import org.denizenmc.menus.components.actions.Action;

import javax.annotation.Nullable;

public class MenusAPI implements IMenusAPI {
    @Override
    public void registerAction(Action action, Plugin plugin) {
        Menus.getInstance().register(action, plugin.getName());
    }

    @Override
    public Menu createEmptyMenu(String name, int rows) {
        Menu newMenu = new Menu(name);
        newMenu.setRows(rows);
        Menus.getInstance().getMenuManager().create(newMenu);
        return newMenu;
    }

    @Override
    public Menu createMenuWithBackground(String name, ItemStack item, int rows) {
        Menu newMenu = new Menu(name);
        newMenu.setRows(rows);
        for (int i = 0; i < rows*9; i++) {
            newMenu.getContent().put(i, new Element(item));
        }
        Menus.getInstance().getMenuManager().create(newMenu);
        return newMenu;
    }

    @Override
    public Menu createMenuFromTemplate(String name) {
        Menu template = getMenu(name);
        if (template == null) template = new Menu(name);
        Menu newMenu = template.copy();
        Menus.getInstance().getMenuManager().create(newMenu);
        return newMenu;
    }

    @Override
    public void updateMenu(Menu menu) {
        Menus.getInstance().getIOSource().update(menu);
    }

    @Nullable
    @Override
    public Menu getMenu(String name) {
        return Menus.getInstance().getMenuManager().getByName(name);
    }

    @Nullable
    @Override
    public Session getSession(Player player, String name) {
        if (Menus.getInstance().getSessionManager().getSession(player) != null) {
            Menu menu = getMenu(name);
            if (menu != null) Menus.getInstance().getSessionManager().getSession(player).push(menu);
            return Menus.getInstance().getSessionManager().getSession(player);
        }
        Menu menu = getMenu(name);
        if (menu == null) return null;
        return Menus.getInstance().getSessionManager().startSession(player, menu);
    }

    @Override
    public Session getSession(Player player) {
        if (Menus.getInstance().getSessionManager().getSession(player) != null) {
            return Menus.getInstance().getSessionManager().getSession(player);
        }
        return null;
    }

    @Override
    public ItemStack getBackgroundItemFromMaterial(Material material) {
        ItemStack background = new ItemStack(material == null ? Material.GRAY_STAINED_GLASS_PANE : material);
        ItemMeta meta = background.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + " ");
        background.setItemMeta(meta);
        return background;
    }
}
