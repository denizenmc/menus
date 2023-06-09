package org.denizenmc.menus.components.actions;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.denizenmc.menus.MenusUtils;
import org.denizenmc.menus.components.Session;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Action {
    private Map<String, String> properties;
    public Action() {
        properties = getDefaultProperties();
    }
    public Action(Map<String, String> properties) {
        this.properties = properties;
    }

    public abstract String getName();
    public abstract List<String> getDescription();
    public abstract String getIconPlayerHeadName();
    public abstract Map<String, String> getDefaultProperties();
    public abstract Action copy();

    public Map<String, String> getProperties() { return properties; }

    /**
     * If the action corresponds to a dynamic icon in the menu, return here.
     * Otherwise, return null and element icon is built like normal.
     * @param session Current player menu session.
     * @param count Current count of the element being referenced in the menu.
     * @return
     */
    @Nullable
    public abstract ItemStack getDynamicIcon(Session session, int count);
    public abstract boolean isDynamicIcon();
    public abstract void onLeftClick(Session session, int count);
    public abstract void onRightClick(Session session, int count);
    public abstract void onShiftLeftClick(Session session, int count);
    public abstract void onShiftRightClick(Session session, int count);

    public ItemStack getIcon() {
        ItemStack icon = new ItemStack(MenusUtils.getHead(getIconPlayerHeadName()));
        ItemMeta meta = icon.getItemMeta();
        if (meta == null) return icon;
        meta.setDisplayName(ChatColor.AQUA + getName());
        List<String> lore = new ArrayList<>();
        for (String line : getDescription()) lore.add(ChatColor.translateAlternateColorCodes('&', line));
        lore.add("");
        lore.add(ChatColor.GRAY + "Click to Select");
        meta.setLore(lore);
        icon.setItemMeta(meta);
        return icon;
    }
}