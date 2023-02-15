package com.pjdevs.tanieirb.commands.todo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

public class TodoPersistentState extends PersistentState {

    public HashMap<UUID, ArrayList<String>> playerTasks;

    public TodoPersistentState() {
        this.playerTasks = new HashMap<UUID, ArrayList<String>>();
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        NbtCompound taskListByPlayerNbtCompound = new NbtCompound();

        playerTasks.forEach((UUID, tasks) -> {
            NbtCompound playerTaskListNbt = new NbtCompound();

            playerTaskListNbt.putInt("taskCount", tasks.size());

            for (int i = 0; i < tasks.size(); i++) {
                nbt.putString(String.format("task[%d]", i), tasks.get(i));
            }

            taskListByPlayerNbtCompound.put(String.valueOf(UUID), playerTaskListNbt);
        });

        nbt.put("taskListByPlayer", taskListByPlayerNbtCompound);

        return nbt;
    }

    public static TodoPersistentState createFromNbt(NbtCompound tag) {
        TodoPersistentState playerState = new TodoPersistentState();
        
        NbtCompound taskListByPlayerNbtCompound = tag.getCompound("taskListByPlayer");

        taskListByPlayerNbtCompound.getKeys().forEach(key -> {
            NbtCompound tasksNbtCompound = taskListByPlayerNbtCompound.getCompound(key);

            ArrayList<String> tasks = new ArrayList<String>();

            int taskCount = tasksNbtCompound.getInt("taskCount");
            for (int i = 0; i < taskCount; i++) {
                tasks.add(tasksNbtCompound.getString(String.format("task[%d]", i)));
            }
        });

        return playerState;
    }

    public static TodoPersistentState getTodoState(MinecraftServer server) {
        PersistentStateManager persistentStateManager = server.getWorld(World.OVERWORLD).getPersistentStateManager();
 
        TodoPersistentState todoState = persistentStateManager.getOrCreate(
            TodoPersistentState::createFromNbt,
            TodoPersistentState::new,
            "tanieirb");
 
        todoState.markDirty();
 
        return todoState;
    }
 
    public static ArrayList<String> getTaskList(LivingEntity player) {
        TodoPersistentState todoState = getTodoState(player.world.getServer());
 
        return todoState.playerTasks.computeIfAbsent(player.getUuid(), uuid -> {
            ArrayList<String> defaultTasks = new ArrayList<String>();
            defaultTasks.add("Discover the todo command!");

            return defaultTasks;
        });
    }
}
