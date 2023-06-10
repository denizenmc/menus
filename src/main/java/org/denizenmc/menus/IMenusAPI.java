package org.denizenmc.menus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.denizenmc.menus.components.Menu;
import org.denizenmc.menus.components.Session;
import org.denizenmc.menus.components.actions.Action;

import javax.annotation.Nullable;

public interface IMenusAPI {
    /**
     * Register a custom action that can live in a menu.
     * @param action Action to be registered.
     * @param plugin Plugin registering the action.
     */
    void registerAction(Action action, JavaPlugin plugin);

    /**
     * Create an empty menu.
     * @param name Unique name for the menu.
     * @param rows Number of rows in menu (1-6).
     * @return New menu.
     */
    Menu createEmptyMenu(String name, int rows);

    /**
     * Create a menu with all slots filled with the specified item.
     * @param name Unique name for the menu.
     * @param item Item to fill all slots.
     * @param rows Number of rows in menu (1-6).
     * @return New menu.
     */
    Menu createMenuWithBackground(String name, ItemStack item, int rows);

    /**
     * Create a menu from a menu template.
     * @param name Unique name of the menu template.
     * @return New menu.
     */
    Menu createMenuFromTemplate(String name);

    /**
     * Update a menu — saving to files.
     * @param menu Menu to update.
     */
    void updateMenu(Menu menu);

    /**
     * Get a menu from its name.
     * @param name Unique name of the menu (without color codes).
     * @return Menu corresponding to the name, null if not found.
     */
    @Nullable
    Menu getMenu(String name);

    /**
     * Get a menu session for a player.
     * If player already has an active session, returns the active session with the menu added.
     * Fails and returns null if the menu is not found and session is not found.
     * @param player Player that the session should be activated for.
     * @param name Unique name of the menu (no color codes).
     * @return
     */
    Session getSession(Player player, String name);

    /**
     * Strip name and description to get a basic background ItemStack.
     * @param material Material of the item.
     * @return Background ItemStack
     */
    ItemStack getBackgroundItemFromMaterial(Material material);
}
