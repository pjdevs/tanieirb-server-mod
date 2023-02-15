package com.pjdevs.tanieirb.commands.todo;

import net.minecraft.entity.LivingEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import com.mojang.brigadier.Command;
import com.pjdevs.tanieirb.commands.TanieirbCommand;

import static net.minecraft.server.command.CommandManager.*;

import java.util.ArrayList;
import java.util.UUID;

public class TodoCommand extends TanieirbCommand {
    public TodoCommand() {
        this.shouldRegister = e -> true;
        this.commandBuilder = literal("todo").executes(context -> {
            ServerCommandSource source = context.getSource();
            source.sendFeedback(Text.literal(String.format("%s tasks", source.getPlayer().getName().getString())), false);
            
            ArrayList<String> tasks = TodoPersistentState.getTaskList(source.getPlayer());

            for (int i = 0; i < tasks.size(); i++) {
                source.sendFeedback(Text.literal(String.format("%d. %s", i, tasks.get(i))), false);
            }

            return Command.SINGLE_SUCCESS;
        })
        .then(literal("add").executes(context -> {
            ArrayList<String> tasks = TodoPersistentState.getTaskList(context.getSource().getPlayer());
            tasks.add(String.format("wow new task %s !", String.valueOf(UUID.randomUUID())));

            return Command.SINGLE_SUCCESS;
        }));
    }
}
