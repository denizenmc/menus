package org.denizenmc.menus.managers;

import org.denizenmc.menus.components.Query;

import java.util.List;
import java.util.UUID;

public interface Manager<T> {
    void add(T component);
    void remove(T component);
    T create();
    void remove(UUID id);
    void init();
    T getById(UUID id);
    T getByName(String name);
    List<T> getList(Query query);
    String getName();
    void saveToFiles();
}
