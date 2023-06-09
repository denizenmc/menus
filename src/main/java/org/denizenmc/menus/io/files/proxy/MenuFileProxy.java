package org.denizenmc.menus.io.files.proxy;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.denizenmc.menus.Menus;
import org.denizenmc.menus.components.ISerializable;
import org.denizenmc.menus.components.Menu;
import org.denizenmc.menus.components.Query;
import org.denizenmc.menus.components.actions.Action;
import org.denizenmc.menus.components.elements.Element;
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
        cfg.set("size", menu.getSize());
        cfg.set("name", menu.getName());
        int index = 0;
        cfg.set("content-amount", menu.getContent().size());
        for (Integer i : menu.getContent().keySet()) {
            cfg.set("content."+index+".slot", i);
            if (menu.getContent().get(i).getAction() != null) {
                cfg.set("content."+index+".element.action.name", menu.getContent().get(i).getAction().getName());
                int count = 0;
                cfg.set("content."+index+".element.action.properties-amount", menu.getContent().get(i).getAction().getProperties().size());
                for (String key : menu.getContent().get(i).getAction().getProperties().keySet()) {
                    cfg.set("content."+index+".element.action.properties."+count+".key", key);
                    cfg.set("content."+index+".element.action.properties."+count+".value", menu.getContent().get(i).getAction().getProperties().get(key));
                    count++;
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
        if (!file.exists() || !file.isDirectory()) return menus;
        File [] list = file.listFiles();
        if (list == null) return menus;
        if (query.getName() != null) {
            for (File f : list) {
                if (f.exists()) {
                    FileConfiguration cfg = FileUtils.loadYML(f);
                    if (cfg == null) continue;
                    if (cfg.getString("name") != null && ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', cfg.getString("name"))).contains(query.getName())) {
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
        int size = cfg.getInt("size");
        String name = cfg.getString("name");
        Map<Integer, Element> content = new HashMap<>();
        for (int i = 0; i < cfg.getInt("content-amount"); i++) {
            content.put(cfg.getInt("content."+i+".slot"), getElementAtIndex(cfg, i));
        }
        return new Menu(id, size, name, content);
    }

    private Element getElementAtIndex(FileConfiguration cfg, int index) {
        Action action = null;
        if (cfg.getString("content."+index+".element.action.name") != null) {
            action = Menus.getInstance().getFromName(cfg.getString("content."+index+".element.action.name"));
            if (action != null) {
                for (int j = 0; j < cfg.getInt("content."+index+".element.action.properties-amount"); j++) {
                    action.getProperties().put(cfg.getString("content."+index+".element.action.properties."+j+".key"),
                            "content."+index+".element.action.properties."+j+".value");
                }
            }
        }
        ItemStack item = cfg.getItemStack("content."+index+".element.item");
        String name = cfg.getString("content."+index+".element.name");
        List<String> description = cfg.getStringList("content."+index+".element.description");
        return new Element(action, item, name, description);
    }

}
