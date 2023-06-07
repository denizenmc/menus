package org.denizenmc.menus.components.actions;

import org.bukkit.inventory.ItemStack;
import org.denizenmc.menus.components.Session;

import javax.annotation.Nullable;
import java.util.List;

public abstract class Action {
    abstract String getName();
    abstract List<String> getDescription();
    abstract String getIconPlayerHeadName();

    /**
     * If the action corresponds to a dynamic icon in the menu, return here.
     * Otherwise, return null and element icon is built like normal.
     * @param session Current player menu session.
     * @param count Current count of the element being referenced in the menu.
     * @return
     */
    @Nullable
    public abstract ItemStack getDynamicIcon(Session session, int count);
    public abstract void onLeftClick(Session session, int count);
    public abstract void onRightClick(Session session, int count);
    public abstract void onShiftLeftClick(Session session, int count);
    public abstract void onShiftRightClick(Session session, int count);
}
