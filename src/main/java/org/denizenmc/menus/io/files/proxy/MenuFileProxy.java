package org.denizenmc.menus.io.files.proxy;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.denizenmc.menus.Menus;
import org.denizenmc.menus.components.ISerializable;
import org.denizenmc.menus.components.Menu;
import org.denizenmc.menus.components.Query;
import org.denizenmc.menus.components.actions.Action;
import org.denizenmc.menus.components.Element;
import org.denizenmc.menus.io.IOProxy;
import org.denizenmc.menus.io.files.FileUtils;

import java.io.File;
import java.util.*;

public class MenuFileProxy extends IOProxy {

    public MenuFileProxy(ISerializable serializable) {
        super(serializable);
    }

    @Override
    public void serialize() {
        if (!(serializable instanceof Menu)) return; // add logging logic
        Menu menu = (Menu) serializable;
        File file = FileUtils.getFile(serializable.getPath());
        if (!file.exists()) return; // add logging logic
        FileConfiguration cfg = FileUtils.loadYML(file);
        cfg.set("id", menu.getId().toString());
        cfg.set("rows", menu.getRows());
        cfg.set("refresh-rate-seconds", menu.getRefreshRateSeconds());
        cfg.set("name", menu.getName());
        cfg.set("title", menu.getTitle());
        cfg.set("player-head-icon", menu.getPlayerHeadNameIcon());
        cfg.set("collection", menu.getCollection());
        cfg.set("can-open-directly", menu.isCanOpenDirectly());
        cfg.set("is-hidden", menu.isHidden());
        cfg.set("permission", menu.getPermission());
        int index = 0;
        cfg.set("content", null);
        cfg.set("content-amount", menu.getContent().size());
        for (Integer i : menu.getContent().keySet()) {
            cfg.set("content."+index+".slot", i);
            if (!menu.getContent().get(i).getActions().isEmpty()) {
                int aCount = 0;
                cfg.set("content."+index+".element.actions-amount", menu.getContent().get(i).getActions().size());
                for (Action a : new ArrayList<>(menu.getContent().get(i).getActions())) {
                    cfg.set("content."+index+".element.action."+aCount+".name", a.getName());
                    cfg.set("content."+index+".element.action."+aCount+".clicks-amount", a.getClicks().size());
                    int clickAmount = 0;
                    for (ClickType click : a.getClicks()) {
                        cfg.set("content."+index+".element.action."+aCount+".clicks."+clickAmount, click.name());
                        clickAmount++;
                    }
                    int count = 0;
                    cfg.set("content."+index+".element.action."+aCount+".properties-amount", a.getProperties().size());
                    for (String key : a.getProperties().keySet()) {
                        cfg.set("content."+index+".element.action."+aCount+".properties."+count+".key", key);
                        cfg.set("content."+index+".element.action."+aCount+".properties."+count+".value", a.getProperties().get(key));
                        count++;
                    }
                    aCount++;
                }
            }
            cfg.set("content."+index+".element.item", menu.getContent().get(i).getItem());
            cfg.set("content."+index+".element.name", menu.getContent().get(i).getName());
            cfg.set("content."+index+".element.description", menu.getContent().get(i).getDescription());
            index++;
        }
        FileUtils.saveYML(cfg, file);
    }

    @Override
    public List<ISerializable> deserialize(Query query) {
        List<ISerializable> menus = new ArrayList<>();
        File file = FileUtils.getFile(Arrays.asList("menus"));
        if (!file.exists() || !file.isDirectory()) {
            return menus;
        }
        File [] list = file.listFiles();
        if (list == null) return menus;
        if (query.getCollection() != null) {
            for (File f : list) {
                if (f.exists()) {
                    FileConfiguration cfg = FileUtils.loadYML(f);
                    if (cfg == null) continue;
                    if (cfg.getString("collection") != null && cfg.getString("collection").equalsIgnoreCase(query.getName())) {
                        menus.add(getMenuFromFileConfig(cfg));
                    }
                }
            }
        } else if (query.getName() != null) {
            for (File f : list) {
                if (f.exists()) {
                    FileConfiguration cfg = FileUtils.loadYML(f);
                    if (cfg == null) continue;
                    if (cfg.getString("name") != null && cfg.getString("name").contains(query.getName())) {
                        menus.add(getMenuFromFileConfig(cfg));
                    }
                }
            }
        } else if (query.getId() != null) {
            for (File f : list) {
                if (f.exists()) {
                    FileConfiguration cfg = FileUtils.loadYML(f);
                    if (cfg == null) continue;
                    if (cfg.getString("id") != null && cfg.getString("id").equalsIgnoreCase(query.getId())) {
                        menus.add(getMenuFromFileConfig(cfg));
                        break;
                    }
                }
            }
        } else {
            for (File f : list) {
                if (f.exists()) {
                    FileConfiguration cfg = FileUtils.loadYML(f);
                    if (cfg == null) continue;
                    menus.add(getMenuFromFileConfig(cfg));
                }
            }
        }
        return menus;
    }

    private ISerializable getMenuFromFileConfig(FileConfiguration cfg) {
        UUID id = cfg.getString("id") == null ? UUID.randomUUID() : UUID.fromString(cfg.getString("id"));
        int rows = cfg.getInt("rows");
        int refreshRateSeconds = cfg.getInt("refresh-rate-seconds");
        String title = cfg.getString("title");
        String playerHeadIcon = cfg.getString("player-head-icon");
        String collection = cfg.getString("collection");
        boolean canOpenDirectly = cfg.getBoolean("can-open-directly");
        boolean isHidden = cfg.getBoolean("is-hidden");
        String permission = cfg.getString("permission");
        String name = cfg.getString("name");
        Map<Integer, Element> content = new HashMap<>();
        for (int i = 0; i < cfg.getInt("content-amount"); i++) {
            content.put(cfg.getInt("content."+i+".slot"), getElementAtIndex(cfg, i));
        }
        return new Menu(id, rows, refreshRateSeconds, name, title, permission, playerHeadIcon, collection, canOpenDirectly, isHidden, content);
    }

    private Element getElementAtIndex(FileConfiguration cfg, int index) {
        List<Action> actions = new ArrayList<>();
        for (int i = 0; i < cfg.getInt("content."+index+".element.actions-amount"); i++) {
            if (cfg.getString("content."+index+".element.action."+i+".name") != null) {
                Action action = Menus.getInstance().getFromName(cfg.getString("content."+index+".element.action."+i+".name"));
                if (action != null) {
                    action.getClicks().clear();
                    for (int j = 0; j < cfg.getInt("content."+index+".element.action."+i+".clicks-amount"); j++) {
                        try {
                            ClickType click = ClickType.valueOf(cfg.getString("content."+index+".element.action."+i+".clicks."+j));
                            action.getClicks().add(click);
                        } catch (Exception ignored) {}
                    }
                    for (int j = 0; j < cfg.getInt("content."+index+".element.action."+i+".properties-amount"); j++) {
                        action.getProperties().put(cfg.getString("content."+index+".element.action."+i+".properties."+j+".key"),
                                cfg.getString("content."+index+".element.action."+i+".properties."+j+".value"));
                    }
                    if (action.getName() != null) actions.add(action);
                }
            }
        }
        ItemStack item = cfg.getItemStack("content."+index+".element.item");
        if (item == null) item = new ItemStack(Material.BARRIER);
        String name = cfg.getString("content."+index+".element.name");
        List<String> description = cfg.getStringList("content."+index+".element.description");
        return new Element(actions, item, name, description);
    }

}
