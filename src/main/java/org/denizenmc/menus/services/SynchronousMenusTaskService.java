package org.denizenmc.menus.services;

import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SynchronousMenusTaskService extends BukkitRunnable {
    private ConcurrentMap<IMenusTask, Integer> tasks = new ConcurrentHashMap<>();

    public void add(IMenusTask task) {
        tasks.put(task, 0);
    }

    @Override
    public void run() {
        List<IMenusTask> list = new ArrayList<>(tasks.keySet());
        for (IMenusTask task : list) {
            if (task.getTicks() <= tasks.get(task)) {
                task.execute();
                tasks.remove(task);
            }  else {
                tasks.put(task, tasks.get(task)+1);
            }
        }
    }
}
