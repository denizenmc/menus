package org.denizenmc.menus.guis.actions;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.denizenmc.menus.components.Session;
import org.denizenmc.menus.components.actions.Action;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class SendDonateLinkMenusAction extends Action {
    @Override
    public boolean isHidden() {
        return true;
    }

    @Override
    public String getName() {
        return "menus-send-donate-link (DEV)";
    }

    @Override
    public List<String> getDescription() {
        return new ArrayList<>(Arrays.asList("&fSend donate link."));
    }

    @Override
    public String getIconPlayerHeadName() {
        return "MrSnowDK";
    }

    @Override
    public Map<String, String> getDefaultProperties() {
        return new HashMap<>();
    }

    @Override
    public Action copy() {
        return new SendDonateLinkMenusAction();
    }

    @Override
    public boolean isDynamicIcon() {
        return false;
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
    public void onClick(Session session, int count, InventoryClickEvent event) {
        event.setCancelled(true);
        TextComponent text = new TextComponent("Click here to donate. Thank you! :)");
        text.setColor(ChatColor.YELLOW.asBungee());
        text.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://denizen.gitbook.io/dungeons/"));
        session.getPlayer().spigot().sendMessage(text);
    }
}
