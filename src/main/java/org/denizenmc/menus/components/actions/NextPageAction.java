package org.denizenmc.menus.components.actions;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.denizenmc.menus.components.Session;

import javax.annotation.Nullable;
import java.util.*;

public class NextPageAction extends Action {

    @Override
    public String getName() {
        return "menus-next-page";
    }

    @Override
    public List<String> getDescription() {
        return new ArrayList<>(Arrays.asList("&fIncrease the page count by 1."));
    }

    @Override
    public String getIconPlayerHeadName() {
        return "MHF_ArrowUp";
    }

    @Override
    public Map<String, String> getDefaultProperties() {
        return new HashMap<>();
    }

    @Override
    public Action copy() {
        return new NextPageAction();
    }

    @Nullable
    @Override
    public ItemStack getDynamicIcon(Session session, int count) {
        return null;
    }

    @Override
    public void onBuild(Session session, int count) {

    }

    @Override
    public boolean isDynamicIcon() {
        return false;
    }

    @Override
    public void onClick(Session session, int count, InventoryClickEvent event) {
        event.setCancelled(true);
        session.setPage(session.getPage()+1);
        session.refresh();
    }
}
