package org.denizenmc.menus.elements;

import org.bukkit.inventory.ItemStack;
import org.denizenmc.menus.components.Session;

public abstract class Element {
    private int refreshRateTicks;
    public Element(int refreshRateTicks) { this.refreshRateTicks = refreshRateTicks; }

    abstract ItemStack build(int count);
    abstract ItemStack getIcon();
    abstract void onLeftClick(Session session, int count);
    abstract void onRightClick(Session session, int count);
    abstract void onShiftLeftClick(Session session, int count);
    abstract void onShiftRightClick(Session session, int count);
}
