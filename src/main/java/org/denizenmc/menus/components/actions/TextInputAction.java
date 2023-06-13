package org.denizenmc.menus.components.actions;

import me.clip.placeholderapi.PlaceholderAPI;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.denizenmc.menus.Menus;
import org.denizenmc.menus.components.Session;

import javax.annotation.Nullable;
import java.util.*;

/**
 * Properties: placeholder-text, title-text, item-material, item-display-name, item-description
 *
 */
public class TextInputAction extends Action {

    @Override
    public boolean isHidden() {
        return false;
    }

    @Override
    public String getName() {
        return "menus-text-input";
    }

    @Override
    public List<String> getDescription() {
        return new ArrayList<>(Arrays.asList("&fOpen anvil menu to receive",
                "&ftext input from the player,", "which can be used by Actions",
                "", "&eInstructions", "&7>> &bplaceholder-text: &fDefault Input Text",
                "&7>> &btitle-text: &fAnvil Menu Title",
                "&7>> &bitem-material: &fLeft Item Material",
                "&7>> &bitem-display-name: &fLeft Item Name",
                "&7>> &bitem-description: &fLeft Item Description",
                "&7>> &f(Separate description lines with '::')", "",
                "&eExample Description", "&fEnter your text input::in the menu"));
    }

    @Override
    public String getIconPlayerHeadName() {
        return "phumicek";
    }

    @Override
    public Map<String, String> getDefaultProperties() {
        Map<String, String> map = new HashMap<>();
        map.put("placeholder-text", "Input text here");
        map.put("title-text", "Text Input");
        map.put("item-material", "PAPER");
        map.put("item-display-name", "&eText Input");
        map.put("item-description", "");
        return map;
    }

    @Override
    public Action copy() {
        return new TextInputAction();
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
        if (event.getClick().equals(ClickType.LEFT)) {
            openEditor(session);
        } else if (event.getClick().equals(ClickType.SHIFT_RIGHT)) {
            if (session.getContext().getValue("menus-text-input", Menus.getInstance()) != null) {
                session.getContext().remove("menus-text-input", Menus.getInstance());
                session.refresh();
            }
        }
    }

    public void openEditor(Session session) {
        session.pause();
        new AnvilGUI.Builder()
                .onClose(player -> {
                    session.resume();
                })
                .onClick((slot, stateSnapshot) -> {
                    if (slot != AnvilGUI.Slot.OUTPUT) {
                        return Collections.emptyList();
                    }
                    session.getContext().setValue("menus-text-input", Menus.getInstance(), PlaceholderAPI.setPlaceholders(session.getPlayer(), stateSnapshot.getText()));
                    return Arrays.asList(AnvilGUI.ResponseAction.close());
                })
                .text(getProperties().get("placeholder-text"))
                .itemLeft(getItem())
                .title(getProperties().get("title-text"))
                .plugin(Menus.getInstance())
                .open(session.getPlayer());
    }

    private ItemStack getItem() {
        Material material = Material.matchMaterial(getProperties().get("item-material"));
        if (material == null) material = Material.PAPER;
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', getProperties().get("item-display-name")));
        List<String> lore = new ArrayList<>();
        for (String line : getProperties().get("item-display-name").split("::")) {
            lore.add(ChatColor.translateAlternateColorCodes('&', line));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}
